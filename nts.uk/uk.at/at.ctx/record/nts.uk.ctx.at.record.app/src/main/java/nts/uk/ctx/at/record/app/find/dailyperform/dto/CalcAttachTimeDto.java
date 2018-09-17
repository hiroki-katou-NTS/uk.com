package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

/** 計算付き時間 and 計算付き時間(マイナス有り) */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcAttachTimeDto implements ItemConst {

	/** 計算時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = CALC)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer calcTime;

	/** 時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer time;
	
	/** 乖離時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = DIVERGENCE)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer divergenceTime;

	public CalcAttachTimeDto(Integer calcTime, Integer time) {
		super();
		this.calcTime = calcTime;
		this.time = time;
	}
	
	@Override
	public CalcAttachTimeDto clone() {
		return new CalcAttachTimeDto(calcTime, time, divergenceTime);
	}
	
	public static CalcAttachTimeDto toTimeWithCal(TimeDivergenceWithCalculationMinusExist time){
		return time == null ? null : new CalcAttachTimeDto(
											time.getCalcTime() == null ? 0 : time.getCalcTime().valueAsMinutes(), 
											time.getTime() == null ? 0 : time.getTime().valueAsMinutes(), 
											time.getDivergenceTime() == null ? 0 : time.getDivergenceTime().valueAsMinutes());
	}
	
	public static CalcAttachTimeDto toTimeWithCal(TimeDivergenceWithCalculation time){
		return time == null ? null : new CalcAttachTimeDto(
											time.getCalcTime() == null ? 0 : time.getCalcTime().valueAsMinutes(), 
											time.getTime() == null ? 0 : time.getTime().valueAsMinutes(), 
											time.getDivergenceTime() == null ? 0 : time.getDivergenceTime().valueAsMinutes());
	}
	
	public static CalcAttachTimeDto toTimeWithCal(TimeWithCalculation time){
		return time == null ? null : new CalcAttachTimeDto(
											time.getCalcTime() == null ? 0 : time.getCalcTime().valueAsMinutes(), 
											time.getTime() == null ? 0 : time.getTime().valueAsMinutes());
	}
	
	public static CalcAttachTimeDto toTimeWithCal(TimeWithCalculationMinusExist time) {
		return time == null ? null : new CalcAttachTimeDto(
											time.getCalcTime() == null ? 0 : time.getCalcTime().valueAsMinutes(), 
											time.getTime() == null ? 0 : time.getTime().valueAsMinutes());
	}
	
	public TimeWithCalculation createTimeWithCalc() {
		return TimeWithCalculation.createTimeWithCalculation(
										time == null ? AttendanceTime.ZERO : new AttendanceTime(time), 
										calcTime == null ? AttendanceTime.ZERO : new AttendanceTime(calcTime));
	}
	
	public TimeDivergenceWithCalculation createTimeDivWithCalc() {
		return TimeDivergenceWithCalculation.createTimeWithCalculation(
										time == null ? AttendanceTime.ZERO : new AttendanceTime(time), 
										calcTime == null ? AttendanceTime.ZERO : new AttendanceTime(calcTime));
	}
	
	public TimeDivergenceWithCalculationMinusExist createTimeDivWithMinus(){
		return TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(
				time == null ? AttendanceTimeOfExistMinus.ZERO : new AttendanceTimeOfExistMinus(time), 
				calcTime == null ? AttendanceTimeOfExistMinus.ZERO : new AttendanceTimeOfExistMinus(calcTime));
	}
}
