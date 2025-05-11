package de.jodegen.exchange.mapper;

import de.jodegen.exchange.model.CurrencyReference;
import de.jodegen.exchange.rest.model.CurrencyReferenceDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyReferenceMapper {

    CurrencyReferenceDto toDto(CurrencyReference reference);

    List<CurrencyReferenceDto> toDtoList(List<CurrencyReference> references);
}
