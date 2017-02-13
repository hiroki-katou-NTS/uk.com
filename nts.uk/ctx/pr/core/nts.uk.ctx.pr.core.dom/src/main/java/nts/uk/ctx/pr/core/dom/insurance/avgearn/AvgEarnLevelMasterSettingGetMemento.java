/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.avgearn;

/**
 * The Interface AvgEarnLevelMasterSettingGetMemento.
 */
public interface AvgEarnLevelMasterSettingGetMemento {

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	Integer getCode();

	/**
	 * Gets the health level.
	 *
	 * @return the health level
	 */
	Integer getHealthLevel();

	/**
	 * Gets the pension level.
	 *
	 * @return the pension level
	 */
	Integer getPensionLevel();

	/**
	 * Gets the avg earn.
	 *
	 * @return the avg earn
	 */
	Long getAvgEarn();

	/**
	 * Gets the sal limit.
	 *
	 * @return the sal limit
	 */
	Long getSalLimit();

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	Long getVersion();

}
