package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import java.util.List;

/**
 * The Interface OutputItemMonthlyWorkScheduleGetMemento.
 */
public interface OutputItemMonthlyWorkScheduleGetMemento {
	
	/**
	 * Gets the company ID.
	 *
	 * @return the company ID
	 */
	String getCompanyID();
	
	/**
	 * Gets the item code.
	 *
	 * @return the item code
	 */
	MonthlyOutputItemSettingCode getItemCode();
	
	/**
	 * Gets the item name.
	 *
	 * @return the item name
	 */
	MonthlyOutputItemSettingName getItemName();
	
	/**
	 * Gets the lst displayed attendance.
	 *
	 * @return the lst displayed attendance
	 */
	List<MonthlyAttendanceItemsDisplay> getLstDisplayedAttendance();
	
	/**
	 * Gets the prints the setting remarks column.
	 *
	 * @return the prints the setting remarks column
	 */
	PrintSettingRemarksColumn getPrintSettingRemarksColumn();

}
