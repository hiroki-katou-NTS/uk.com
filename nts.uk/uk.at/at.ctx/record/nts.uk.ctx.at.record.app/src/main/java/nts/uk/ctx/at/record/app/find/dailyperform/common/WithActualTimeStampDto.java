package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 勤怠打刻(実打刻付き) */
@AllArgsConstructor
@NoArgsConstructor
public class WithActualTimeStampDto {

	@AttendanceItemLayout(layout="A", jpPropertyName="打刻")
	private TimeStampDto time;

	@AttendanceItemLayout(layout="B", jpPropertyName="実打刻")
	private TimeStampDto actualTime;
	
	/** 打刻反映回数 */
	// @AttendanceItemLayout(layout = "C")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private int numberOfReflectionStamp;
}
