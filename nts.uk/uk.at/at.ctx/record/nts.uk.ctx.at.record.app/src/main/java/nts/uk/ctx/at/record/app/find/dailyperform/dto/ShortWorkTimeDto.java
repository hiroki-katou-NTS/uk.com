package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimeOfDaily;

/** 日別実績の短時間勤務時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortWorkTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 合計控除時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = DEDUCTION, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private TotalDeductionTimeDto totalDeductionTime;

	/** 合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = TOTAL, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private TotalDeductionTimeDto totalTime;

	/** 勤務回数 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = COUNT, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	@AttendanceItemValue(type = ValueType.COUNT)
	private Integer times;

	/** 育児介護区分 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = CHILD_CARE_ATTR)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int attr;
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case COUNT:
			return Optional.of(ItemValue.builder().value(times).valueType(ValueType.COUNT));
		case CHILD_CARE_ATTR:
			return Optional.of(ItemValue.builder().value(attr).valueType(ValueType.ATTR));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case COUNT:
		case CHILD_CARE_ATTR:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case DEDUCTION:
		case TOTAL:
			return new TotalDeductionTimeDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case DEDUCTION:
			return Optional.ofNullable(totalDeductionTime);
		case TOTAL:
			return Optional.ofNullable(totalTime);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case COUNT:
			this.times = value.valueOrDefault(null);
			break;
		case CHILD_CARE_ATTR:
			this.attr = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case DEDUCTION:
			this.totalDeductionTime = (TotalDeductionTimeDto) value;
			break;
		case TOTAL:
			this.totalTime = (TotalDeductionTimeDto) value;
			break;
		default:
			break;
		}
	}

	@Override
	public void setEnum(String enumText) {
		switch (enumText) {
		case E_CHILD_CARE:
			this.attr = 0;
			break;
		case E_CARE:
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
	public String enumText(){
		switch (this.attr) {
		case 0:
			return E_CHILD_CARE;

		case 1:
			return E_CARE;
		default:
			return EMPTY_STRING;
		}
	}
	
	@Override
	public ShortWorkTimeDto clone(){
		return new ShortWorkTimeDto(totalDeductionTime == null ? null : totalDeductionTime.clone(), 
				totalTime == null ? null : totalTime.clone(), times,  attr);
	}
	
	public static ShortWorkTimeDto toDto(ShortWorkTimeOfDaily domain){
		return domain == null ? null : new ShortWorkTimeDto(
											TotalDeductionTimeDto.getDeductionTime(domain.getTotalDeductionTime()), 
											TotalDeductionTimeDto.getDeductionTime(domain.getTotalTime()), 
											domain.getWorkTimes() == null ? null : domain.getWorkTimes().v(), 
											domain.getChildCareAttribute().value);
	}
	
	public ShortWorkTimeOfDaily toDomain(){
		return new ShortWorkTimeOfDaily(
				new WorkTimes(times == null ? 0 : times),
				createDeductionTime(totalTime),
				createDeductionTime(totalDeductionTime),
				attr == ChildCareAttribute.CARE.value 
						? ChildCareAttribute.CARE : ChildCareAttribute.CHILD_CARE);
	}
	
	public static ShortWorkTimeOfDaily defaultDomain(){
		return new ShortWorkTimeOfDaily(
				new WorkTimes(0),
				DeductionTotalTime.defaultValue(),
				DeductionTotalTime.defaultValue(),
				ChildCareAttribute.CHILD_CARE);
	}

	private DeductionTotalTime createDeductionTime(TotalDeductionTimeDto dto) {
		return dto == null ? DeductionTotalTime.defaultValue() : dto.createDeductionTime();
	}
}
