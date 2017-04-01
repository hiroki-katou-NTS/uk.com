/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.avgearn;

import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSettingSetMemento;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaAvgEarnLevelMasterSettingSetMemento implements AvgEarnLevelMasterSettingSetMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaAvgEarnLevelMasterSettingSetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCode(Integer code) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHealthLevel(Integer healthLevel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPensionLevel(Integer pensionLevel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAvgEarn(Long avgEarn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSalLimit(Long salLimit) {
		// TODO Auto-generated method stub

	}

}
