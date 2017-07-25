/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwmmtWorkMonthSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwmmtWorkMonthSetPK;

/**
 * The Class JpaWorkMonthlySettingRepository.
 */
@Stateless
public class JpaWorkMonthlySettingRepository extends JpaRepository
		implements WorkMonthlySettingRepository {

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#add(nts.uk.ctx.at.schedule.dom.shift.pattern
	 * .work.WorkMonthlySetting)
	 */
	@Override
	public void add(WorkMonthlySetting workMonthlySetting) {
		this.commandProxy().insert(this.toEntity(workMonthlySetting));
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * pattern.work.WorkMonthlySetting)
	 */
	@Override
	public void update(WorkMonthlySetting workMonthlySetting) {
		this.commandProxy().update(this.toEntityUpdate(workMonthlySetting));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingRepository#findById(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<WorkMonthlySetting> findById(String companyId, String monthlyPatternCode,
			GeneralDate baseDate) {
		return this.queryProxy()
				.find(new KwmmtWorkMonthSetPK(companyId, monthlyPatternCode, baseDate),
						KwmmtWorkMonthSet.class)
				.map(c -> this.toDomain(c));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the work monthly setting
	 */
	private WorkMonthlySetting toDomain(KwmmtWorkMonthSet entity) {
		return new WorkMonthlySetting(new JpaWorkMonthlySettingGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kwmmt work month set
	 */
	private KwmmtWorkMonthSet toEntity(WorkMonthlySetting domain){
		KwmmtWorkMonthSet entity = new KwmmtWorkMonthSet();
		domain.saveToMemento(new JpaWorkMonthlySettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kwmmt work month set
	 */
	private KwmmtWorkMonthSet toEntityUpdate(WorkMonthlySetting domain){
		
		Optional<KwmmtWorkMonthSet> optionalEntity = this.queryProxy()
				.find(new KwmmtWorkMonthSetPK(domain.getCompanyId().v(),
						domain.getMonthlyPatternCode().v(), domain.getDate()),
						KwmmtWorkMonthSet.class);
		
		KwmmtWorkMonthSet entity = optionalEntity.get();
		domain.saveToMemento(new JpaWorkMonthlySettingSetMemento(entity));
		return entity;
	}

}
