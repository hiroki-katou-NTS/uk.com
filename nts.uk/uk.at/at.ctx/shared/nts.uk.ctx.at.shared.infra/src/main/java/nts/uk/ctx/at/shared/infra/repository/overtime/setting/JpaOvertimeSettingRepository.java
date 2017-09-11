/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeRepository;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItem;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemRepository;
import nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSetting;
import nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.overtime.KshstOverTime;
import nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.KshstOverTimeBrd;
import nts.uk.ctx.at.shared.infra.entity.overtime.setting.KshstOverTimeSet;
import nts.uk.ctx.at.shared.infra.repository.overtime.JpaOvertimeSetMemento;
import nts.uk.ctx.at.shared.infra.repository.overtime.breakdown.JpaOvertimeBRDItemSetMemento;

/**
 * The Class JpaOvertimeSettingRepository.
 */
@Stateless
public class JpaOvertimeSettingRepository extends JpaRepository
		implements OvertimeSettingRepository {

	/** The repository. */
	@Inject
	private OvertimeRepository overtimeRepository;
	
	/** The overtime BRD item repository. */
	@Inject
	private OvertimeBRDItemRepository overtimeBRDItemRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingRepository#findById(java
	 * .lang.String)
	 */
	@Override
	public Optional<OvertimeSetting> findById(String companyId) {

		// call repository find entity setting
		Optional<KshstOverTimeSet> entity = this.queryProxy().find(companyId,
				KshstOverTimeSet.class);

		// call repository find all domain overtime
		List<Overtime> domainOvertime = this.overtimeRepository.findAll(companyId);

		// call repository find all domain overtime break down item
		List<OvertimeBRDItem> domainOvertimeBrdItem = this.overtimeBRDItemRepository
				.findAll(companyId);

		// domain to entity
		List<KshstOverTime> entityOvertime = domainOvertime.stream().map(domain -> {
			KshstOverTime entityItem = new KshstOverTime();
			domain.saveToMemento(new JpaOvertimeSetMemento(entityItem, companyId));
			return entityItem;
		}).collect(Collectors.toList());

		// domain to entity
		List<KshstOverTimeBrd> entityOvertimeBRDItem = domainOvertimeBrdItem.stream()
				.map(domain -> {
					KshstOverTimeBrd entityItem = new KshstOverTimeBrd();
					domain.saveToMemento(new JpaOvertimeBRDItemSetMemento(entityItem, companyId));
					return entityItem;
				}).collect(Collectors.toList());

		// check exist data
		if (entity.isPresent()) {
			return Optional
					.ofNullable(this.toDomain(entity.get(), entityOvertimeBRDItem, entityOvertime));
		}
		// default data
		return Optional.ofNullable(
				this.toDomain(new KshstOverTimeSet(), entityOvertimeBRDItem, entityOvertime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingRepository#save(
	 * nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSetting)
	 */
	@Override
	public void save(OvertimeSetting domain) {
		KshstOverTimeSet entity = new KshstOverTimeSet();
		// call repository find entity setting
		Optional<KshstOverTimeSet> opEntity = this.queryProxy().find(domain.getCompanyId().v(),
				KshstOverTimeSet.class);
		
		// check exist data
		if(opEntity.isPresent()){
			entity = opEntity.get();
			entity = this.toEntity(domain);
			this.commandProxy().update(entity);
		}
		// insert data
		else {
			entity = this.toEntity(domain);
			this.commandProxy().insert(entity);
		}
		// save all overtime
		overtimeRepository.saveAll(domain.getOvertimes(), domain.getCompanyId().v());
		
		// save all over time breakdown item
		overtimeBRDItemRepository.saveAll(domain.getBreakdownItems(), domain.getCompanyId().v());
	}
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @param entityOvertimeBRDItems the entity overtime BRD items
	 * @param entityOvertime the entity overtime
	 * @return the overtime setting
	 */
	private OvertimeSetting toDomain(KshstOverTimeSet entity,
			List<KshstOverTimeBrd> entityOvertimeBRDItems, List<KshstOverTime> entityOvertime) {
		return new OvertimeSetting(
				new JpaOvertimeSettingGetMemento(entity, entityOvertimeBRDItems, entityOvertime));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst over time set
	 */
	private KshstOverTimeSet toEntity(OvertimeSetting domain) {
		KshstOverTimeSet entity = new KshstOverTimeSet();
		domain.saveToMemento(
				new JpaOvertimeSettingSetMemento(new ArrayList<>(), new ArrayList<>(), entity));
		return entity;
	}
	

}
