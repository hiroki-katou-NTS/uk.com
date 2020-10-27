/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFl;
import nts.uk.ctx.at.shared.infra.repository.worktime.flexset.JpaFlexFlowWorkRestSettingDetailGetMemento;

/**
 * The Class JpaFlexFWRestSettingGetMemento.
 */
public class JpaFlexFWRestSettingGetMemento implements FlowWorkRestSettingGetMemento{
	
	/** The entity. */
	private KshmtWtFleBrFl entity;
	

	/**
	 * Instantiates a new jpa flex FW rest setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexFWRestSettingGetMemento(KshmtWtFleBrFl entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingGetMemento#
	 * getCommonRestSetting()
	 */
	@Override
	public CommonRestSetting getCommonRestSetting() {
		return new CommonRestSetting(new JpaFlexCommonRestSettingGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingGetMemento#
	 * getFlowRestSetting()
	 */
	@Override
	public FlowWorkRestSettingDetail getFlowRestSetting() {
		return new FlowWorkRestSettingDetail(new JpaFlexFlowWorkRestSettingDetailGetMemento(this.entity));
	}

}
