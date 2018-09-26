package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.medical.MedicalCareTimeOfDaily;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の医療時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalTimeDailyPerformDto implements ItemConst {

	/** 日勤夜勤区分: 日勤夜勤区分 */
	/**
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift 日勤
	 *      Day_Shift(0), 夜勤 Night_Shift(1)
	 */
	// @AttendanceItemLayout(layout = "A")
	// @AttendanceItemValue(type = ValueType.INTEGER)
	private int attr;

	/** 申送時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TAKE_OVER, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer takeOverTime;

	/** 控除時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = DEDUCTION, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer deductionTime;

	/** 勤務時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = WORKING_TIME, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer workTime;

	/** @see nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift */
	public String enumText() {
		switch (this.attr) {
		case 0:
			return E_DAY_WORK;
		case 1:
			return E_NIGHT_WORK;
		default:
			return EMPTY_STRING;
		}
	}
	
	@Override
	public MedicalTimeDailyPerformDto clone() {
		return new MedicalTimeDailyPerformDto(attr, takeOverTime, deductionTime, workTime);
	}

	public static MedicalTimeDailyPerformDto fromMedicalCareTime(MedicalCareTimeOfDaily domain) {
		return domain == null ? null : new MedicalTimeDailyPerformDto(
											domain.getDayNightAtr() == null ? 0 : domain.getDayNightAtr().value,
											getAttendanceTime(domain.getTakeOverTime()),
											getAttendanceTime(domain.getDeductionTime()),
											getAttendanceTime(domain.getWorkTime()));
	}

	private static Integer getAttendanceTime(AttendanceTime domain) {
		return domain == null ? null : domain.valueAsMinutes();
	}
}
