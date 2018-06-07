package com.marshmallow.demo.dto;

import java.io.Serializable;


public class OilSpillOutputDto implements Serializable {

    private CoordinateDto finalPosition;

    private Integer patchesCleaned;

    public CoordinateDto getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(CoordinateDto finalPosition) {
        this.finalPosition = finalPosition;
    }

    public Integer getPatchesCleaned() {
        return patchesCleaned;
    }

    public void setPatchesCleaned(Integer patchesCleaned) {
        this.patchesCleaned = patchesCleaned;
    }
}
