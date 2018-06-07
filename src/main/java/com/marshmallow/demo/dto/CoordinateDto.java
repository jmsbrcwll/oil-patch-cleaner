package com.marshmallow.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class CoordinateDto {

    @Min(value = 0)
    private Integer x;

    @Min(value = 0)
    private Integer y;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
