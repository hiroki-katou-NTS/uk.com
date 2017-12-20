package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@Data
public class TimeStampDto {

	/** 時刻 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時刻", needCheckIDWithIndex = true)
	@AttendanceItemValue(type = ValueType.INTEGER, getIdFromUtil = true)
	private Integer timesOfDay;

	/** 丸め後の時刻 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "丸め後の時刻")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer afterRoundingTimesOfDay;

	/** 場所コード */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "場所コード", needCheckIDWithIndex = true)
	@AttendanceItemValue(getIdFromUtil = true)
	private String placeCode;
}
