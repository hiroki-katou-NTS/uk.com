package nts.uk.ctx.at.record.app.find.dailyperform.goout.dto;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@Data
public class OutingTimeZoneDto {

	private Integer workNo;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "外出")
	private TimeStampDto outing;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "戻り")
	private TimeStampDto comeBack;

	@AttendanceItemLayout(layout = "C", jpPropertyName = "外出理由")
	@AttendanceItemValue(itemId = { 86, 149, 93, 100, 107, 114, 121, 128, 135, 142 }, type = ValueType.INTEGER)
	private int reason;
}
