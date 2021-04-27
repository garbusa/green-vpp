package de.uol.vpp.masterdata.domain.valueobjects;

import de.uol.vpp.masterdata.domain.exceptions.ProducerException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WindEnergyLongitudeVO {

    private Double value;

    public WindEnergyLongitudeVO(Double value) throws ProducerException {
        if (value == null || value < -180. || value > 180.) {
            throw new ProducerException("validation for wind longitude failed");
        }
        this.value = value;
    }
}
