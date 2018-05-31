/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingSetmemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSetPK;

/**
 * The Class JpaFlexCommonRestSettingGetMemento.
 */
public class JpaFlexCommonRestSettingSetMemento implements CommonRestSettingSetmemento{
	
	/** The entity. */
	private KshmtFlexRestSet entity;
	

	/**
	 * Instantiates a new jpa flex common rest setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexCommonRestSettingSetMemento(KshmtFlexRestSet entity) {
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
	 * nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingSetmemento#
	 * setCalculateMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * RestTimeOfficeWorkCalcMethod)
	 */
	@Override
	public void setCalculateMethod(RestTimeOfficeWorkCalcMethod method) {
		this.entity.setCommonCalculateMethod(method.value);
	}

}
