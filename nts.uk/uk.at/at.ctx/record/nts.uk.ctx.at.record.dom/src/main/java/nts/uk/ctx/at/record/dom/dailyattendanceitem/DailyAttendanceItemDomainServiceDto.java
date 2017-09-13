package nts.uk.ctx.at.record.dom.dailyattendanceitem;

import lombok.Data;

@Data
public class DailyAttendanceItemDomainServiceDto {
	
	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;

	public DailyAttendanceItemDomainServiceDto() {
		super();
	}	

}
