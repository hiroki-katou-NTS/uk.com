package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.WorkTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の就業時間 */
public class WorkingTimeOfMonthlyDto implements ItemConst {

	/** 就業時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = WORK_TIME, layout = LAYOUT_A)
	private Integer workTime;

	/** 所定内割増時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = PREMIUM, layout = LAYOUT_B)
	private Integer withinPrescribedPremiumTime;
	
	/** 実働就業時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = ACTUAL, layout = LAYOUT_C)
	private Integer actualWorkTime;
	
	public static WorkingTimeOfMonthlyDto from(WorkTimeOfMonthly domain) {
		WorkingTimeOfMonthlyDto dto = new WorkingTimeOfMonthlyDto();
		if(domain != null) {
			dto.setWithinPrescribedPremiumTime(domain.getWithinPrescribedPremiumTime() == null 
					? null : domain.getWithinPrescribedPremiumTime().valueAsMinutes());
			dto.setWorkTime(domain.getWorkTime() == null ? null : domain.getWorkTime().valueAsMinutes());
			dto.setActualWorkTime(domain.getActualWorkTime() == null ? null : domain.getActualWorkTime().valueAsMinutes());
		}
		return dto;
	}
	
	public WorkTimeOfMonthly toDomain() {
		return WorkTimeOfMonthly.of(workTime == null ? null : new AttendanceTimeMonth(workTime),
									withinPrescribedPremiumTime == null ? null : new AttendanceTimeMonth(withinPrescribedPremiumTime),
									actualWorkTime == null ? null : new AttendanceTimeMonth(actualWorkTime));
	}
}
