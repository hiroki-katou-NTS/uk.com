package nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttdItemDto {

	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;
	
	private int nameLineFeedPosition;
	
	private int dailyAttendanceAtr;
	
	private int displayNumber;
	
	private int userCanUpdateAtr;

	
	public AttdItemDto(int attendanceItemId, String attendanceItemName, int attendanceItemDisplayNumber,
			int nameLineFeedPosition, int dailyAttendanceAtr, int displayNumber, int userCanUpdateAtr) {
		super();
		this.attendanceItemId = attendanceItemId;
		this.attendanceItemName = attendanceItemName;
		this.attendanceItemDisplayNumber = attendanceItemDisplayNumber;
		this.nameLineFeedPosition = nameLineFeedPosition;
		this.dailyAttendanceAtr = dailyAttendanceAtr;
		this.displayNumber = displayNumber;
		this.userCanUpdateAtr = userCanUpdateAtr;
	}
	

}