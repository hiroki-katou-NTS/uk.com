/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingGetmemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFl;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlPK;

/**
 * The Class JpaFlexCommonRestSettingGetMemento.
 */
public class JpaFlexCommonRestSettingGetMemento implements CommonRestSettingGetmemento {

	/** The entity. */
	private KshmtWtFleBrFl entity;

	/**
	 * Instantiates a new jpa flex common rest setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexCommonRestSettingGetMemento(KshmtWtFleBrFl entity) {
		super();
		if (entity.getKshmtWtFleBrFlPK() == null) {
			entity.setKshmtWtFleBrFlPK(new KshmtWtFleBrFlPK());
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
