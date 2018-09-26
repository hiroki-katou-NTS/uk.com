package nts.uk.ctx.at.record.dom.daily;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.latetime.IntervalExemptionTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateLeaveEarlyTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;


/**
 * 日別実績の早退時間
 * @author ken_takasu
 *
 */
@Getter
public class LeaveEarlyTimeOfDaily {
	//早退時間
	private TimeWithCalculation leaveEarlyTime;
	//早退控除時間
	private TimeWithCalculation leaveEarlyDeductionTime;
	//勤務No
	private WorkNo workNo;
	//休暇使用時間
	private TimevacationUseTimeOfDaily timePaidUseTime;
	//インターバル時間
	private IntervalExemptionTime intervalTime;
	
	
	public LeaveEarlyTimeOfDaily(TimeWithCalculation leaveEarlyTime, TimeWithCalculation lateDeductionTime, WorkNo workNo,
			TimevacationUseTimeOfDaily timePaidUseTime, IntervalExemptionTime exemptionTime) {
		this.leaveEarlyTime = leaveEarlyTime;
		this.leaveEarlyDeductionTime = lateDeductionTime;
		this.workNo = workNo;
		this.timePaidUseTime = timePaidUseTime;
		this.intervalTime = exemptionTime;
	}
	
	/**
	 * 早退時間のみ更新
	 * @param leaveEarlyTime
	 */
	public void rePlaceLeaveEarlyTime(TimeWithCalculation leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	
	public static LeaveEarlyTimeOfDaily noLeaveEarlyTimeOfDaily() {
		return new LeaveEarlyTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)),
										 TimeWithCalculation.sameTime(new AttendanceTime(0)),
										 new WorkNo(1),
										 new TimevacationUseTimeOfDaily(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0)),
										 new IntervalExemptionTime());
	}
	
	/**
	 * 早退時間の計算
	 * @param oneDay
	 * @param workNo
	 * @param late
	 * @param holidayCalcMethodSet
	 * @return
	 */
	public static LeaveEarlyTimeOfDaily calcLeaveEarlyTime(CalculationRangeOfOneDay oneDay,
											   WorkNo workNo,
											   boolean leaveEarly, //日別実績の計算区分.遅刻早退の自動計算設定.早退
											   HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting
			) {

		//勤務Noに一致する早退時間をListで取得する
		List<LeaveEarlyTimeSheet> leaveEarlyTimeSheetList = oneDay.getWithinWorkingTimeSheet().isPresent()?oneDay.getWithinWorkingTimeSheet().get().getWithinWorkTimeFrame()
																												.stream().map(t -> t.getLeaveEarlyTimeSheet().orElse(null))
																												.filter(t -> t != null && workNo.compareTo(t.getWorkNo()) == 0 && t.getForDeducationTimeSheet().isPresent())																												 
																												 .sorted((leaveEarlyTimeSheet1,leaveEarlyTimeSheet2) -> leaveEarlyTimeSheet1.getForDeducationTimeSheet().get().getTimeSheet().getStart()
																												 .compareTo(leaveEarlyTimeSheet2.getForDeducationTimeSheet().get().getTimeSheet().getStart()))
																												 .collect(Collectors.toList()):new ArrayList<>();
		LateLeaveEarlyTimeSheet forRecordTimeSheet = new LateLeaveEarlyTimeSheet(new TimeZoneRounding(new TimeWithDayAttr(0),new TimeWithDayAttr(0),new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN)),
				  																 new TimeSpanForCalc(new TimeWithDayAttr(0),new TimeWithDayAttr(0)));

		LateLeaveEarlyTimeSheet forDeductTimeSheet = new LateLeaveEarlyTimeSheet(new TimeZoneRounding(new TimeWithDayAttr(0),new TimeWithDayAttr(0),new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN)),
																				 new TimeSpanForCalc(new TimeWithDayAttr(0),new TimeWithDayAttr(0)));
		if(!leaveEarlyTimeSheetList.isEmpty()) {
			if(leaveEarlyTimeSheetList.get(0).getForRecordTimeSheet().isPresent()) {
				//早退時間帯を１つの時間帯にする。
				forRecordTimeSheet = new LateLeaveEarlyTimeSheet(new TimeZoneRounding(leaveEarlyTimeSheetList.get(0).getForRecordTimeSheet().get().getTimeSheet().getStart(),
																										  leaveEarlyTimeSheetList.get(leaveEarlyTimeSheetList.size()-1).getForRecordTimeSheet().get().getTimeSheet().getEnd(),
																										  leaveEarlyTimeSheetList.get(0).getForRecordTimeSheet().get().getTimeSheet().getRounding()),
																					 new TimeSpanForCalc(leaveEarlyTimeSheetList.get(0).getForRecordTimeSheet().get().getTimeSheet().getStart(),
																							 			 leaveEarlyTimeSheetList.get(leaveEarlyTimeSheetList.size()-1).getForRecordTimeSheet().get().getTimeSheet().getEnd()));
			
				forRecordTimeSheet.setDeductionTimeSheet(leaveEarlyTimeSheetList.stream().flatMap(t -> t.getForRecordTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
				forRecordTimeSheet.setRecordedTimeSheet(leaveEarlyTimeSheetList.stream().flatMap(t -> t.getForRecordTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
			}
			forDeductTimeSheet = new LateLeaveEarlyTimeSheet(new TimeZoneRounding(leaveEarlyTimeSheetList.get(0).getForDeducationTimeSheet().get().getTimeSheet().getStart(),
																									  leaveEarlyTimeSheetList.get(leaveEarlyTimeSheetList.size()-1).getForDeducationTimeSheet().get().getTimeSheet().getEnd(),
																									  leaveEarlyTimeSheetList.get(0).getForDeducationTimeSheet().get().getTimeSheet().getRounding()),
																				new TimeSpanForCalc(leaveEarlyTimeSheetList.get(0).getForDeducationTimeSheet().get().getTimeSheet().getStart(),
																									leaveEarlyTimeSheetList.get(leaveEarlyTimeSheetList.size()-1).getForDeducationTimeSheet().get().getTimeSheet().getEnd()));
		
			forDeductTimeSheet.setDeductionTimeSheet(leaveEarlyTimeSheetList.stream().flatMap(t -> t.getForDeducationTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
			forDeductTimeSheet.setRecordedTimeSheet(leaveEarlyTimeSheetList.stream().flatMap(t -> t.getForDeducationTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
		}
		
		LeaveEarlyTimeSheet leaveEarlyTimeSheet = new LeaveEarlyTimeSheet(Optional.of(forRecordTimeSheet), Optional.of(forDeductTimeSheet), workNo.v(), Optional.empty());
		
		//遅刻・早退を控除する
//		NotUseAtr notDeductLateLeaveEarly = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()?holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly()
//																															  :NotUseAtr.NOT_USE;
		NotUseAtr notDeductLateLeaveEarly = NotUseAtr.NOT_USE;
		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()) {
			if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().isDeductLateLeaveEarly(commonSetting)) {
				notDeductLateLeaveEarly = NotUseAtr.USE;
			}
		}
		
		//早退計上時間の計算
		TimeWithCalculation leaveEarlyTime = leaveEarlyTimeSheet.calcForRecordTime(leaveEarly);
		//早退控除時間の計算
		TimeWithCalculation leaveEarlyDeductionTime = leaveEarlyTimeSheet.calcDedctionTime(leaveEarly,notDeductLateLeaveEarly);
		
		LeaveEarlyTimeOfDaily LeaveEarlyTimeOfDaily = new LeaveEarlyTimeOfDaily(leaveEarlyTime,
															  					leaveEarlyDeductionTime,
															  					workNo,
															  					new TimevacationUseTimeOfDaily(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0)),
															  					new IntervalExemptionTime(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0)));
		return LeaveEarlyTimeOfDaily;
		
	}
	
	/**
	 * 早退時間のエラーチェック 
	 * @return エラーである。
	 */
	public List<EmployeeDailyPerError>  checkError(String employeeId,
												  GeneralDate targetDate,
												  String searchWord,
												  AttendanceItemDictionaryForCalc attendanceItemDictionary,
												  ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorList = new ArrayList<>();
		if(this.getLeaveEarlyTime().getTime().greaterThan(0)) {
			val itemId = attendanceItemDictionary.findId(searchWord+this.workNo.v());
			if(itemId.isPresent())
				returnErrorList.add(new EmployeeDailyPerError(AppContexts.user().companyCode(), employeeId, targetDate, errorCode, itemId.get()));
		}
		return returnErrorList;
	}
	
	/**
	 * 休暇加算時間の計算
	 * @return
	 */
	public int calcVacationAddTime(Optional<HolidayAddtionSet> holidayAddtionSet) {
		int result = 0;	
		int totalAddTime = this.timePaidUseTime.calcTotalVacationAddTime(holidayAddtionSet, AdditionAtr.WorkingHoursOnly);	
		if(this.leaveEarlyTime.getCalcTime().lessThanOrEqualTo(totalAddTime)) {
			result = this.leaveEarlyTime.getCalcTime().valueAsMinutes();
		}else {
			result = totalAddTime;
		}
		return result;
	}
}
