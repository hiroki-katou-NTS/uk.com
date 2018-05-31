package nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem;

import lombok.Data;

@Data
public class MonthlyAttdItemSharedDto {

	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;
	
	private int nameLineFeedPosition;
	
	private int dailyAttendanceAtr;
	
	private int displayNumber;
}
