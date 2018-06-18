package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;

public interface OutputItemMonthlyWorkScheduleSetMemento {

	/**
	 * Sets the company ID.
	 *
	 * @param companyID the new company ID
	 */
	void setCompanyID(String companyID);
	
	/**
	 * Sets the item code.
	 *
	 * @param itemCode the new item code
	 */
	void setItemCode(OutputItemSettingCode itemCode);
	
	/**
	 * Sets the item name.
	 *
	 * @param itemName the new item name
	 */
	void setItemName(OutputItemSettingName itemName);
	
	/**
	 * Sets the lst displayed attendance.
	 *
	 * @param lstDisplayAttendance the new lst displayed attendance
	 */
	void setLstDisplayedAttendance(List<AttendanceItemsDisplay> lstDisplayAttendance);
	
	/**
	 * Sets the prints the remarks column.
	 *
	 * @param printSettingRemarksColumn the new prints the remarks column
	 */
	void setPrintRemarksColumn(PrintSettingRemarksColumn printSettingRemarksColumn);
}
