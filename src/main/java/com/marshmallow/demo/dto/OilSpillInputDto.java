package com.marshmallow.demo.dto;



import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

public class OilSpillInputDto implements Serializable {

    @NotNull @Valid
    private CoordinateDto areaSize;

    @NotNull @Valid
    private CoordinateDto startingPosition;

    @NotNull @Valid
    private List<CoordinateDto> oilPatches;

    @NotNull @Pattern(regexp = "[NESW]*")
    private String navigationInstructions;

    public CoordinateDto getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(CoordinateDto areaSize) {
        this.areaSize = areaSize;
    }

    public CoordinateDto getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(CoordinateDto startingPosition) {
        this.startingPosition = startingPosition;
    }

    public List<CoordinateDto> getOilPatches() {
        return oilPatches;
    }

    public void setOilPatches(List<CoordinateDto> oilPatches) {
        this.oilPatches = oilPatches;
    }

    public String getNavigationInstructions() {
        return navigationInstructions;
    }

    public void setNavigationInstructions(String navigationInstructions) {
        this.navigationInstructions = navigationInstructions;
    }
}
