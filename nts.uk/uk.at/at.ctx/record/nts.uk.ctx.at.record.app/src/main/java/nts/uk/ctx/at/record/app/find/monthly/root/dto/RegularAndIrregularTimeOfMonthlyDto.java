package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の通常変形時間 */
public class RegularAndIrregularTimeOfMonthlyDto {

	/** 週割増合計時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "週割増合計時間", layout = "A")
	private Integer weeklyTotalPremiumTime;

	/** 月割増合計時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "月割増合計時間", layout = "B")
	private Integer monthlyTotalPremiumTime;

	/** 変形労働時間 */
	@AttendanceItemLayout(jpPropertyName = "変形労働時間", layout = "C")
	private IrregularWorkingTimeOfMonthlyDto irregularWorkingTime;

	public RegularAndIrregularTimeOfMonthly toDomain() {
		return RegularAndIrregularTimeOfMonthly.of(
				weeklyTotalPremiumTime == null ? null : new AttendanceTimeMonth(weeklyTotalPremiumTime), 
				monthlyTotalPremiumTime == null ? null : new AttendanceTimeMonth(monthlyTotalPremiumTime),
				irregularWorkingTime == null ? null : irregularWorkingTime.toDomain());
	}
	
	public static RegularAndIrregularTimeOfMonthlyDto from(RegularAndIrregularTimeOfMonthly domain) {
		RegularAndIrregularTimeOfMonthlyDto dto = new RegularAndIrregularTimeOfMonthlyDto();
		if(domain != null) {
			dto.setIrregularWorkingTime(IrregularWorkingTimeOfMonthlyDto.from(domain.getIrregularWorkingTime()));
			dto.setMonthlyTotalPremiumTime(domain.getMonthlyTotalPremiumTime() == null 
					? null : domain.getMonthlyTotalPremiumTime().valueAsMinutes());
			dto.setWeeklyTotalPremiumTime(domain.getWeeklyTotalPremiumTime() == null 
					? null : domain.getWeeklyTotalPremiumTime().valueAsMinutes());
		}
		return dto;
	}
}
