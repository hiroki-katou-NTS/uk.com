/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface CompensatoryLeaveComGetMemento.
 */
public interface CompensatoryLeaveComGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the checks if is managed.
	 *
	 * @return the checks if is managed
	 */
	ManageDistinct getIsManaged();

	/**
	 * Gets the compensatory acquisition use.
	 *
	 * @return the compensatory acquisition use
	 */
	CompensatoryAcquisitionUse getCompensatoryAcquisitionUse();

	/**
	 * Gets the compensatory digestive time unit.
	 *
	 * @return the compensatory digestive time unit
	 */
	CompensatoryDigestiveTimeUnit getCompensatoryDigestiveTimeUnit();

	/**
	 * Gets the compensatory occurrence setting.
	 *
	 * @return the compensatory occurrence setting
	 */
	List<CompensatoryOccurrenceSetting> getCompensatoryOccurrenceSetting();

	// 代休発生設定 ---
	SubstituteHolidaySetting getSubstituteHolidaySetting();
	// 紐付け管理区分
	ManageDistinct getLinkingManagementATR();
}
