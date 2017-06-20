/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.JuuwtstUsageUnitWtSet;

/**
 * The Class JpaUsageUnitSettingRepository.
 */
@Stateless
public class JpaUsageUnitSettingRepository extends JpaRepository implements UsageUnitSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingRepository#update(nts.uk.ctx.at.shared.dom.employment.
	 * statutory.worktime.UsageUnitSetting)
	 */
	@Override
	public void update(UsageUnitSetting setting) {
		this.commandProxy().update(this.toEntitỵ̣̣(setting));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingRepository#find(java.lang.String)
	 */
	@Override
	public Optional<UsageUnitSetting> findByCompany(String companyId) {
		return this.queryProxy().find(companyId, JuuwtstUsageUnitWtSet.class)
				.map(setting -> this.toDomain(setting));
	}
	
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the usage unit setting
	 */
	private UsageUnitSetting toDomain(JuuwtstUsageUnitWtSet entity){
		return new UsageUnitSetting(new JpaUsageUnitSettingGetMemento(entity));
	}
	
	
	/**
	 * To entitỵ̣̣.
	 *
	 * @param domain the domain
	 * @return the juuwtst usage unit wt set
	 */
	private JuuwtstUsageUnitWtSet toEntitỵ̣̣(UsageUnitSetting domain){
		JuuwtstUsageUnitWtSet entity = new JuuwtstUsageUnitWtSet();
		domain.saveToMemento(new JpaUsageUnitSettingSetMemento(entity));
		return entity;
	}

}
