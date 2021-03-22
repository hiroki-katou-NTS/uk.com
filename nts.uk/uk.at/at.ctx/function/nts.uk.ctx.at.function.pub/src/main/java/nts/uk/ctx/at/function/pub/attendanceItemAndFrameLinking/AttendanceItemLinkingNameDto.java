package nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking;

import lombok.Value;

@Value
public class AttendanceItemLinkingNameDto {
	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;

	private Integer typeOfAttendanceItem;

	private Integer frameCategory;
}