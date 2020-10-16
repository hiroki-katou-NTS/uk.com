package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.interval.IntervalTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryFrameTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.WithinStatutoryTimeOfDaily;
import nts.uk.shr.com.time.AttendanceClock;


/** 日別実績の総労働時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalWorkingTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 総労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TOTAL_LABOR)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer totalWorkingTime;

	/** 総計算時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = TOTAL_CALC)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer totalCalcTime;

	/** 実働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = ACTUAL)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer actualTime;

	/** 所定内時間: 日別実績の所定内時間 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = WITHIN_STATUTORY)
	private WithinStatutoryTimeDailyPerformDto withinStatutoryTime;

	/** 所定外時間: 日別実績の所定外時間 */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = EXCESS_STATUTORY)
	private ExcessOfStatutoryTimeDailyPerformDto excessOfStatutoryTime;

	/** 臨時時間: 日別実績の臨時時間 */
	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = TEMPORARY, listMaxLength = 3, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<TemporaryTimeFrameDto> temporaryTime;

	/** 遅刻時間: 日別実績の遅刻時間 */
	@AttendanceItemLayout(layout = LAYOUT_G, jpPropertyName = LATE, listMaxLength = 2, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<LateEarlyTimeDailyPerformDto> lateTime;

	/** 早退時間: 日別実績の早退時間 */
	@AttendanceItemLayout(layout = LAYOUT_H, jpPropertyName = LEAVE_EARLY, listMaxLength = 2, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<LateEarlyTimeDailyPerformDto> leaveEarlyTime;

	/** 休憩時間: 日別実績の休憩時間 */
	@AttendanceItemLayout(layout = LAYOUT_I, jpPropertyName = BREAK)
	private BreakTimeSheetDailyPerformDto breakTimeSheet;

	/** 外出時間: 日別実績の外出時間 */
	@AttendanceItemLayout(layout = LAYOUT_J, listMaxLength = 4, jpPropertyName = GO_OUT, 
			enumField = DEFAULT_ENUM_FIELD_NAME, listNoIndex = true)
	private List<GoOutTimeSheetDailyPerformDto> goOutTimeSheet;

	/** 短時間勤務時間: 日別実績の短時間勤務時間 */
	@AttendanceItemLayout(layout = LAYOUT_K, jpPropertyName = SHORT_WORK, enumField = DEFAULT_ENUM_FIELD_NAME)
	private ShortWorkTimeDto shortWorkTime;

	/** 加給時間: 日別実績の加給時間 */
	@AttendanceItemLayout(layout = LAYOUT_L, jpPropertyName = RAISING_SALARY)
	private RaisingSalaryTimeDailyPerformDto raisingSalaryTime;

	/** 休暇時間: 日別実績の休暇 */
	@AttendanceItemLayout(layout = LAYOUT_M, jpPropertyName = HOLIDAY)
	private HolidayDailyPerformDto dailyOfHoliday;

	/** 勤務回数: 勤務回数 */
	@AttendanceItemLayout(layout = LAYOUT_N, jpPropertyName = COUNT)
	@AttendanceItemValue(type = ValueType.COUNT)
	private Integer workTimes;

	/** /*休暇加算時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_O, jpPropertyName = HOLIDAY + ADD)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer vacationAddTime;

	/** インターバル時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_P, jpPropertyName = INTERVAL + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private int intervalTime;

	/** インターバル出勤時刻: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_Q, jpPropertyName = INTERVAL + ATTENDANCE)
	@AttendanceItemValue(type = ValueType.TIME)
	private int intervalAttendanceClock;

	/** 計算差異時間 */
	@AttendanceItemLayout(layout = LAYOUT_R, jpPropertyName = CALC + DIFF)
	@AttendanceItemValue(type = ValueType.TIME)
	private int calcDiffTime;
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case TOTAL_LABOR:
			return Optional.of(ItemValue.builder().value(totalWorkingTime).valueType(ValueType.TIME));
		case TOTAL_CALC:
			return Optional.of(ItemValue.builder().value(totalCalcTime).valueType(ValueType.TIME));
		case ACTUAL:
			return Optional.of(ItemValue.builder().value(actualTime).valueType(ValueType.TIME));
		case COUNT:
			return Optional.of(ItemValue.builder().value(workTimes).valueType(ValueType.COUNT));
		case (HOLIDAY + ADD):
			return Optional.of(ItemValue.builder().value(vacationAddTime).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case WITHIN_STATUTORY:
			return new WithinStatutoryTimeDailyPerformDto();
		case EXCESS_STATUTORY:
			return new ExcessOfStatutoryTimeDailyPerformDto();
		case TEMPORARY:
			return new TemporaryTimeFrameDto();
		case LATE:
		case LEAVE_EARLY:
			return new LateEarlyTimeDailyPerformDto();
		case BREAK:
			return new BreakTimeSheetDailyPerformDto();
		case GO_OUT:
			return new GoOutTimeSheetDailyPerformDto();
		case SHORT_WORK:
			return new ShortWorkTimeDto();
		case RAISING_SALARY:
			return new RaisingSalaryTimeDailyPerformDto();
		case HOLIDAY:
			return new HolidayDailyPerformDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case WITHIN_STATUTORY:
			return Optional.ofNullable(withinStatutoryTime);
		case EXCESS_STATUTORY:
			return Optional.ofNullable(excessOfStatutoryTime);
		case BREAK:
			return Optional.ofNullable(breakTimeSheet);
		case SHORT_WORK:
			return Optional.ofNullable(shortWorkTime);
		case RAISING_SALARY:
			return Optional.ofNullable(raisingSalaryTime);
		case HOLIDAY:
			return Optional.ofNullable(dailyOfHoliday);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public int size(String path) {
		switch (path) {
		case TEMPORARY:
			return 3;
		case LATE:
		case LEAVE_EARLY:
			return 2;
		case GO_OUT:
			return 4;
		default:
			break;
		}
		return AttendanceItemDataGate.super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case TEMPORARY:
		case LATE:
		case LEAVE_EARLY:
			return PropType.IDX_LIST;
		case GO_OUT:
			return PropType.ENUM_LIST;
		case TOTAL_LABOR:
		case TOTAL_CALC:
		case ACTUAL:
		case COUNT:
		case (HOLIDAY + ADD):
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		switch (path) {
		case TEMPORARY:
			return (List<T>) temporaryTime;
		case LATE:
			return (List<T>) lateTime;
		case LEAVE_EARLY:
			return (List<T>) leaveEarlyTime;
		case GO_OUT:
			return (List<T>) goOutTimeSheet;
		default:
			break;
		}
		return AttendanceItemDataGate.super.gets(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case TOTAL_LABOR:
			totalWorkingTime = value.valueOrDefault(null);
			break;
		case TOTAL_CALC:
			totalCalcTime = value.valueOrDefault(null);
			break;
		case ACTUAL:
			actualTime = value.valueOrDefault(null);
			break;
		case COUNT:
			workTimes = value.valueOrDefault(null);
			break;
		case (HOLIDAY + ADD):
		vacationAddTime = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case WITHIN_STATUTORY:
			this.withinStatutoryTime = (WithinStatutoryTimeDailyPerformDto) value;
			break;
		case EXCESS_STATUTORY:
			this.excessOfStatutoryTime = (ExcessOfStatutoryTimeDailyPerformDto) value;
			break;
		case BREAK:
			this.breakTimeSheet = (BreakTimeSheetDailyPerformDto) value;
			break;
		case SHORT_WORK:
			this.shortWorkTime = (ShortWorkTimeDto) value;
			break;
		case RAISING_SALARY:
			this.raisingSalaryTime = (RaisingSalaryTimeDailyPerformDto) value;
			break;
		case HOLIDAY:
			this.dailyOfHoliday = (HolidayDailyPerformDto) value;
			break;
		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		switch (path) {
		case TEMPORARY:
			temporaryTime = (List<TemporaryTimeFrameDto>) value;
			break;
		case LATE:
			lateTime = (List<LateEarlyTimeDailyPerformDto>) value;
			break;
		case LEAVE_EARLY:
			leaveEarlyTime = (List<LateEarlyTimeDailyPerformDto>) value;
			break;
		case GO_OUT:
			goOutTimeSheet = (List<GoOutTimeSheetDailyPerformDto>) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public TotalWorkingTimeDto clone() {
		return new TotalWorkingTimeDto(totalWorkingTime, totalCalcTime, actualTime,
										withinStatutoryTime == null ? null : withinStatutoryTime.clone(),
										excessOfStatutoryTime == null ? null : excessOfStatutoryTime.clone(),
										temporaryTime == null ? null : temporaryTime.stream().map(t -> t.clone()).collect(Collectors.toList()),
										lateTime == null ? null : lateTime.stream().map(t -> t.clone()).collect(Collectors.toList()),
										leaveEarlyTime == null ? null : leaveEarlyTime.stream().map(t -> t.clone()).collect(Collectors.toList()),
										breakTimeSheet == null ? null : breakTimeSheet.clone(),
										goOutTimeSheet == null ? null : goOutTimeSheet.stream().map(t -> t.clone()).collect(Collectors.toList()),
										shortWorkTime == null ? null : shortWorkTime.clone(), 
										raisingSalaryTime == null ? null : raisingSalaryTime.clone(), 
										dailyOfHoliday == null ? null : dailyOfHoliday.clone(), 
										workTimes,
										vacationAddTime,
										intervalTime,
										intervalAttendanceClock,
										calcDiffTime);
	}
	
	public static TotalWorkingTimeDto fromTotalWorkingTime(TotalWorkingTime domain) {
		return domain == null ? null
				: new TotalWorkingTimeDto(getAttendanceTime(domain.getTotalTime()),
						getAttendanceTime(domain.getTotalCalcTime()), getAttendanceTime(domain.getActualTime()),
						WithinStatutoryTimeDailyPerformDto
								.fromWithinStatutoryTimeDailyPerform(domain.getWithinStatutoryTimeOfDaily()),
						ExcessOfStatutoryTimeDailyPerformDto
								.fromExcessOfStatutoryTimeDailyPerform(domain.getExcessOfStatutoryTimeOfDaily()),
								domain.getTemporaryTime() == null ? new ArrayList<>() : domain.getTemporaryTime().getTemporaryTime() == null ? new ArrayList<>()
								: ConvertHelper.mapTo(domain.getTemporaryTime().getTemporaryTime(),
										(c) -> new TemporaryTimeFrameDto(c.getWorkNo().v(),
												getAttendanceTime(c.getTemporaryLateNightTime()),
												getAttendanceTime(c.getTemporaryTime()))),
						ConvertHelper.mapTo(domain.getLateTimeOfDaily(),
								(c) -> new LateEarlyTimeDailyPerformDto(CalcAttachTimeDto.toTimeWithCal(c.getLateTime()),
										CalcAttachTimeDto.toTimeWithCal(c.getLateDeductionTime()),
										getValicationUseDto(c.getTimePaidUseTime()),
										getAttendanceTime(c.getExemptionTime().getExemptionTime()), c.getWorkNo().v())),
						ConvertHelper.mapTo(domain.getLeaveEarlyTimeOfDaily(),
								(c) -> new LateEarlyTimeDailyPerformDto(CalcAttachTimeDto.toTimeWithCal(c.getLeaveEarlyTime()),
										CalcAttachTimeDto.toTimeWithCal(c.getLeaveEarlyDeductionTime()),
										getValicationUseDto(c.getTimePaidUseTime()),
										getAttendanceTime(c.getIntervalTime().getExemptionTime()), c.getWorkNo().v())),
						BreakTimeSheetDailyPerformDto.fromBreakTimeOfDaily(domain.getBreakTimeOfDaily()),
						ConvertHelper.mapTo(domain.getOutingTimeOfDailyPerformance(), c -> GoOutTimeSheetDailyPerformDto.toDto(c)),
						ShortWorkTimeDto.toDto(domain.getShotrTimeOfDaily()), 
						RaisingSalaryTimeDailyPerformDto.toDto(domain.getRaiseSalaryTimeOfDailyPerfor()), 
//						null,
						HolidayDailyPerformDto.from(domain.getHolidayOfDaily()), 
						domain.getWorkTimes() == null ? null : domain.getWorkTimes().v(),
						domain.getVacationAddTime() == null ? null : domain.getVacationAddTime().valueAsMinutes(),
						domain.getIntervalTime().getIntervalTime().v(),
						domain.getIntervalTime().getIntervalAttendance().v(),
						domain.getCalcDiffTime().v());
	}

	private static ValicationUseDto getValicationUseDto(TimevacationUseTimeOfDaily c) {
		return c == null ? null
				: new ValicationUseDto(getAttendanceTime(c.getTimeAnnualLeaveUseTime()),
						getAttendanceTime(c.getSixtyHourExcessHolidayUseTime()),
						getAttendanceTime(c.getTimeSpecialHolidayUseTime()),
						getAttendanceTime(c.getTimeCompensatoryLeaveUseTime()));
	}

	private static Integer getAttendanceTime(AttendanceTime domain) {
		return domain == null ? null : domain.valueAsMinutes();
	}

	public TotalWorkingTime toDomain() {
		TotalWorkingTime total = new TotalWorkingTime(toAttendanceTime(totalWorkingTime), toAttendanceTime(totalCalcTime),
				toAttendanceTime(actualTime), withinStatutoryTime == null ? WithinStatutoryTimeOfDaily.defaultValue() : withinStatutoryTime.toDomain(),
				excessOfStatutoryTime == null ? null : excessOfStatutoryTime.toDomain(),
				ConvertHelper.mapTo(lateTime, (c) -> new LateTimeOfDaily(
											createTimeWithCalc(c.getTime()),
											createTimeWithCalc(c.getDeductionTime()), new WorkNo(c.getNo()),
											createTimeValication(c.getValicationUseTime()),
											new IntervalExemptionTime(toAttendanceTime(c.getIntervalExemptionTime())))),
				ConvertHelper.mapTo(leaveEarlyTime, (c) -> new LeaveEarlyTimeOfDaily(
											createTimeWithCalc(c.getTime()),
											createTimeWithCalc(c.getDeductionTime()), new WorkNo(c.getNo()),
											createTimeValication(c.getValicationUseTime()),
											new IntervalExemptionTime(toAttendanceTime(c.getIntervalExemptionTime())))),
				breakTimeSheet == null ? BreakTimeSheetDailyPerformDto.defaultValue() : breakTimeSheet.toDmain(), 
				ConvertHelper.mapTo(goOutTimeSheet, c -> c.toDomain()), 
				raisingSalaryTime == null ? new RaiseSalaryTimeOfDailyPerfor(new ArrayList<>(), new ArrayList<>()) 
						: raisingSalaryTime.toDomain(),
				workTimes == null ? null : new WorkTimes(workTimes),
				new TemporaryTimeOfDaily(ConvertHelper.mapTo(temporaryTime, (c) -> new TemporaryFrameTimeOfDaily(new WorkNo(c.getNo()),
										toAttendanceTime(c.getTemporaryTime()),
										toAttendanceTime(c.getTemporaryNightTime())))),
				shortWorkTime == null ? ShortWorkTimeDto.defaultDomain() : shortWorkTime.toDomain(),
				dailyOfHoliday == null ? HolidayDailyPerformDto.defaulDomain() : dailyOfHoliday.toDomain(),
				IntervalTimeOfDaily.of(new AttendanceClock(intervalAttendanceClock), new AttendanceTime(intervalTime)));
		
		if(vacationAddTime != null) {
			total.setVacationAddTime(new AttendanceTime(vacationAddTime));
		}
		total.setCalcDiffTime(new AttendanceTime(calcDiffTime));
		return total;
	}

	private AttendanceTime toAttendanceTime(Integer time) {
		return time == null ? AttendanceTime.ZERO : new AttendanceTime(time);
	}

	private TimevacationUseTimeOfDaily createTimeValication(ValicationUseDto c) {
		return new TimevacationUseTimeOfDaily(toAttendanceTime(c == null ? null : c.getTimeAnnualLeaveUseTime()),
						toAttendanceTime(c == null ? null : c.getTimeCompensatoryLeaveUseTime()),
						toAttendanceTime(c == null ? null : c.getExcessHolidayUseTime()),
						toAttendanceTime(c == null ? null : c.getTimeSpecialHolidayUseTime()));
	}

	private TimeWithCalculation createTimeWithCalc(CalcAttachTimeDto c) {
		return c == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) : c.createTimeWithCalc();
	}
}
