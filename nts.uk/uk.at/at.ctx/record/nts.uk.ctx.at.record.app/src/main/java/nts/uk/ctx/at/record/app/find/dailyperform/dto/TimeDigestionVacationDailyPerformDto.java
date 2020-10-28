package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.TimeDigestOfDaily;

/** 日別実績の時間消化休暇 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeDigestionVacationDailyPerformDto implements ItemConst {

	/** 不足時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = SHORTAGE)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer shortageTime;

	/** 使用時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = USAGE)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer useTime;
	
	@Override
	public TimeDigestionVacationDailyPerformDto clone() {
		return new TimeDigestionVacationDailyPerformDto(shortageTime, useTime);
	}
	
	public static TimeDigestionVacationDailyPerformDto from(TimeDigestOfDaily domain) {
		return domain == null ? null: new TimeDigestionVacationDailyPerformDto(
				domain.getLeakageTime() == null ? 0 : domain.getLeakageTime().valueAsMinutes(), 
				domain.getUseTime() == null ? 0 : domain.getUseTime().valueAsMinutes());
	}
	
	public TimeDigestOfDaily toDomain() {
		return new TimeDigestOfDaily(useTime == null ? AttendanceTime.ZERO : new AttendanceTime(useTime),
									shortageTime == null ? AttendanceTime.ZERO : new AttendanceTime(shortageTime));
	}
	
	public static TimeDigestOfDaily defaultDomain() {
		return new TimeDigestOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO);
}	
}
