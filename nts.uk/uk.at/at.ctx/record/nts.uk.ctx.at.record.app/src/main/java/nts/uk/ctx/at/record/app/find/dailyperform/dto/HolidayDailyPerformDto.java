package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.AbsenceOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.YearlyReservedOfDaily;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の休暇 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDailyPerformDto {

	/** 年休: 日別実績の年休 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "年休")
	private HolidayUseTimeDto annualLeave;

	/** 特別休暇: 日別実績の特別休暇 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "特別休暇")
	private HolidayUseTimeDto specialHoliday;

	/** 超過有休: 日別実績の超過有休 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "超過有休")
	private HolidayUseTimeDto excessSalaries;

	/** 代休: 日別実績の代休 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "代休")
	private HolidayUseTimeDto compensatoryLeave;

	/** 積立年休: 日別実績の積立年休 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "積立年休")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer retentionYearly;

	/** 時間消化休暇: 日別実績の時間消化休暇 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "時間消化休暇")
	private TimeDigestionVacationDailyPerformDto timeDigestionVacation;

	/** 欠勤: 日別実績の欠勤 */
	@AttendanceItemLayout(layout = "G", jpPropertyName = "欠勤")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer absence;
	
	public static HolidayDailyPerformDto from(HolidayOfDaily domain) {
		return domain == null ? null : 
			new HolidayDailyPerformDto(HolidayUseTimeDto.from(domain.getAnnual()), 
					HolidayUseTimeDto.from(domain.getSpecialHoliday()), 
					HolidayUseTimeDto.from(domain.getOverSalary()), 
					HolidayUseTimeDto.from(domain.getSubstitute()), 
					domain.getYearlyReserved() == null ? null : fromTime(domain.getYearlyReserved().getUseTime()), 
					TimeDigestionVacationDailyPerformDto.from(domain.getTimeDigest()), 
					domain.getAbsence() == null ? null : fromTime(domain.getAbsence().getUseTime()));
	}

	public HolidayOfDaily toDomain() {
		return new HolidayOfDaily(absence == null ? null : new AbsenceOfDaily(new AttendanceTime(absence)),
				timeDigestionVacation == null ? null : timeDigestionVacation.toDomain(),
				retentionYearly == null ? null : new YearlyReservedOfDaily(new AttendanceTime(retentionYearly)),
				compensatoryLeave == null ? null : compensatoryLeave.toSubstituteHoliday(),
				excessSalaries == null ? null : excessSalaries.toOverSalary(), 
				specialHoliday == null ? null : specialHoliday.toSpecialHoliday(),
				annualLeave == null ? null : annualLeave.toAnnualOfDaily());
	}
	
	private static Integer fromTime(AttendanceTime time) {
		return time == null ? null : time.valueAsMinutes();
	}
}
