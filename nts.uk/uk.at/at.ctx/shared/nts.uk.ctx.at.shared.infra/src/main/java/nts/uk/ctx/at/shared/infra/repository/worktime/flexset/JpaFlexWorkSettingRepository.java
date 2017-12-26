/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSetPK;

/**
 * The Class JpaFlexWorkSettingRepository.
 */
@Stateless
public class JpaFlexWorkSettingRepository extends JpaRepository
		implements FlexWorkSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#
	 * findById(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<FlexWorkSetting> find(String companyId, String worktimeCode) {
		return this.findWorkSetting(companyId, worktimeCode).map(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#
	 * saveFlexWorkSetting(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * FlexWorkSetting)
	 */
	@Override
	public void add(FlexWorkSetting domain) {
		KshmtFlexWorkSet entity = new KshmtFlexWorkSet();
		domain.saveToMemento(new JpaFlexWorkSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#
	 * update(nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting)
	 */
	@Override
	public void update(FlexWorkSetting domain) {
		KshmtFlexWorkSet entity = this.findWorkSetting(domain.getCompanyId(), domain.getWorkTimeCode().v()).get();
		domain.saveToMemento(new JpaFlexWorkSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#
	 * remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String workTimeCode) {
		this.commandProxy().remove(KshmtFlexWorkSet.class, new KshmtFlexWorkSetPK(companyId, workTimeCode));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the flex work setting
	 */
	private FlexWorkSetting toDomain(KshmtFlexWorkSet entity) {
		return new FlexWorkSetting(new JpaFlexWorkSettingGetMemento(entity));
	}

	
	/**
	 * Find work setting.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @return the optional
	 */
	private Optional<KshmtFlexWorkSet> findWorkSetting(String companyId, String worktimeCode) {
		return this.queryProxy().find(new KshmtFlexWorkSetPK(companyId, worktimeCode),
				KshmtFlexWorkSet.class);
	}

}
