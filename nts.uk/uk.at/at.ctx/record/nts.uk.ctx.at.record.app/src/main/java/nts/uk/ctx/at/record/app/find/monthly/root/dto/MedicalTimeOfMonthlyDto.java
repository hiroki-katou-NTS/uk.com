package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.medicaltime.MedicalTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の医療時間 */
public class MedicalTimeOfMonthlyDto implements ItemConst{

	/** 勤務時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = WORKING_TIME, layout = LAYOUT_A, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD )
	private int workTime;

	/** 控除時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = DEDUCTION, layout = LAYOUT_B, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private int deducationTime;

	/** 申送時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TAKE_OVER, layout = LAYOUT_C, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private int takeOverTime;

	/** 日勤夜勤区分: 日勤夜勤区分 */
	@AttendanceItemValue(type = ValueType.ATTR)
	@AttendanceItemLayout(jpPropertyName = ATTRIBUTE, layout = LAYOUT_D, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private int attr;

	public String enumText() {
		switch (this.attr) {
		case 0:
			return E_DAY_WORK;
		case 1:
		default:
			return E_NIGHT_WORK;
		}
	}
	
	public static MedicalTimeOfMonthlyDto from(MedicalTimeOfMonthly domain) {
		MedicalTimeOfMonthlyDto dto = new MedicalTimeOfMonthlyDto();
		if(domain != null) {
			dto.setAttr(domain.getDayNightAtr() == null ? 0 : domain.getDayNightAtr().value);
			dto.setDeducationTime(domain.getDeducationTime() == null ? 0 : domain.getDeducationTime().valueAsMinutes());
			dto.setTakeOverTime(domain.getTakeOverTime() == null ? 0 : domain.getTakeOverTime().valueAsMinutes());
			dto.setWorkTime(domain.getWorkTime() == null ? 0 : domain.getWorkTime().valueAsMinutes());
		}
		return dto;
	}

	public MedicalTimeOfMonthly toDomain() {
		return MedicalTimeOfMonthly.of(attr  == WorkTimeNightShift.DAY_SHIFT.value 
										? WorkTimeNightShift.DAY_SHIFT : WorkTimeNightShift.NIGHT_SHIFT,
				toAttendanceTimeMonth(workTime), toAttendanceTimeMonth(deducationTime),
				toAttendanceTimeMonth(takeOverTime));
	}

	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time) {
		return new AttendanceTimeMonth(time);
	}
}
