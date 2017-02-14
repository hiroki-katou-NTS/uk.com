/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.avgearn;

import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSettingGetMemento;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaAvgEarnLevelMasterSettingGetMemento implements AvgEarnLevelMasterSettingGetMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaAvgEarnLevelMasterSettingGetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}
	@Override
	
	public Long getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getHealthLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getPensionLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getAvgEarn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getSalLimit() {
		// TODO Auto-generated method stub
		return null;
	}

}
