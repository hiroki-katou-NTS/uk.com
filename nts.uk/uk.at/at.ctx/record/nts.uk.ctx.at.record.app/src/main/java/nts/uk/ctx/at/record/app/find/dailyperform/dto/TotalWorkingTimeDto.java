package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.TotalWorkingTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.temporarytime.TemporaryFrameTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.latetime.IntervalExemptionTime;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;

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
	@AttendanceItemLayout(layout = "F", isList = true, jpPropertyName = "臨時時間", listMaxLength = 3, setFieldWithIndex = "workNo")
	private List<TemporaryTimeFrameDto> temporaryTime;

	/** 遅刻時間: 日別実績の遅刻時間 */
	@AttendanceItemLayout(layout = "G", isList = true, jpPropertyName = "遅刻時間", listMaxLength = 2, setFieldWithIndex = "workNo")
	private List<LateTimeDto> lateTime;

	/** 早退時間: 日別実績の早退時間 */
	@AttendanceItemLayout(layout = "H", isList = true, jpPropertyName = "早退時間", listMaxLength = 2, setFieldWithIndex = "workNo")
	private List<LeaveEarlyTimeDailyPerformDto> leaveEarlyTime;

	/** 休憩時間: 日別実績の休憩時間 */
	// @AttendanceItemLayout(layout = "I")
	private BreakTimeSheetDailyPerformDto breakTimeSheet;

	/** 外出時間: 日別実績の外出時間 */
	//TODO:
	// @AttendanceItemLayout(layout = "J", isList = true, listMaxLength = ?)
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
				getAttendanceTime(domain.getTotalTime()), 
				getAttendanceTime(domain.getTotalCalcTime()), 
				getAttendanceTime(domain.getActualTime()), 
				WithinStatutoryTimeDailyPerformDto.fromWithinStatutoryTimeDailyPerform(domain.getWithinStatutoryTimeOfDaily()),
				ExcessOfStatutoryTimeDailyPerformDto.fromExcessOfStatutoryTimeDailyPerform(domain.getExcessOfStatutoryTimeOfDaily()), 
				domain.getTemporaryTime().getTemporaryTime() == null ? new ArrayList<>() :
					ConvertHelper.mapTo(domain.getTemporaryTime().getTemporaryTime(), (c) -> 
						new TemporaryTimeFrameDto(c.getWorkNo().v(), 
							getAttendanceTime(c.getTemporaryLateNightTime()), 
							getAttendanceTime(c.getTemporaryTime()))), 
				ConvertHelper.mapTo(domain.getLateTimeOfDaily(), (c) -> 
					new LateTimeDto(
						getCalcTime(c.getLateTime()), 
						getCalcTime(c.getLateDeductionTime()), 
						new HolidayUseDto(
								getAttendanceTime(c.getTimePaidUseTime().getTimeAnnualLeaveUseTime()),
								getAttendanceTime(c.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime()),
								getAttendanceTime(c.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime()),
								getAttendanceTime(c.getTimePaidUseTime().getTimeSpecialHolidayUseTime())),
						getAttendanceTime(c.getExemptionTime().getExemptionTime()), 
						c.getWorkNo().v())), 
				ConvertHelper.mapTo(domain.getLeaveEarlyTimeOfDaily(), (c) -> 
					new LeaveEarlyTimeDailyPerformDto(
						getCalcTime(c.getLeaveEarlyTime()), 
						getCalcTime(c.getLeaveEarlyDeductionTime()), 
						new ValicationUseDto(
								getAttendanceTime(c.getTimePaidUseTime().getTimeAnnualLeaveUseTime()),
								getAttendanceTime(c.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime()),
								getAttendanceTime(c.getTimePaidUseTime().getTimeSpecialHolidayUseTime()),
								getAttendanceTime(c.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime())),
						getAttendanceTime(c.getIntervalTime().getExemptionTime()), 
						c.getWorkNo().v())), 
				BreakTimeSheetDailyPerformDto.fromBreakTimeOfDaily(domain.getBreakTimeOfDaily()), 
				//TODO: get domain 今回対象外
				null, 
				null, 
				null, 
				null, 
				domain.getWorkTimes().v());
	}
	
	private static Integer getAttendanceTime(AttendanceTime domain) {
		return domain == null ? null : domain.valueAsMinutes();
	}

	private static CalcAttachTimeDto getCalcTime(TimeWithCalculation c) {
		return c == null ? null : new CalcAttachTimeDto(
				c.getCalcTime() == null ? null : c.getCalcTime().valueAsMinutes(),
				c.getTime() == null ? null : c.getTime().valueAsMinutes());
	}
	
	public TotalWorkingTime toDomain() {
		return new TotalWorkingTime(toAttendanceTime(totalWorkingTime), toAttendanceTime(totalCalcTime),
				toAttendanceTime(actualTime), withinStatutoryTime.toDomain(), excessOfStatutoryTime.toDomain(),
				ConvertHelper.mapTo(lateTime, (c) -> new LateTimeOfDaily(
						createTimeWithCalc(c.getLateTime()),
						createTimeWithCalc(c.getLateDeductionTime()),
						new WorkNo(c.getWorkNo()),
						createTimeValication(c.getBreakUse()),
						new IntervalExemptionTime(null, null, toAttendanceTime(c.getIntervalExemptionTime())))),
				ConvertHelper.mapTo(leaveEarlyTime,
						(c) -> new LeaveEarlyTimeOfDaily(
								createTimeWithCalc(c.getLeaveEarlyTime()),
								createTimeWithCalc(c.getLeaveEarlyDeductionTime()),
								new WorkNo(c.getWorkNo()),
								createTimeValication(c.getValicationUseTime()),
								new IntervalExemptionTime(null, null, toAttendanceTime(c.getIntervalExemptionTime()))
								)),
				null, null, null, new WorkTimes(workTimes),
				new TemporaryTimeOfDaily(ConvertHelper.mapTo(temporaryTime,
						(c) -> new TemporaryFrameTimeOfDaily(new WorkNo(c.getWorkNo()),
								toAttendanceTime(c.getTemporaryTime()),
								toAttendanceTime(c.getTemporaryNightTime())))));
	}

	private AttendanceTime toAttendanceTime(Integer time) {
		return time == null ? null : new AttendanceTime(time);
	}

	private TimevacationUseTimeOfDaily createTimeValication(HolidayUseDto c) {
		return c == null ? null : new TimevacationUseTimeOfDaily(
					toAttendanceTime(c.getTimeAnnualLeaveUseTime()),
					toAttendanceTime(c.getTimeCompensatoryLeaveUseTime()),
					toAttendanceTime(c.getExcessHolidayUseTime()),
					toAttendanceTime(c.getTimeSpecialHolidayUseTime()));
	}
	
	private TimevacationUseTimeOfDaily createTimeValication(ValicationUseDto c) {
		return c == null ? null : new TimevacationUseTimeOfDaily(
					toAttendanceTime(c.getTimeAnnualLeaveUseTime()),
					toAttendanceTime(c.getTimeCompensatoryLeaveUseTime()),
					toAttendanceTime(c.getExcessHolidayUseTime()),
					toAttendanceTime(c.getTimeSpecialHolidayUseTime()));
	}

	private TimeWithCalculation createTimeWithCalc(CalcAttachTimeDto c) {
		return c == null ? null : TimeWithCalculation.createTimeWithCalculation(
					toAttendanceTime(c.getTime()),
					toAttendanceTime(c.getCalcTime()));
	}
}
