package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;

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
	 * Gets the remark input no.
	 *
	 * @return the remark input no
	 */
	RemarkInputContent getRemarkInputNo();
	
	/**
	 * Gets the layout ID.
	 *
	 * @return the layout ID
	 */
	String getLayoutID();
	 
	/**
	 * Gets the employee ID.
	 *
	 * @return the employee ID
	 */
	String getEmployeeID();
	
	/**
	 * Gets the text size.
	 *
	 * @return the text size
	 */
	TextSizeCommonEnum getTextSize();
	
	/**
	 * Gets item selection type.
	 *
	 * @return item selection type
	 */
	ItemSelectionEnum getItemSelectionEnum();
	/**
	 * Gets getIsRemarkPrinted.
	 *
	 * @return boolean
	 */
	Boolean getIsRemarkPrinted();
}
