package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;

/** 外出時間帯 */
@Data
public class GoOutTimeDto {

	/** 戻り: 勤怠打刻(実打刻付き) */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "戻り")
	private WithActualTimeStampDto comeBack;

	/** 外出: 勤怠打刻(実打刻付き) */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "外出")
	private WithActualTimeStampDto outing;

	/** 外出時間: 勤怠時間 */
//	@AttendanceItemLayout(layout = "C")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer outingTime;

	/** 外出枠NO: 外出枠NO */
//	@AttendanceItemLayout(layout = "D")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer outingFrameNo;

	/** 外出理由: 外出理由 */
//	@AttendanceItemLayout(layout = "E")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer outingReason;
}
