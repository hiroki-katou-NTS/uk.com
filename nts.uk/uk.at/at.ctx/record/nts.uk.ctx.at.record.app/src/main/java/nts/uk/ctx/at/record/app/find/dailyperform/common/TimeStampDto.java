package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 勤怠打刻 */
@AllArgsConstructor
@NoArgsConstructor
public class TimeStampDto implements ItemConst {

	/** 時刻 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = CLOCK)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer timesOfDay;

	/** 丸め後の時刻 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = ROUNDING)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer afterRoundingTimesOfDay;

	/** 場所コード */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = PLACE)
	@AttendanceItemValue
	private String placeCode;
	
	private Integer stampSourceInfo;
	
	public static TimeStampDto createTimeStamp(WorkStamp c) {
		return c == null ? null : new TimeStampDto(
				c.getTimeWithDay() == null ? null : c.getTimeWithDay().valueAsMinutes(),
				c.getAfterRoundingTime() == null ? null : c.getAfterRoundingTime().valueAsMinutes(),
				!c.getLocationCode().isPresent() ? null : c.getLocationCode().get().v(),
				c.getStampSourceInfo() == null ? null : c.getStampSourceInfo().value);
	}
}
