package de.uol.vpp.load.domain.valueobjects;

import de.uol.vpp.load.domain.exceptions.HouseholdLoadException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseholdLoadCurrentValueVO {

    private Double value;
    private boolean outdated;

    public HouseholdLoadCurrentValueVO(Double value, boolean outdated) throws HouseholdLoadException {
        if (value < 0) {
            throw new HouseholdLoadException("validation for household load value failed.");
        }
        this.value = value;
        this.outdated = outdated;
    }
}
