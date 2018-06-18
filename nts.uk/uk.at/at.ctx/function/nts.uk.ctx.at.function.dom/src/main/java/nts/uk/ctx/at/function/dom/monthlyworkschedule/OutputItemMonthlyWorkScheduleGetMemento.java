package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;

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
	OutputItemSettingCode getItemCode();
	
	/**
	 * Gets the item name.
	 *
	 * @return the item name
	 */
	OutputItemSettingName getItemName();
	
	/**
	 * Gets the lst displayed attendance.
	 *
	 * @return the lst displayed attendance
	 */
	List<AttendanceItemsDisplay> getLstDisplayedAttendance();
	
	/**
	 * Gets the prints the setting remarks column.
	 *
	 * @return the prints the setting remarks column
	 */
	PrintSettingRemarksColumn getPrintSettingRemarksColumn();

}
