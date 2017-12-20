package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem;

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
