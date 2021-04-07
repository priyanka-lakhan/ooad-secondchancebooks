package com.ooad.secondchance.domain.mappers;

import com.ooad.secondchance.domain.entities.Book;
import com.ooad.secondchance.dto.BooksDTO;
import com.ooad.secondchance.service.BooksService;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Source;
import java.lang.annotation.Target;

/**
 * Created by Priyanka on 4/2/21.
 */
@Component
@Mapper(componentModel = "spring", uses = {BooksService.class})
public interface BookMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDTO(BooksDTO booksDTO, @MappingTarget Book book);
}
