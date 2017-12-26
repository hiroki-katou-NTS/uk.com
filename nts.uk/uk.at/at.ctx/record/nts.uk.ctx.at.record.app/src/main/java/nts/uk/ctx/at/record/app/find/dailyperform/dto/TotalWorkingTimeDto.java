package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.TotalWorkingTime;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の総労働時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalWorkingTimeDto {

	/** 総労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "総労働時間")
	@AttendanceItemValue(itemId = 559, type = ValueType.INTEGER)
	private Integer totalWorkingTime;

	/** 総計算時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "総計算時間")
	@AttendanceItemValue(itemId = 560, type = ValueType.INTEGER)
	private Integer totalCalcTime;

	/** 実働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "実働時間")
	@AttendanceItemValue(itemId = 579, type = ValueType.INTEGER)
	private Integer actualTime;

	/** 所定内時間: 日別実績の所定内時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "所定内時間")
	private WithinStatutoryTimeDailyPerformDto withinStatutoryTime;

	/** 所定外時間: 日別実績の所定外時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "所定外時間")
	private ExcessOfStatutoryTimeDailyPerformDto excessOfStatutoryTime;

	/** 臨時時間: 日別実績の臨時時間 */
	@AttendanceItemLayout(layout = "F", isList = true, jpPropertyName = "臨時時間")
	private List<TemporaryTimeFrameDto> temporaryTime;

	/** 遅刻時間: 日別実績の遅刻時間 */
	@AttendanceItemLayout(layout = "G", isList = true, jpPropertyName = "遅刻時間")
	private List<LateTimeDto> lateTime;

	/** 早退時間: 日別実績の早退時間 */
	@AttendanceItemLayout(layout = "H", isList = true, jpPropertyName = "早退時間")
	private List<LeaveEarlyTimeDailyPerformDto> leaveEarlyTime;

	/** 休憩時間: 日別実績の休憩時間 */
	// @AttendanceItemLayout(layout = "I")
	private BreakTimeSheetDailyPerformDto breakTimeSheet;

	/** 外出時間: 日別実績の外出時間 */
	// @AttendanceItemLayout(layout = "J", isList = true)
	private List<GoOutTimeSheetDailyPerformDto> goOutTimeSheet;

	/** 短時間勤務時間: 日別実績の短時間勤務時間 */
	// @AttendanceItemLayout(layout = "K", jpPropertyName = "短時間勤務時間")
	private ShortWorkTimeDto shortWorkTimeSheet;

	/** 加給時間: 日別実績の加給時間 */
	// @AttendanceItemLayout(layout = "L", jpPropertyName = "加給時間")
	private RaisingSalaryTimeDailyPerformDto raisingSalaryTime;

	/** 休暇時間: 日別実績の休暇 */
	// @AttendanceItemLayout(layout = "M", jpPropertyName = "休暇時間")
	private HolidayDailyPerformDto dailyOfHoliday;

	/** 勤務回数: 勤務回数 */
	// @AttendanceItemLayout(layout = "N")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer workTimes;
	

	public static TotalWorkingTimeDto fromTotalWorkingTime(TotalWorkingTime domain){
		return domain == null ? null : new TotalWorkingTimeDto(
				domain.getTotalTime().valueAsMinutes(), 
				domain.getTotalCalcTime().valueAsMinutes(), 
				domain.getActualTime().valueAsMinutes(), 
				WithinStatutoryTimeDailyPerformDto.fromWithinStatutoryTimeDailyPerform(domain.getWithinStatutoryTimeOfDaily()),
				ExcessOfStatutoryTimeDailyPerformDto.fromExcessOfStatutoryTimeDailyPerform(domain.getExcessOfStatutoryTimeOfDaily()), 
				ConvertHelper.mapTo(domain.getTemporaryTime().getTemporaryTime(), (c) -> 
					new TemporaryTimeFrameDto(c.getWorkNo().v(), c.getTemporaryLateNightTime().valueAsMinutes(), c.getTemporaryTime().valueAsMinutes())), 
				ConvertHelper.mapTo(domain.getLateTimeOfDaily(), (c) -> 
					new LateTimeDto(
						new CalcAttachTimeDto(c.getLateTime().getCalcTime().valueAsMinutes(), c.getLateTime().getTime().valueAsMinutes()), 
						new CalcAttachTimeDto(c.getLateDeductionTime().getCalcTime().valueAsMinutes(), c.getLateDeductionTime().getTime().valueAsMinutes()), 
						new HolidayUseDto(c.getTimePaidUseTime().getTimeAnnualLeaveUseTime().valueAsMinutes(),
								c.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().valueAsMinutes(),
								c.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().valueAsMinutes(),
								c.getTimePaidUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes()),
						c.getExemptionTime().getExemptionTime().valueAsMinutes(), 
						c.getWorkNo().v())), 
				ConvertHelper.mapTo(domain.getLeaveEarlyTimeOfDaily(), (c) -> 
					new LeaveEarlyTimeDailyPerformDto(
						new CalcAttachTimeDto(c.getLeaveEarlyTime().getCalcTime().valueAsMinutes(), c.getLeaveEarlyTime().getTime().valueAsMinutes()), 
						new CalcAttachTimeDto(c.getLeaveEarlyDeductionTime().getCalcTime().valueAsMinutes(), c.getLeaveEarlyDeductionTime().getTime().valueAsMinutes()), 
						new ValicationUseDto(
								c.getTimePaidUseTime().getTimeAnnualLeaveUseTime().valueAsMinutes(),
								c.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().valueAsMinutes(),
								c.getTimePaidUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes(),
								c.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().valueAsMinutes()),
						c.getIntervalTime().getExemptionTime().valueAsMinutes(), 
						c.getWorkNo().v())), 
				BreakTimeSheetDailyPerformDto.fromBreakTimeOfDaily(domain.getBreakTimeOfDaily()), 
				//TODO: get domain 今回対象外
				null, 
				null, 
				null, 
				null, 
				domain.getWorkTimes().v());
	}
}
