package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.record.dom.stamp.GoOutReason;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 日別実績の外出時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoOutTimeSheetDailyPerformDto implements ItemConst {

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
								reason(), 
								valicationUseTime == null ? ValicationUseDto.createEmpty() : valicationUseTime.toDomain(), 
								totalTimeForCalc == null ? OutingTotalTimeDto.createEmpty() : totalTimeForCalc.createDeductionTime(),
								totalTimeForDeduction == null ? OutingTotalTimeDto.createEmpty() : totalTimeForDeduction.createDeductionTime(), 
								ConvertHelper.mapTo(goOutTime, c -> c.toDomain()));
	}
	
	public GoOutReason reason() {
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
