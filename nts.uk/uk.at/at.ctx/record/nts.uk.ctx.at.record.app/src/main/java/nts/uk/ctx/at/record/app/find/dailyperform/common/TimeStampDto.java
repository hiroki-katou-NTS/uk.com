package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeStampDto {

	/** 時刻 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時刻", needCheckIDWithIndex = true)
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer timesOfDay;

	/** 丸め後の時刻 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "丸め後の時刻", needCheckIDWithIndex = true)
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer afterRoundingTimesOfDay;

	/** 場所コード */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "場所コード", needCheckIDWithIndex = true)
	@AttendanceItemValue
	private String placeCode;
	
	
	private Integer stampSourceInfo;
}
