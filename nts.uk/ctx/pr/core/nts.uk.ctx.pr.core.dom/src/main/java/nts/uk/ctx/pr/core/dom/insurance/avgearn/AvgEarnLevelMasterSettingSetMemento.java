/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.avgearn;

/**
 * The Interface AvgEarnLevelMasterSettingSetMemento.
 */
public interface AvgEarnLevelMasterSettingSetMemento {

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	void setCode(Integer code);

	/**
	 * Sets the health level.
	 *
	 * @param healthLevel the new health level
	 */
	void setHealthLevel(Integer healthLevel);

	/**
	 * Sets the pension level.
	 *
	 * @param pensionLevel the new pension level
	 */
	void setPensionLevel(Integer pensionLevel);

	/**
	 * Sets the avg earn.
	 *
	 * @param avgEarn the new avg earn
	 */
	void setAvgEarn(Long avgEarn);

	/**
	 * Sets the sal limit.
	 *
	 * @param salLimit the new sal limit
	 */
	void setSalLimit(Long salLimit);

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	void setVersion(Long version);

}
