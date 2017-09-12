package nts.uk.ctx.at.function.dom.dailyattendanceitem;

import lombok.Data;

@Data
public class DailyAttendanceItem {
	
	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;
	
	private int typeOfAttendanceItem;
	
	private int frameCategory;

	public DailyAttendanceItem() {
		super();
	}	

}
