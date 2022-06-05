package ru.job4j.passport.controller.tools;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.job4j.passport.domain.Passport;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PassportMapper {
    PassportMapper INSTANCE = Mappers.getMapper(PassportMapper.class);
    @Mapping(target="name", source="name")
    @Mapping(target="lastname", source="lastname")
    @Mapping(target="expirationDate", source="expirationDate")
    @Mapping(target="citizenship", source="citizenship")
    void update(Passport newPassport, @MappingTarget Passport oldPassport);
}
