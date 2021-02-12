package de.uol.vpp.masterdata.domain.household;

import de.uol.vpp.masterdata.domain.architecture.ValueObject;
import lombok.Data;

@ValueObject
@Data
public class HouseholdMemberAmount {
    private Integer amount;
}
