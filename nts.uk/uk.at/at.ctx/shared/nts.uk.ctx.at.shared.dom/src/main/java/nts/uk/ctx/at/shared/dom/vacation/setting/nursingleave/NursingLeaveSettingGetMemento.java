/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface NursingVacationSettingGetMemento.
 */
public interface NursingLeaveSettingGetMemento {

	/**
     * Gets the company id.
     *
     * @return the company id
     */
    String getCompanyId();

	/**
     * Gets the manage type.
     *
     * @return the manage type
     */
    ManageDistinct getManageType();

	/**
     * Gets the nursing category.
     *
     * @return the nursing category
     */
    NursingCategory getNursingCategory();

	/**
     * Gets the start month day.
     *
     * @return the start month day
     */
    Integer getStartMonthDay();

	/**
     * Gets the max person setting.
     *
     * @return the max person setting
     */
    MaxPersonSetting getMaxPersonSetting();

	/**
     * Gets the special holiday frame.
     *
     * @return the special holiday frame
     */
    Optional<Integer> getSpecialHolidayFrame();

	/**
     * Gets the work absence.
     *
     * @return the work absence
     */
    Optional<Integer> getWorkAbsence();
}
