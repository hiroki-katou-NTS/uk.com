package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.secondorder.medical.MedicalCareTimeOfDaily;

/** 日別実績の医療時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalTimeDailyPerformDto implements ItemConst, AttendanceItemDataGate {

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
	@Override
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
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case TAKE_OVER:
			return Optional.of(ItemValue.builder().value(takeOverTime).valueType(ValueType.TIME));
		case DEDUCTION:
			return Optional.of(ItemValue.builder().value(deductionTime).valueType(ValueType.TIME));
		case WORKING_TIME:
			return Optional.of(ItemValue.builder().value(workTime).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case TAKE_OVER:
		case DEDUCTION:
		case WORKING_TIME:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case TAKE_OVER:
			takeOverTime = value.valueOrDefault(null);
			break;
		case DEDUCTION:
			deductionTime = value.valueOrDefault(null);
			break;
		case WORKING_TIME:
			workTime = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}

	@Override
	public void setEnum(String enumText) {
		switch (enumText) {
		case E_DAY_WORK:
			this.attr = 0;
			break;
		case E_NIGHT_WORK:
			this.attr = 1;
			break;
		default:
		}
	}

//	@Override
//	public boolean enumNeedSet() {
//		return true;
//	}
	
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
