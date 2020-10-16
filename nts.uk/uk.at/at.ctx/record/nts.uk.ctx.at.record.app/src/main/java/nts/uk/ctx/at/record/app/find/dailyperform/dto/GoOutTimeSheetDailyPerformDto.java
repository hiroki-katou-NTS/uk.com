package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;
//import java.util.stream.Collectors;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.enums.GoOutReason;

/** 日別実績の外出時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoOutTimeSheetDailyPerformDto implements ItemConst, AttendanceItemDataGate {

	/** 時間休暇使用時間: 日別実績の時間休暇使用時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = HOLIDAY + USAGE, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private ValicationUseDto valicationUseTime;

	/** 控除用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = DEDUCTION, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private OutingTotalTimeDto totalTimeForDeduction;

	/** 計上用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = CALC, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private OutingTotalTimeDto totalTimeForCalc;

	/** 控除用コア外合計時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = DEDUCTION + OUT_CORE, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private CalcAttachTimeDto coreTotalTimeForDeduction;

	/** 計上用コア外合計時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = CALC + OUT_CORE, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private CalcAttachTimeDto coreTotalTimeForCalc;

	/** 回数: 休憩外出回数 */
	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = COUNT, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	@AttendanceItemValue(type = ValueType.COUNT)
	private Integer times;

	/** 外出理由: 外出理由 */
	@AttendanceItemLayout(layout = LAYOUT_G, jpPropertyName = REASON)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int attr;

	/** 補正後時間帯: 外出時間帯 */
	@AttendanceItemLayout(layout = LAYOUT_H, jpPropertyName = AFTER_CORRECTED, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<GoOutTimeDto> goOutTime;

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case REASON:
			return Optional.of(ItemValue.builder().value(attr).valueType(ValueType.ATTR));
		case COUNT:
			return Optional.of(ItemValue.builder().value(times).valueType(ValueType.COUNT));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case (HOLIDAY + USAGE):
			return new ValicationUseDto();
		case DEDUCTION:
		case CALC:
			return new OutingTotalTimeDto();
		case (DEDUCTION + OUT_CORE):
		case (CALC + OUT_CORE):
			return new CalcAttachTimeDto();
		case AFTER_CORRECTED:
			return new GoOutTimeDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case (HOLIDAY + USAGE):
			return Optional.ofNullable(valicationUseTime);
		case DEDUCTION:
			return Optional.ofNullable(totalTimeForDeduction);
		case CALC:
			return Optional.ofNullable(totalTimeForCalc);
		case (DEDUCTION + OUT_CORE):
			return Optional.ofNullable(coreTotalTimeForDeduction);
		case (CALC + OUT_CORE):
			return Optional.ofNullable(coreTotalTimeForCalc);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public int size(String path) {
		if (path.equals(AFTER_CORRECTED)) {
			return 10;
		}
		return AttendanceItemDataGate.super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case REASON:
		case COUNT:
			return PropType.VALUE;
		case AFTER_CORRECTED:
			return PropType.OBJECT;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (path.equals(AFTER_CORRECTED)) {
			return (List<T>) goOutTime;
		}
		return AttendanceItemDataGate.super.gets(path);
	}	

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case REASON:
			attr = value.valueOrDefault(0);
			break;
		case COUNT:
			times = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case (HOLIDAY + USAGE):
			valicationUseTime = (ValicationUseDto) value;
			break;
		case DEDUCTION:
			totalTimeForDeduction = (OutingTotalTimeDto) value;
			break;
		case CALC:
			totalTimeForCalc = (OutingTotalTimeDto) value;
			break;
		case (DEDUCTION + OUT_CORE):
			coreTotalTimeForDeduction = (CalcAttachTimeDto) value;
			break;
		case (CALC + OUT_CORE):
			coreTotalTimeForCalc = (CalcAttachTimeDto) value;
			break;
		default:
			break;
		}
	}
	
