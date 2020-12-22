package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.medicaltime.MedicalTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の医療時間 */
public class MedicalTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

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

	@Override
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

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case WORKING_TIME:
			return Optional.of(ItemValue.builder().value(workTime).valueType(ValueType.TIME));
		case DEDUCTION:
			return Optional.of(ItemValue.builder().value(deducationTime).valueType(ValueType.TIME));
		case TAKE_OVER:
			return Optional.of(ItemValue.builder().value(takeOverTime).valueType(ValueType.TIME));
		case ATTRIBUTE:
			return Optional.of(ItemValue.builder().value(attr).valueType(ValueType.ATTR));
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case WORKING_TIME:
		case DEDUCTION:
		case TAKE_OVER:
		case ATTRIBUTE:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case WORKING_TIME:
			workTime = value.valueOrDefault(0); break;
		case DEDUCTION:
			deducationTime = value.valueOrDefault(0); break;
		case TAKE_OVER:
			takeOverTime = value.valueOrDefault(0); break;
		case ATTRIBUTE:
			attr = value.valueOrDefault(0); break;
		default:
		}
	}

	@Override
	public void setEnum(String enumText) {
		switch (enumText) {
		case E_DAY_WORK:
			this.attr = 0; break;
		case E_NIGHT_WORK:
			this.attr = 1; break;
		default:
		}
	}

//	@Override
//	public boolean enumNeedSet() {
//		return true;
//	}
	
	
}
