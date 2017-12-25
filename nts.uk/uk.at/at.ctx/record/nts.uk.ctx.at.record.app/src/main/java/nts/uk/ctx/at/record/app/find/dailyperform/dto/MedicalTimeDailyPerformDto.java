package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.medical.MedicalCareTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の医療時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalTimeDailyPerformDto {

	
	/** 日勤夜勤区分: 日勤夜勤区分 */
	/** @see nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift 日勤 Day_Shift(0), 夜勤 Night_Shift(1) */
	// @AttendanceItemLayout(layout = "A")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private int dayNightAtr;

	/** 申送時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "申送時間", needCheckIDWithMethod = "dayNightAtr")
	@AttendanceItemValue(itemId = { 750, 751 }, type = ValueType.INTEGER, getIdFromUtil = true)
	private Integer takeOverTime;

	/** 控除時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "控除時間", needCheckIDWithMethod = "dayNightAtr")
	@AttendanceItemValue(itemId = { 752, 753 }, type = ValueType.INTEGER, getIdFromUtil = true)
	private Integer deductionTime;

	/** 勤務時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "勤務時間", needCheckIDWithMethod = "dayNightAtr")
	@AttendanceItemValue(itemId = { 754, 755 }, type = ValueType.INTEGER, getIdFromUtil = true)
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
	
	public static MedicalTimeDailyPerformDto fromMedicalCareTime(MedicalCareTimeOfDaily domain) {
		return domain == null ? null : new MedicalTimeDailyPerformDto(
				domain.getDayNightAtr().value, 
				domain.getTakeOverTime().valueAsMinutes(), 
				domain.getDeductionTime().valueAsMinutes(), 
				domain.getWorkTime().valueAsMinutes());
	}
}
