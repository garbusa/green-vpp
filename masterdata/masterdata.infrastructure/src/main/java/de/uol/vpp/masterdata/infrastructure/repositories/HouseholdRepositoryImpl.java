package de.uol.vpp.masterdata.infrastructure.repositories;

import de.uol.vpp.masterdata.domain.aggregates.HouseholdAggregate;
import de.uol.vpp.masterdata.domain.aggregates.VirtualPowerPlantAggregate;
import de.uol.vpp.masterdata.domain.exceptions.HouseholdException;
import de.uol.vpp.masterdata.domain.repositories.HouseholdRepositoryException;
import de.uol.vpp.masterdata.domain.repositories.IHouseholdRepository;
import de.uol.vpp.masterdata.domain.valueobjects.HouseholdIdVO;
import de.uol.vpp.masterdata.infrastructure.InfrastructureEntityConverter;
import de.uol.vpp.masterdata.infrastructure.entities.Household;
import de.uol.vpp.masterdata.infrastructure.entities.VirtualPowerPlant;
import de.uol.vpp.masterdata.infrastructure.jpaRepositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseholdRepositoryImpl implements IHouseholdRepository {

    private final HouseholdJpaRepository jpaRepository;
    private final VirtualPowerPlantJpaRepository virtualPowerPlantJpaRepository;
    private final StorageJpaRepository storageJpaRepository;
    private final SolarEnergyJpaRepository solarJpaRepository;
    private final WindEnergyJpaRepository windJpaRepository;
    private final WaterEnergyJpaRepository waterJpaRepository;
    private final OtherEnergyJpaRepository otherJpaRepository;
    private final InfrastructureEntityConverter converter;

    @Override
    public List<HouseholdAggregate> getAllByVirtualPowerPlant(VirtualPowerPlantAggregate virtualPowerPlantAggregate) throws HouseholdRepositoryException {
        try {
            Optional<VirtualPowerPlant> virtualPowerPlantOptional = virtualPowerPlantJpaRepository.findOneByBusinessKey(virtualPowerPlantAggregate.getVirtualPowerPlantId().getValue());
            if (virtualPowerPlantOptional.isPresent()) {
                List<HouseholdAggregate> result = new ArrayList<>();
                for (Household household : jpaRepository.findAllByVirtualPowerPlant(virtualPowerPlantOptional.get())) {
                    result.add(converter.toDomain(household));
                }
                return result;
            } else {
                throw new HouseholdRepositoryException(
                        String.format("There is no VPP with actionRequestId %s to get all households", virtualPowerPlantAggregate.getVirtualPowerPlantId().getValue())
                );
            }
        } catch (HouseholdException e) {
            throw new HouseholdRepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<HouseholdAggregate> getById(HouseholdIdVO id) throws HouseholdRepositoryException {
        try {
            Optional<Household> result = jpaRepository.findOneByBusinessKey(id.getValue());
            if (result.isPresent()) {
                return Optional.of(converter.toDomain(result.get()));
            }
            return Optional.empty();
        } catch (HouseholdException e) {
            throw new HouseholdRepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void save(HouseholdAggregate entity) throws HouseholdRepositoryException {
        Household jpaEntity = converter.toInfrastructure(entity);
        jpaRepository.save(jpaEntity);
    }

    @Override
    public void deleteById(HouseholdIdVO id) throws HouseholdRepositoryException {
        Optional<Household> jpaEntity = jpaRepository.findOneByBusinessKey(id.getValue());
        if (jpaEntity.isPresent()) {
            Household household = jpaEntity.get();

            household.getSolars().forEach(solarJpaRepository::delete);
            household.getWaters().forEach(waterJpaRepository::delete);
            household.getWinds().forEach(windJpaRepository::delete);
            household.getOthers().forEach(otherJpaRepository::delete);
            household.getStorages().forEach(storageJpaRepository::delete);

            jpaRepository.delete(household);
        } else {
            throw new HouseholdRepositoryException(
                    String.format("household %s can not be found and can not be deleted", id.getValue())
            );
        }
    }

    @Override
    public void assign(HouseholdAggregate entity, VirtualPowerPlantAggregate virtualPowerPlant) throws HouseholdRepositoryException {
        Optional<Household> jpaEntityOptional = jpaRepository.findOneByBusinessKey(entity.getHouseholdId().getValue());
        Optional<VirtualPowerPlant> virtualPowerPlantOptional = virtualPowerPlantJpaRepository.findOneByBusinessKey(virtualPowerPlant.getVirtualPowerPlantId().getValue());
        if (jpaEntityOptional.isPresent() && virtualPowerPlantOptional.isPresent()) {
            Household jpaEntity = jpaEntityOptional.get();
            VirtualPowerPlant virtualPowerPlantJpaEntity = virtualPowerPlantOptional.get();
            if (jpaEntity.getVirtualPowerPlant() == null) {
                jpaEntity.setVirtualPowerPlant(virtualPowerPlantJpaEntity);
                jpaRepository.save(jpaEntity);
                virtualPowerPlantJpaEntity.getHouseholds().add(jpaEntity);
                virtualPowerPlantJpaRepository.save(virtualPowerPlantJpaEntity);
            } else {
                throw new HouseholdRepositoryException(
                        String.format("Dpp %s is already assigned to vpp %s", entity.getHouseholdId().getValue(),
                                jpaEntity.getVirtualPowerPlant().getBusinessKey())
                );
            }
        } else {
            throw new HouseholdRepositoryException(
                    String.format("An error occured while assigning dpp %s to vpp %s", entity.getHouseholdId().getValue(),
                            virtualPowerPlant.getVirtualPowerPlantId().getValue())
            );
        }
    }

    @Override
    public void update(HouseholdIdVO id, HouseholdAggregate domainEntity) throws HouseholdRepositoryException {
        Optional<Household> jpaEntityOptional = jpaRepository.findOneByBusinessKey(id.getValue());
        if (jpaEntityOptional.isPresent()) {
            Household jpaEntity = jpaEntityOptional.get();
            Household updated = converter.toInfrastructure(domainEntity);
            jpaEntity.setBusinessKey(updated.getBusinessKey());
            jpaEntity.setMemberAmount(updated.getMemberAmount());
            jpaRepository.save(jpaEntity);
        } else {
            throw new HouseholdRepositoryException("failed to update household. can not find household entity.");
        }
    }

}