//	@Override
//	public boolean enumNeedSet(){
//		return true;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (path.equals(AFTER_CORRECTED)) {
			goOutTime = (List<GoOutTimeDto>) value;
		}
	}

	@Override
	public void setEnum(String enumText) {
		switch (enumText) {
		case E_SUPPORT:
			this.attr = 0;
			break;
		case E_UNION:
			this.attr = 1;
			break;
		case E_CHARGE:
			this.attr = 2;
			break;
		case E_OFFICAL:
			this.attr = 3;
			break;
		default:
		}
	}
	
	@Override
	public String enumText() {
		switch (this.attr) {
		case 0:
			return E_SUPPORT;
		case 1:
			return E_UNION;
		case 2:
			return E_CHARGE;
		case 3:
			return E_OFFICAL;
		default:
			return EMPTY_STRING;
		}
	}
	
	@Override
	public GoOutTimeSheetDailyPerformDto clone(){
		return new GoOutTimeSheetDailyPerformDto(
						valicationUseTime == null ? null : valicationUseTime.clone(), 
						totalTimeForDeduction == null ? null : totalTimeForDeduction.clone(), 
						totalTimeForCalc == null ? null : totalTimeForCalc.clone(), 
						coreTotalTimeForDeduction == null ? null : coreTotalTimeForDeduction.clone(),  
						coreTotalTimeForCalc == null ? null : coreTotalTimeForCalc.clone(), 
						times, 
						attr, 
						ConvertHelper.mapTo(goOutTime, c -> c.clone()));
	}
	
	public static GoOutTimeSheetDailyPerformDto toDto(OutingTimeOfDaily domain){
		return domain == null ? null : new GoOutTimeSheetDailyPerformDto(
				toValicationUse(domain.getTimeVacationUseOfDaily()), 
				OutingTotalTimeDto.from(domain.getDeductionTotalTime()), 
				OutingTotalTimeDto.from(domain.getRecordTotalTime()), 
				null,  null, 
				domain.getWorkTime() == null ? null : domain.getWorkTime().v(), 
				domain.getReason().value, 
				ConvertHelper.mapTo(domain.getOutingTimeSheets(), c -> GoOutTimeDto.toDto(c)));
	}
	
	private static ValicationUseDto toValicationUse(TimevacationUseTimeOfDaily valication){
		return valication == null ? null : new ValicationUseDto(
				valication.getTimeAnnualLeaveUseTime() == null ? null : valication.getTimeAnnualLeaveUseTime().valueAsMinutes(), 
				valication.getSixtyHourExcessHolidayUseTime() == null ? null : valication.getSixtyHourExcessHolidayUseTime().valueAsMinutes(), 
				valication.getTimeSpecialHolidayUseTime() == null ? null : valication.getTimeSpecialHolidayUseTime().valueAsMinutes(),
				valication.getTimeCompensatoryLeaveUseTime() == null ? null : valication.getTimeCompensatoryLeaveUseTime().valueAsMinutes());
	}
	
	public OutingTimeOfDaily toDomain(){
		return new OutingTimeOfDaily(times == null ? new BreakTimeGoOutTimes(0) : new BreakTimeGoOutTimes(times), 
								toEnum(), 
								valicationUseTime == null ? ValicationUseDto.createEmpty() : valicationUseTime.toDomain(), 
								totalTimeForCalc == null ? OutingTotalTimeDto.createEmpty() : totalTimeForCalc.createDeductionTime(),
								totalTimeForDeduction == null ? OutingTotalTimeDto.createEmpty() : totalTimeForDeduction.createDeductionTime(), 
								ConvertHelper.mapTo(goOutTime, c -> c.toDomain()));
	}
	
	public GoOutReason toEnum() {
		switch (attr) {
		case 0:
			return GoOutReason.SUPPORT;
		case 1:
			return GoOutReason.UNION;
		case 2:
			return GoOutReason.CHARGE;
		case 3:
		default:
			return GoOutReason.OFFICAL;
		}
	}
}
