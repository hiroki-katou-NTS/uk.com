package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname;

import lombok.Data;

@Data
public class AttItemNameImport {

	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;

	private int userCanUpdateAtr;

	private int typeOfAttendanceItem;

	private int nameLineFeedPosition;

	private int frameCategory;

	private AttItemAuthority authority;

	public AttItemNameImport() {
		super();
	}
}
