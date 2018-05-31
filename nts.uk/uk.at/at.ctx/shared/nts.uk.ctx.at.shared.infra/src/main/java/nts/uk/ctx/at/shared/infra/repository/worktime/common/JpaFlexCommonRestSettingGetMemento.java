/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingGetmemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSetPK;

/**
 * The Class JpaFlexCommonRestSettingGetMemento.
 */
public class JpaFlexCommonRestSettingGetMemento implements CommonRestSettingGetmemento {

	/** The entity. */
	private KshmtFlexRestSet entity;

	/**
	 * Instantiates a new jpa flex common rest setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexCommonRestSettingGetMemento(KshmtFlexRestSet entity) {
		super();
		if (entity.getKshmtFlexRestSetPK() == null) {
			entity.setKshmtFlexRestSetPK(new KshmtFlexRestSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingGetmemento#
	 * getCalculateMethod()
	 */
	@Override
	public RestTimeOfficeWorkCalcMethod getCalculateMethod() {
		return RestTimeOfficeWorkCalcMethod.valueOf(this.entity.getCommonCalculateMethod());
	}

}
