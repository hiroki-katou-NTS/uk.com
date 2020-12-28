package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;

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
	 * Sets the remark input no.
	 *
	 * @param remarkInputNo the new remark input no
	 */
	void setRemarkInputNo(RemarkInputContent remarkInputNo);
	
	/**
	 * Sets the layout ID.
	 *
	 * @param layoutID the new layoutID
	 */
	void setLayoutID(String layoutID);
	
	/**
	 * Sets the employee ID.
	 *
	 * @param employeeID the new employeeID
	 */
	void setEmployeeID(String employeeID);
	
	/**
	 * Sets the character size.
	 *
	 * @param characterSize the new character size
	 */
	void setTextSize(TextSizeCommonEnum textSize);
	
	/**
	 * Sets the item selection enum.
	 *
	 * @param itemSelectionEnum the new item selection enum
	 */
	void setItemSelectionEnum(ItemSelectionEnum itemSelectionEnum);
	
	/**
	 * Sets the remarkInput.
	 *
	 * @param remarkInput the new remarkInput
	 */
	void setIsRemarkPrinted(Boolean isRemarkPrinted);
	
}
