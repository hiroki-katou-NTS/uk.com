package nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto;

import lombok.Data;

@Data
public class AttendanceItemDto {

	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;
	
	public AttendanceItemDto() {
		super();
	}

	public AttendanceItemDto(int attendanceItemId, String attendanceItemName, int attendanceItemDisplayNumber) {
		super();
		this.attendanceItemId = attendanceItemId;
		this.attendanceItemName = attendanceItemName;
		this.attendanceItemDisplayNumber = attendanceItemDisplayNumber;
	}
}
