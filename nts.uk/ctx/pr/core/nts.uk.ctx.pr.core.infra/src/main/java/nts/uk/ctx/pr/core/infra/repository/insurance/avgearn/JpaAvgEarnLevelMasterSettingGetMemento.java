/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.avgearn;

import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSettingGetMemento;

/**
 * The Class JpaAvgEarnLevelMasterSettingGetMemento.
 */
public class JpaAvgEarnLevelMasterSettingGetMemento implements AvgEarnLevelMasterSettingGetMemento {

	/** The type value. */
	// TODO: Object -> entity class.
	private Object typeValue;

	/**
	 * Instantiates a new jpa avg earn level master setting get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaAvgEarnLevelMasterSettingGetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.avgearn.
	 * AvgEarnLevelMasterSettingGetMemento#getCode()
	 */
	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.avgearn.
	 * AvgEarnLevelMasterSettingGetMemento#getHealthLevel()
	 */
	@Override
	public Integer getHealthLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.avgearn.
	 * AvgEarnLevelMasterSettingGetMemento#getPensionLevel()
	 */
	@Override
	public Integer getPensionLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.avgearn.
	 * AvgEarnLevelMasterSettingGetMemento#getAvgEarn()
	 */
	@Override
	public Long getAvgEarn() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.avgearn.
	 * AvgEarnLevelMasterSettingGetMemento#getSalLimit()
	 */
	@Override
	public Long getSalLimit() {
		// TODO Auto-generated method stub
		return null;
	}

}
