package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter;

import lombok.Value;

@Value
public class DailyAttendanceItemNameAdapterDto {
	
	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;

	private int typeOfAttendanceItem;
	
	private int frameCategory;

}
