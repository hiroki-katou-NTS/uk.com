package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import lombok.Data;

/**
 * The Class MonthlyAttendanceItemsDisplay.
 */
@Data
public class MonthlyAttendanceItemsDisplay {

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
	 * @param attendanceDisplay the attendance display
	 */
	public MonthlyAttendanceItemsDisplay(int orderNo, int attendanceDisplay) {
		super();
		this.orderNo = orderNo;
		this.attendanceDisplay = attendanceDisplay;
	}
	
	
}
