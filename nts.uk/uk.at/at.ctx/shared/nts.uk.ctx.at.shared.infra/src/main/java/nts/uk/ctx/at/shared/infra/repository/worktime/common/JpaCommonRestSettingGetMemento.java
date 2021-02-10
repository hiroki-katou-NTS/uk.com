/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingGetmemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;

/**
 * The Class JpaCommonRestSettingGetMemento.
 */
public class JpaCommonRestSettingGetMemento implements CommonRestSettingGetmemento {

	/** The entity. */
	KshmtWtFloBrFlAll entity;
	
	/**
	 * Instantiates a new jpa common rest setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaCommonRestSettingGetMemento(KshmtWtFloBrFlAll entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingGetmemento#getCalculateMethod()
	 */
	@Override
	public RestTimeOfficeWorkCalcMethod getCalculateMethod() {
		return RestTimeOfficeWorkCalcMethod.valueOf(this.entity.getCommonCalculateMethod());
	}
}
