package com.marshmallow.demo.web;

import com.marshmallow.demo.dto.OilSpillInputDto;
import com.marshmallow.demo.dto.OilSpillOutputDto;
import com.marshmallow.demo.exception.CoordinateOutOfBoundsException;
import com.marshmallow.demo.service.OilSpillService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/oil-spill")
public class OilSpillController {

    @PostMapping
    public OilSpillOutputDto cleanOilSpill(@RequestBody @Validated OilSpillInputDto oilSpillInputDto) {
        return OilSpillService.cleanOilSpill(oilSpillInputDto);
    }

    @ExceptionHandler(CoordinateOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public void handleOutOfBoundsException(CoordinateOutOfBoundsException e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }




}
