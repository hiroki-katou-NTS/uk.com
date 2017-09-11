package nts.uk.ctx.at.function.dom.dailyattendanceitem;

import lombok.Data;

@Data
public class DailyAttendanceItem {
	
	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;

	public DailyAttendanceItem() {
		super();
	}	

}
