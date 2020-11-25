package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の所定労働時間 */
public class PrescribedWorkingTimeOfMonthlyDto implements ItemConst {

	/** 計画所定労働時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = PLAN, layout = LAYOUT_A)
	private int scheduleTime;

	/** 実績所定労働時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = ACTUAL, layout = LAYOUT_B)
	private int recordTime;
	
	public PrescribedWorkingTimeOfMonthly toDomain() {
		return PrescribedWorkingTimeOfMonthly.of(
							new AttendanceTimeMonth(scheduleTime),
							new AttendanceTimeMonth(recordTime));
	}
	
	public static PrescribedWorkingTimeOfMonthlyDto from(PrescribedWorkingTimeOfMonthly domain) {
		PrescribedWorkingTimeOfMonthlyDto dto = new PrescribedWorkingTimeOfMonthlyDto();
		if(domain != null) {
			dto.setRecordTime(domain.getRecordPrescribedWorkingTime() == null ? 0 : domain.getRecordPrescribedWorkingTime().valueAsMinutes());
			dto.setScheduleTime(domain.getSchedulePrescribedWorkingTime() == null ? 0 : domain.getSchedulePrescribedWorkingTime().valueAsMinutes());
		}
		return dto;
	}
}
