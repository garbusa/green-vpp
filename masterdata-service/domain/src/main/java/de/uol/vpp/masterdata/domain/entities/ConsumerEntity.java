package de.uol.vpp.masterdata.domain.entities;

import de.uol.vpp.masterdata.domain.architecture.DomainEntity;
import de.uol.vpp.masterdata.domain.valueobjects.ConsumerIdVO;
import de.uol.vpp.masterdata.domain.valueobjects.ConsumerPowerVO;
import de.uol.vpp.masterdata.domain.valueobjects.ConsumerStatusVO;
import lombok.Data;

@DomainEntity
@Data
public class ConsumerEntity {
    private ConsumerIdVO consumerId;
    private ConsumerPowerVO consumerPower;
    private ConsumerStatusVO consumerStatus;
}
