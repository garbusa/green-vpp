package de.uol.vpp.masterdata.domain.repositories;

import de.uol.vpp.masterdata.domain.aggregates.DecentralizedPowerPlantAggregate;
import de.uol.vpp.masterdata.domain.aggregates.VirtualPowerPlantAggregate;
import de.uol.vpp.masterdata.domain.valueobjects.DecentralizedPowerPlantIdVO;

import java.util.List;
import java.util.Optional;

public interface IDecentralizedPowerPlantRepository {
    List<DecentralizedPowerPlantAggregate> getAllByVppKey(VirtualPowerPlantAggregate virtualPowerPlantAggregate) throws VirtualPowerPlantRepositoryException, DecentralizedPowerPlantRepositoryException;

    Optional<DecentralizedPowerPlantAggregate> getById(DecentralizedPowerPlantIdVO id) throws DecentralizedPowerPlantRepositoryException;

    void save(DecentralizedPowerPlantAggregate entity) throws DecentralizedPowerPlantRepositoryException;

    void deleteById(DecentralizedPowerPlantIdVO id) throws DecentralizedPowerPlantRepositoryException;

    void assign(DecentralizedPowerPlantAggregate entity, VirtualPowerPlantAggregate virtualPowerPlant) throws DecentralizedPowerPlantRepositoryException;

    void update(DecentralizedPowerPlantIdVO id, DecentralizedPowerPlantAggregate domainEntity) throws DecentralizedPowerPlantRepositoryException;
}
