package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.medicaltime.MedicalTimeOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の医療時間 */
public class MedicalTimeOfMonthlyDto {

	/** 勤務時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "勤務時間", layout = "A", needCheckIDWithMethod = "dayNightAtr")
	private Integer workTime;

	/** 控除時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "控除時間", layout = "B", needCheckIDWithMethod = "dayNightAtr")
	private Integer deducationTime;

	/** 申送時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "申送時間", layout = "C", needCheckIDWithMethod = "dayNightAtr")
	private Integer takeOverTime;

	/** 日勤夜勤区分: 日勤夜勤区分 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "日勤夜勤区分", layout = "D", needCheckIDWithMethod = "dayNightAtr")
	private int dayNightAtr;

	public String dayNightAtr() {
		switch (this.dayNightAtr) {
		case 0:
			return "日勤";
		case 1:
		default:
			return "夜勤";
		}
	}
	
	public static MedicalTimeOfMonthlyDto from(MedicalTimeOfMonthly domain) {
		MedicalTimeOfMonthlyDto dto = new MedicalTimeOfMonthlyDto();
		if(domain != null) {
			dto.setDayNightAtr(domain.getDayNightAtr() == null ? 0 : domain.getDayNightAtr().value);
			dto.setDeducationTime(domain.getDeducationTime() == null ? null : domain.getDeducationTime().valueAsMinutes());
			dto.setTakeOverTime(domain.getTakeOverTime() == null ? null : domain.getTakeOverTime().valueAsMinutes());
			dto.setWorkTime(domain.getWorkTime() == null ? null : domain.getWorkTime().valueAsMinutes());
		}
		return dto;
	}

	public MedicalTimeOfMonthly toDomain() {
		return MedicalTimeOfMonthly.of(ConvertHelper.getEnum(dayNightAtr, WorkTimeNightShift.class),
				toAttendanceTimeMonth(workTime), toAttendanceTimeMonth(deducationTime),
				toAttendanceTimeMonth(takeOverTime));
	}

	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time) {
		return time == null ? null : new AttendanceTimeMonth(time);
	}
}
