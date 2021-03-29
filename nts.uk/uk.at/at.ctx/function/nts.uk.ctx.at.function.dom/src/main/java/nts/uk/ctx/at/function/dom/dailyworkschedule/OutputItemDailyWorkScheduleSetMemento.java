/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.List;

/**
 * The Interface OutputItemDailyWorkScheduleSetMemento.
 * @author HoangDD
 */
public interface OutputItemDailyWorkScheduleSetMemento {
	
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
	 * Sets the lst remark content.
	 *
	 * @param lstRemarkContent the new lst remark content
	 */
	void setLstRemarkContent(List<PrintRemarksContent> lstRemarkContent);
	
	/**
	 * Sets the work type name display.
	 *
	 * @param workTypeNameDisplay the new work type name display
	 */
	void setWorkTypeNameDisplay(NameWorkTypeOrHourZone workTypeNameDisplay);
	
	/**
	 * Sets the remark input no.
	 *
	 * @param remarkInputNo the new remark input no
	 */
	void setRemarkInputNo(RemarkInputContent remarkInputNo);
	
	/**
	 * Sets the font size.
	 *
	 * @param fontSize the new font size
	 */
	void setFontSize(FontSizeEnum fontSize);

	/**
	 * Sets the layout id.
	 *
	 * @param layoutId the new layout id
	 */
	void setLayoutId(String layoutId);
}
