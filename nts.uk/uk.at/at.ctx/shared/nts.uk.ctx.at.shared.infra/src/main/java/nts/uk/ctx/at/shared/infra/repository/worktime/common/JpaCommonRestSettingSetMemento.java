/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingSetmemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;

/**
 * The Class JpaCommonRestSettingSetMemento.
 */
public class JpaCommonRestSettingSetMemento implements CommonRestSettingSetmemento {

	/** The entity. */
	KshmtWtFloBrFlAll entity;

	/**
	 * Instantiates a new jpa common rest setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaCommonRestSettingSetMemento(KshmtWtFloBrFlAll entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingSetmemento#
	 * setCalculateMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * RestTimeOfficeWorkCalcMethod)
	 */
	@Override
	public void setCalculateMethod(RestTimeOfficeWorkCalcMethod method) {
		this.entity.setCommonCalculateMethod(method.value);
	}

}
