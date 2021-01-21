/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaCommonRestSettingSetMemento;

/**
 * The Class JpaFlowWorkRestSettingSetMemento.
 */
public class JpaFlowWorkRestSettingSetMemento implements FlowWorkRestSettingSetMemento {

	/** The entity. */
	KshmtWtFloBrFlAll entity;

	/**
	 * Instantiates a new jpa flow work rest setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowWorkRestSettingSetMemento(KshmtWtFloBrFlAll entity) {
		super();
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
		commonRest.saveToMemento(new JpaCommonRestSettingSetMemento(this.entity));
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
		flowRest.saveToMemento(new JpaFlowWorkRestSettingDetailSetMemento(this.entity));
	}

}
