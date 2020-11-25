/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.List;

/**
 * The Interface OutputItemDailyWorkScheduleGetMemento.
 * @author HoangDD
 */
public interface OutputItemDailyWorkScheduleGetMemento {

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
	 * Gets the lst remark content.
	 *
	 * @return the lst remark content
	 */
	List<PrintRemarksContent> getLstRemarkContent();
	
	/**
	 * Gets the work type name display.
	 *
	 * @return the work type name display
	 */
	NameWorkTypeOrHourZone getWorkTypeNameDisplay();
	
	/**
	 * Gets the remark input no.
	 *
	 * @return the remark input no
	 */
	RemarkInputContent getRemarkInputNo();
	
	/**
	 * Gets the font size.
	 *
	 * @return the font size
	 */
	FontSizeEnum getFontSize();
	
	/**
	 * Gets the layout id.
	 *
	 * @return the layout id
	 */
	String getLayoutId();
}
