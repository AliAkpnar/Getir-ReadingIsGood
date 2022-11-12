package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.advice.exception.BookNotFoundException;
import com.getir.readingisgood.converter.BookEntityConverter;
import com.getir.readingisgood.model.dto.BookDto;
import com.getir.readingisgood.model.request.BookRequest;
import com.getir.readingisgood.model.request.PatchBookRequest;
import com.getir.readingisgood.persistence.entity.BookEntity;
import com.getir.readingisgood.persistence.repository.BookRepository;
import com.getir.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookDto saveBook(BookRequest bookRequest) {
        BookEntity bookEntity = mapToEntity(bookRequest);
        bookRepository.save(bookEntity);
        return BookEntityConverter.toMapBookDto(bookEntity);
    }

    @Override
    public BookDto updateBook(PatchBookRequest patchBookRequest) {
        BookEntity bookEntity = getBook(patchBookRequest.getBookId());
        bookEntity.setStock(patchBookRequest.getStockOfBook());
        BookEntity updateBook = bookRepository.save(bookEntity);
        return BookEntityConverter.toMapBookDto(updateBook);
    }

    private BookEntity getBook(long BookId) {
        Optional<BookEntity> bookEntity = Optional.ofNullable(bookRepository.findById(BookId)
                .orElseThrow(BookNotFoundException::new));
        return bookEntity.get();
    }

    private BookEntity mapToEntity(BookRequest bookRequest) {
        return modelMapper.map(bookRequest, BookEntity.class);
    }
}
