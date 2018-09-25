package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname;

import lombok.Data;

@Data
public class AttItemName {

	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;

	private int userCanUpdateAtr;

	private Integer typeOfAttendanceItem;

	private int nameLineFeedPosition;

	private Integer frameCategory;

	private AttItemAuthority authority;

	public AttItemName() {
		super();
	}
}
