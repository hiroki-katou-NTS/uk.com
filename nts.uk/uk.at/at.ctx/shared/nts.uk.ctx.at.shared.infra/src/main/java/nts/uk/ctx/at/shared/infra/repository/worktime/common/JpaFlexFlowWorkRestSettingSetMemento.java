/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSetPK;
import nts.uk.ctx.at.shared.infra.repository.worktime.flexset.JpaFlexFlowWorkRestSettingDetailSetMemento;

/**
 * The Class JpaFlexFlowWorkRestSettingSetMemento.
 */
public class JpaFlexFlowWorkRestSettingSetMemento implements FlowWorkRestSettingSetMemento{
	
	/** The entity. */
	private KshmtFlexRestSet entity;
	
	/**
	 * Instantiates a new jpa flex flow work rest setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexFlowWorkRestSettingSetMemento(KshmtFlexRestSet entity) {
		super();
		if(entity.getKshmtFlexRestSetPK() == null){
			entity.setKshmtFlexRestSetPK(new KshmtFlexRestSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingSetMemento#
	 * setCommonRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * CommonRestSetting)
	 */
	@Override
	public void setCommonRestSetting(CommonRestSetting commonRest) {
		commonRest.saveToMemento(new JpaFlexCommonRestSettingSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingSetMemento#
	 * setFlowRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetail)
	 */
	@Override
	public void setFlowRestSetting(FlowWorkRestSettingDetail flowRest) {
		flowRest.saveToMemento(new JpaFlexFlowWorkRestSettingDetailSetMemento(this.entity));
	}

}
