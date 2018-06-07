package com.marshmallow.demo.validation;

import com.marshmallow.demo.dto.OilSpillInputDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OilSpillValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return OilSpillInputDto.class == clazz;
    }
    @Override
    public void validate(Object obj, Errors errors) {
        OilSpillInputDto oilSpillInput = (OilSpillInputDto) obj;
    }

}
