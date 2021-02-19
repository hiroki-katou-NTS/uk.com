package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.AggregateTotalTimeSpentAtWork;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 期間別の総拘束時間 */
public class AggregateTotalTimeSpentAtWorkDto implements ItemConst {

	/** 拘束残業時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = OVERTIME, layout = LAYOUT_A)
	private int overTime;

	/** 拘束深夜時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LATE_NIGHT, layout = LAYOUT_B)
	private int midnightTime;

	/** 拘束休出時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_C)
	private int holidayTime;

	/** 拘束差異時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = DIFF, layout = LAYOUT_D)
	private int varienceTime;

	/** 総拘束時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_E)
	private int totalTime;

	public AggregateTotalTimeSpentAtWork toDomain() {
		return AggregateTotalTimeSpentAtWork.of(new AttendanceTimeMonth(overTime),
												new AttendanceTimeMonth(midnightTime),
												new AttendanceTimeMonth(holidayTime),
												new AttendanceTimeMonth(varienceTime),
												new AttendanceTimeMonth(totalTime));
	}
	
	public static AggregateTotalTimeSpentAtWorkDto from(AggregateTotalTimeSpentAtWork domain) {
		AggregateTotalTimeSpentAtWorkDto dto = new AggregateTotalTimeSpentAtWorkDto();
		if(domain != null) {
			dto.setHolidayTime(domain.getHolidayTimeSpentAtWork() == null ? 0 : domain.getHolidayTimeSpentAtWork().valueAsMinutes());
			dto.setMidnightTime(domain.getMidnightTimeSpentAtWork() == null ? 0 : domain.getMidnightTimeSpentAtWork().valueAsMinutes());
			dto.setOverTime(domain.getOverTimeSpentAtWork() == null ? 0 : domain.getOverTimeSpentAtWork().valueAsMinutes());
			dto.setTotalTime(domain.getTotalTimeSpentAtWork() == null ? 0 : domain.getTotalTimeSpentAtWork().valueAsMinutes());
			dto.setVarienceTime(domain.getVarienceTimeSpentAtWork() == null ? 0 : domain.getVarienceTimeSpentAtWork().valueAsMinutes());
		}
		return dto;
	}
}
