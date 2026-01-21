package main.service.location.mapper;

import main.service.location.dto.LocationDto;
import main.service.location.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDto toDto(Location location);

    @Mapping(target = "id", ignore = true)
    Location toEntity(LocationDto locationDto);
}
