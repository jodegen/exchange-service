package de.jodegen.exchange.mapper;

import de.jodegen.exchange.model.ExchangeRate;
import de.jodegen.exchange.rest.model.ExchangeRateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {

    @Mapping(source = "reference.code", target = "currencyCode")
    ExchangeRateDto toDto(ExchangeRate rate);

    List<ExchangeRateDto> toDtoList(List<ExchangeRate> rates);
}
