package nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto;

import lombok.Data;

@Data
public class AttdItemDto {

	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;
	
	private int nameLineFeedPosition;
	
	private int dailyAttendanceAtr;
	
	private int displayNumber;
	
	private int userCanUpdateAtr;
	

}