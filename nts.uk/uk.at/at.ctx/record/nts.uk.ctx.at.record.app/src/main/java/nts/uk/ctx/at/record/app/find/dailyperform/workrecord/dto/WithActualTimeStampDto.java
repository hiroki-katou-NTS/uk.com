package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

@Data
/** 勤怠打刻(実打刻付き) */
public class WithActualTimeStampDto {

	@AttendanceItemLayout(layout="A", jpPropertyName="打刻")
	private TimeStampDto time;

	@AttendanceItemLayout(layout="B", jpPropertyName="実打刻")
	private TimeStampDto actualTime;
}
