package nts.uk.ctx.at.function.dom.attendanceitemname;

import lombok.Data;

@Data
public class AttendanceItemName {

	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;
	
	private int typeOfAttendanceItem;
	
	private int frameCategory;

	public AttendanceItemName() {
		super();
	}	
}
