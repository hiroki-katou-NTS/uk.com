package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 期間別の総拘束時間 */
public class AggregateTotalTimeSpentAtWorkDto implements ItemConst {

	/** 拘束残業時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = OVERTIME, layout = LAYOUT_A)
	private Integer overTime;

	/** 拘束深夜時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = LATE_NIGHT, layout = LAYOUT_B)
	private Integer midnightTime;

	/** 拘束休出時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_C)
	private Integer holidayTime;

	/** 拘束差異時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = DIFF, layout = LAYOUT_D)
	private Integer varienceTime;

	/** 総拘束時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_E)
	private Integer totalTime;

	public AggregateTotalTimeSpentAtWork toDomain() {
		return AggregateTotalTimeSpentAtWork.of(overTime == null ? null : new AttendanceTimeMonth(overTime),
												midnightTime == null ? null : new AttendanceTimeMonth(midnightTime),
												holidayTime == null ? null : new AttendanceTimeMonth(holidayTime),
												varienceTime == null ? null : new AttendanceTimeMonth(varienceTime),
												totalTime == null ? null : new AttendanceTimeMonth(totalTime));
	}
	
	public static AggregateTotalTimeSpentAtWorkDto from(AggregateTotalTimeSpentAtWork domain) {
		AggregateTotalTimeSpentAtWorkDto dto = new AggregateTotalTimeSpentAtWorkDto();
		if(domain != null) {
			dto.setHolidayTime(domain.getHolidayTimeSpentAtWork() == null ? null : domain.getHolidayTimeSpentAtWork().valueAsMinutes());
			dto.setMidnightTime(domain.getMidnightTimeSpentAtWork() == null ? null : domain.getMidnightTimeSpentAtWork().valueAsMinutes());
			dto.setOverTime(domain.getOverTimeSpentAtWork() == null ? null : domain.getOverTimeSpentAtWork().valueAsMinutes());
			dto.setTotalTime(domain.getTotalTimeSpentAtWork() == null ? null : domain.getTotalTimeSpentAtWork().valueAsMinutes());
			dto.setVarienceTime(domain.getVarienceTimeSpentAtWork() == null ? null : domain.getVarienceTimeSpentAtWork().valueAsMinutes());
		}
		return dto;
	}
}
