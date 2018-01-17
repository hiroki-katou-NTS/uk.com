/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowWorkSetPK;

/**
 * The Class JpaFlowWorkSettingRepository.
 */
@Stateless
public class JpaFlowWorkSettingRepository extends JpaRepository
		implements FlowWorkSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#find(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<FlowWorkSetting> find(String companyId, String workTimeCode) {
		// Query
		Optional<KshmtFlowWorkSet> optionalEntityTimeSet = this.queryProxy()
				.find(new KshmtFlowWorkSetPK(companyId, workTimeCode), KshmtFlowWorkSet.class);

		// Check exist
		if (!optionalEntityTimeSet.isPresent()) {
			return Optional.empty();
		}
		return Optional.ofNullable(new FlowWorkSetting(new JpaFlowWorkSettingGetMemento(optionalEntityTimeSet.get())));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository#add(nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting)
	 */
	@Override
	public void add(FlowWorkSetting domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository#update(nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting)
	 */
	@Override
	public void update(FlowWorkSetting domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String workTimeCode) {
		this.commandProxy().remove(KshmtFlowWorkSet.class, new KshmtFlowWorkSetPK(companyId, workTimeCode));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshmt flow work set
	 */
	private KshmtFlowWorkSet toEntity(FlowWorkSetting domain) {
		// Find entity
		Optional<KshmtFlowWorkSet> optional = this.queryProxy().find(
				new KshmtFlowWorkSetPK(domain.getCompanyId(), domain.getWorkingCode().v()), KshmtFlowWorkSet.class);

		KshmtFlowWorkSet entity;
		// check existed
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KshmtFlowWorkSet();
		}
		// save to memento
		domain.saveToMemento(new JpaFlowWorkSettingSetMemento(entity));
		return entity;
	}
}
