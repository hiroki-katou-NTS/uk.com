package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 勤怠打刻(実打刻付き) */
@Data
public class ActualTimeStampDto {

	/** 打刻: 勤怠打刻 */
	@AttendanceItemLayout(layout = "A")
	private TimeStampDto stamp;

	/** 実打刻: 勤怠打刻 */
	@AttendanceItemLayout(layout = "B")
	private TimeStampDto actualStamp;

	/** 打刻反映回数 */
	@AttendanceItemLayout(layout = "C")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer numberOfReflectionStamp;
}
