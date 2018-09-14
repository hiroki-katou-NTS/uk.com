package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.OverSalaryOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の特別休暇 / 日別実績の年休 / 日別実績の超過有休 / 日別実績の代休 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayUseTimeDto implements ItemConst {

	/** 時間消化休暇使用時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME_DIGESTION)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer timeDigestionVacationUseTime;

	/** 使用時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = USAGE)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer useTime;
	
	@Override
	public HolidayUseTimeDto clone() {
		return new HolidayUseTimeDto(timeDigestionVacationUseTime, useTime);
	}

	public SpecialHolidayOfDaily toSpecialHoliday() {
		return new SpecialHolidayOfDaily(toUseAttendanceTime(),
				toDigestionTIme());
	}
	
	public SubstituteHolidayOfDaily toSubstituteHoliday() {
		return new SubstituteHolidayOfDaily(toUseAttendanceTime(),
				toDigestionTIme());
	}
	
	public OverSalaryOfDaily toOverSalary() {
		return new OverSalaryOfDaily(toUseAttendanceTime(),
				toDigestionTIme());
	}
	
	public AnnualOfDaily toAnnualOfDaily() {
		return new AnnualOfDaily(toUseAttendanceTime(),
				toDigestionTIme());
	}
	
	public static HolidayUseTimeDto from(SpecialHolidayOfDaily domain) {
		return domain == null ? null : new HolidayUseTimeDto(fromTime(domain.getDigestionUseTime()),
				fromTime(domain.getUseTime()));
	}
	
	public static HolidayUseTimeDto from(SubstituteHolidayOfDaily domain) {
		return domain == null ? null : new HolidayUseTimeDto(fromTime(domain.getDigestionUseTime()),
				fromTime(domain.getUseTime()));
	}
	
	public static HolidayUseTimeDto from(OverSalaryOfDaily domain) {
		return domain == null ? null : new HolidayUseTimeDto(fromTime(domain.getDigestionUseTime()),
				fromTime(domain.getUseTime()));
	}
	
	public static HolidayUseTimeDto from(AnnualOfDaily domain) {
		return domain == null ? null : new HolidayUseTimeDto(fromTime(domain.getDigestionUseTime()),
				fromTime(domain.getUseTime()));
	}
	
	private static Integer fromTime(AttendanceTime time) {
		return time == null ? 0 : time.valueAsMinutes();
	}

	private AttendanceTime toDigestionTIme() {
		return timeDigestionVacationUseTime == null ? AttendanceTime.ZERO : new AttendanceTime(timeDigestionVacationUseTime);
	}

	private AttendanceTime toUseAttendanceTime() {
		return useTime == null ? AttendanceTime.ZERO : new AttendanceTime(useTime);
	}
}
