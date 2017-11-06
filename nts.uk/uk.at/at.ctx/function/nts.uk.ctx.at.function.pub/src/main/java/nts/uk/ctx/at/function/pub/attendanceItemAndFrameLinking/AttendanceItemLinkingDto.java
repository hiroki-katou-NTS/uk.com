package nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking;

import lombok.Value;

@Value
public class AttendanceItemLinkingDto {
	
	private int attendanceItemId;
	
	private int frameNo;
	
	private int frameCategory;
}
