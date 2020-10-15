package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeVacationOffSetItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 遅刻早退時間帯
 * @author ken_takasu
 *
 */
public class LateLeaveEarlyTimeSheet extends TimeVacationOffSetItem{

	public LateLeaveEarlyTimeSheet(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding) {
		super(timeSheet, rounding);
	}
	
	//遅刻再度補正
	public LateLeaveEarlyTimeSheet collectionAgainOfLate(LateLeaveEarlyTimeSheet calcTime, List<TimeSheetOfDeductionItem> breakTimeList) {
		//休憩取得範囲分の時間帯
		//TimeSpanForDailyCalc calcTimeSheet = new TimeSpanForDailyCalc(this.getTimeSheet().getStart(), this.getTimeSheet().getStart().forwardByMinutes(calcTime.calcTotalTime(DeductionAtr.Deduction).valueAsMinutes()+calcTime.calcDedTimeByAtr(DeductionAtr.Deduction).valueAsMinutes()));
		//丸め後の遅刻時間帯
		TimeSpanForDailyCalc lateTimeSheet = new TimeSpanForDailyCalc(this.getTimeSheet().getStart(), this.getTimeSheet().getStart().forwardByMinutes(calcTime.calcTotalTime().valueAsMinutes()));
		List<TimeSheetOfDeductionItem> hasList = new ArrayList<>();
				
		List<TimeSheetOfDeductionItem> deductionTimeSheetList = breakTimeList.stream().sorted((time1,time2) -> time1.getTimeSheet().getStart().compareTo(time2.getTimeSheet().getStart())).collect(Collectors.toList());
		//控除時間帯分ループ
		for(TimeSheetOfDeductionItem deduTimeSheet:deductionTimeSheetList) {
			//int deductTime = lateTimeSheet.getDuplicatedWith(deductionTimeSheet.getTimeSheet().getTimeSpan()).isPresent()?lateTimeSheet.getDuplicatedWith(deductionTimeSheet.getTimeSheet().getTimeSpan()).get().lengthAsMinutes():0;
			int deductTime = lateTimeSheet.getDuplicatedWith(deduTimeSheet.getTimeSheet()).isPresent()?deduTimeSheet.calcTotalTime().valueAsMinutes():0;
			if(lateTimeSheet.isContinus(deduTimeSheet.getTimeSheet())) {
				deductTime = deduTimeSheet.calcTotalTime().valueAsMinutes();
			}
			//控除時間分、終了時刻を後ろにズラす
			lateTimeSheet = new TimeSpanForDailyCalc(lateTimeSheet.getStart(), lateTimeSheet.getEnd().forwardByMinutes(deductTime));
			
			hasList.add(deduTimeSheet);
		}
		LateLeaveEarlyTimeSheet result = new LateLeaveEarlyTimeSheet(new TimeSpanForDailyCalc(lateTimeSheet.getStart(), lateTimeSheet.getEnd()),
																	 this.rounding);
		
		//控除時間帯を保持
		result.addDuplicatedDeductionTimeSheet(hasList,DeductionAtr.Deduction,Optional.empty());
		result.addDuplicatedDeductionTimeSheet(hasList,DeductionAtr.Appropriate,Optional.empty());

		return result;
	}
	
	//再度補正
	public LateLeaveEarlyTimeSheet collectionAgainOfEarly(LateLeaveEarlyTimeSheet calcTime) {
		//休憩取得範囲分の時間帯
//		TimeSpanForDailyCalc calcTimeSheet = new TimeSpanForDailyCalc(this.getTimeSheet().getStart(), this.getTimeSheet().getStart().backByMinutes(calcTime.calcTotalTime().valueAsMinutes()+calcTime.calcDedTimeByAtr(DeductionAtr.Deduction).valueAsMinutes()));
		TimeSpanForDailyCalc calcTimeSheet = new TimeSpanForDailyCalc(new TimeWithDayAttr(this.getTimeSheet().getEnd().backByMinutes(calcTime.calcTotalTime().valueAsMinutes()+calcTime.calcDedTimeByAtr(DeductionAtr.Deduction).valueAsMinutes()).valueAsMinutes())
															,this.getTimeSheet().getEnd());
		//丸め後の早退時間帯
		TimeSpanForDailyCalc EarlyTimeSheet = new TimeSpanForDailyCalc(new TimeWithDayAttr(this.getTimeSheet().getEnd().backByMinutes(calcTime.calcTotalTime().valueAsMinutes()).valueAsMinutes())
															 ,this.getTimeSheet().getEnd());
				
		List<TimeSpanForDailyCalc> deductionTimeSheetList = this.getDeductionTimeSheet().stream().map(t -> t.getTimeSheet()).sorted((time1,time2) -> time1.getStart().compareTo(time2.getStart())).collect(Collectors.toList());
		//控除時間帯分ループ
		for(TimeSpanForDailyCalc deductionTimeSheet:deductionTimeSheetList) {
			TimeSpanForDailyCalc timeSheet = new TimeSpanForDailyCalc(deductionTimeSheet.getStart(), deductionTimeSheet.getEnd());
			int deductTime = calcTimeSheet.getDuplicatedWith(timeSheet).isPresent()?calcTimeSheet.getDuplicatedWith(timeSheet).get().lengthAsMinutes():0;
			//控除時間分、開始時刻を手前にズラす
			EarlyTimeSheet = new TimeSpanForDailyCalc(EarlyTimeSheet.getStart().backByMinutes(deductTime), EarlyTimeSheet.getEnd());
		}
		LateLeaveEarlyTimeSheet result = new LateLeaveEarlyTimeSheet(new TimeSpanForDailyCalc(EarlyTimeSheet.getStart(), EarlyTimeSheet.getEnd()),
																	 this.rounding);
		//控除時間帯を保持
		result.addDuplicatedDeductionTimeSheet(this.getDeductionTimeSheet(),DeductionAtr.Deduction,Optional.empty());
		result.addDuplicatedDeductionTimeSheet(this.getDeductionTimeSheet(),DeductionAtr.Appropriate,Optional.empty());

		return result;
	}
}
