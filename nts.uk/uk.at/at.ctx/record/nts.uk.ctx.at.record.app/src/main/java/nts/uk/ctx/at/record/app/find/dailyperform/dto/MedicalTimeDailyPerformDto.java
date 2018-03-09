package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.medical.MedicalCareTimeOfDaily;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 日別実績の医療時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalTimeDailyPerformDto {

	/** 日勤夜勤区分: 日勤夜勤区分 */
	/**
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift 日勤
	 *      Day_Shift(0), 夜勤 Night_Shift(1)
	 */
	// @AttendanceItemLayout(layout = "A")
	// @AttendanceItemValue(type = ValueType.INTEGER)
	private int dayNightAtr;

	/** 申送時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "申送時間", needCheckIDWithMethod = "dayNightAtr", methodForEnumValues = "dayNights")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer takeOverTime;

	/** 控除時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "控除時間", needCheckIDWithMethod = "dayNightAtr", methodForEnumValues = "dayNights")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer deductionTime;

	/** 勤務時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "勤務時間", needCheckIDWithMethod = "dayNightAtr", methodForEnumValues = "dayNights")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer workTime;

	/** @see nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift */
	public String dayNightAtr() {
		switch (this.dayNightAtr) {
		case 0:
			return "日勤";
		case 1:
			return "夜勤";
		default:
			return "";
		}
	}

	public void dayNightAtr(String text) {
		if (text.contains("日勤")) {
			this.dayNightAtr = 0;
		} else if (text.contains("夜勤")) {
			this.dayNightAtr = 1;
		}
	}

	public List<String> dayNights() {
		return Arrays.asList("日勤", "夜勤");
	}

	public static MedicalTimeDailyPerformDto fromMedicalCareTime(MedicalCareTimeOfDaily domain) {
		return domain == null ? null : new MedicalTimeDailyPerformDto(
						domain.getDayNightAtr() == null ? 0 : domain.getDayNightAtr().value,
						domain.getTakeOverTime() == null ? null : domain.getTakeOverTime().valueAsMinutes(),
						domain.getDeductionTime() == null ? null : domain.getDeductionTime().valueAsMinutes(),
						domain.getWorkTime() == null ? null : domain.getWorkTime().valueAsMinutes());
	}
}
