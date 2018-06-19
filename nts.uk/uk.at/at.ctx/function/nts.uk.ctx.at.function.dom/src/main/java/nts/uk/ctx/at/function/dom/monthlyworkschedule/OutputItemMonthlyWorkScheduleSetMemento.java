package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import java.util.List;

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
	void setItemCode(MonthlyOutputItemSettingCode itemCode);

	/**
	 * Sets the item name.
	 *
	 * @param itemName the new item name
	 */
	void setItemName(MonthlyOutputItemSettingName itemName);

	/**
	 * Sets the lst displayed attendance.
	 *
	 * @param lstDisplayAttendance the new lst displayed attendance
	 */
	void setLstDisplayedAttendance(List<MonthlyAttendanceItemsDisplay> lstDisplayAttendance);

	/**
	 * Sets the prints the remarks column.
	 *
	 * @param printSettingRemarksColumn the new prints the remarks column
	 */
	void setPrintRemarksColumn(PrintSettingRemarksColumn printSettingRemarksColumn);
}
