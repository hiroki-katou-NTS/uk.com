/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaCommonRestSettingGetMemento;

/**
 * The Class JpaFlowWorkRestSettingGetMemento.
 */
public class JpaFlowWorkRestSettingGetMemento implements FlowWorkRestSettingGetMemento {

	/** The entity. */
	KshmtWtFloBrFlAll entity;
	
	/**
	 * Instantiates a new jpa flow work rest setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowWorkRestSettingGetMemento(KshmtWtFloBrFlAll entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingGetMemento#getCommonRestSetting()
	 */
	@Override
	public CommonRestSetting getCommonRestSetting() {
		return new CommonRestSetting(new JpaCommonRestSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingGetMemento#getFlowRestSetting()
	 */
	@Override
	public FlowWorkRestSettingDetail getFlowRestSetting() {
		return new FlowWorkRestSettingDetail(new JpaFlowWorkRestSettingDetailGetMemento(this.entity));
	}
}
