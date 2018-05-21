/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AttendanceItemsDisplay.
 * @author HoangDD
 */
// 表示する勤怠項目
@Getter
public class AttendanceItemsDisplay extends DomainObject{
	
	/** The order no. */
	// 並び順
	private int orderNo;
	
	/** The attendance display. */
	// 表示する項目
	private int attendanceDisplay;

	/**
	 * Instantiates a new attendance items display.
	 *
	 * @param orderNo the order no
	 * @param attendanceDisplay the item to display
	 */
	public AttendanceItemsDisplay(int orderNo, int attendanceDisplay) {
		super();
		this.orderNo = orderNo;
		this.attendanceDisplay = attendanceDisplay;
	}
}
