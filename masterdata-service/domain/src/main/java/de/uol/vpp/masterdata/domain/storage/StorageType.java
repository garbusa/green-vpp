package de.uol.vpp.masterdata.domain.storage;

import de.uol.vpp.masterdata.domain.EnergyType;
import de.uol.vpp.masterdata.domain.architecture.ValueObject;
import lombok.Data;

@ValueObject
@Data
public class StorageType {
    private EnergyType energyType;
}

