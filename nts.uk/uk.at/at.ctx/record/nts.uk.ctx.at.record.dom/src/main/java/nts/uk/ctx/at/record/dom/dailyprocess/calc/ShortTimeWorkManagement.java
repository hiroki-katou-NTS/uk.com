package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkingTimeSheet;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;

/**
 * 短時間勤務管理
 * @author ken_takasu
 *
 */
public class ShortTimeWorkManagement {

	private List<ShortTimeOfDailyPerformance> shortTimeOfDailyPerformance;
	private ShortWorkTimeOfDaily shortWorkTimeOfDaily;
	
	
	/**
	 * 育児時間の取得
	 * @author ken_takasu
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> getChildCareTime(WorkTimeCommonSet workTimeCommonSet,
														   TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		List<TimeSheetOfDeductionItem> timeSheetList = new ArrayList<TimeSheetOfDeductionItem>();
		//育児介護区分分ループ
		for(ShortTimeOfDailyPerformance shortTimeOfDaily :shortTimeOfDailyPerformance) {
			for(ShortWorkingTimeSheet timeSheet : shortTimeOfDaily.getShortWorkingTimeSheets()) {
				timeSheetList.add(judgeGetChildCareCalcSet(workTimeCommonSet,timeSheet,timeLeavingOfDailyPerformance));
			}
		}
		return timeSheetList;
	}
	
	/**
	 * 育児計算の設定取得
	 * @author ken_takasu
	 * @param timeSheet
	 * @return
	 */
	private TimeSheetOfDeductionItem judgeGetChildCareCalcSet(WorkTimeCommonSet workTimeCommonSet,
															  ShortWorkingTimeSheet timeSheet,
															  TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		if(timeSheet.getChildCareAttr().isChildCare()) {
			return calcShortTime(workTimeCommonSet.getShortTimeWorkSet().isChildCareWorkUse(),timeSheet,timeLeavingOfDailyPerformance);
		}else {
			return calcShortTime(workTimeCommonSet.getShortTimeWorkSet().isNursTimezoneWorkUse(),timeSheet,timeLeavingOfDailyPerformance);
		}	
	}
	
	/**
	 * 出退勤と重複している部分を削除
	 * @author ken_takasu
	 * @return
	 */
	private TimeSheetOfDeductionItem calcShortTime(boolean shortTimeWorkSet,
												   ShortWorkingTimeSheet timeSheet,
												   TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {	
		TimeSpanForCalc calcRange = new TimeSpanForCalc(timeSheet.getStartTime(), timeSheet.getEndTime());
		if(shortTimeWorkSet) {
			//出退勤と重複している部分を削除
			for(TimeLeavingWork timeLeavingWork : timeLeavingOfDailyPerformance.getTimeLeavingWorks()) {
				if(timeLeavingWork.getAttendanceStamp().getStamp().isPresent()&&timeLeavingWork.getLeaveStamp().getStamp().isPresent()) {
					TimeSpanForCalc stanpTimeSheet = new TimeSpanForCalc(timeLeavingWork.getAttendanceStamp().getStamp().get().getAfterRoundingTime(),
																		 timeLeavingWork.getLeaveStamp().getStamp().get().getAfterRoundingTime());
					Optional<TimeSpanForCalc> notDuplicateTime = calcRange.getNotDuplicationWith(stanpTimeSheet);
					if(notDuplicateTime.isPresent()) {
						calcRange = notDuplicateTime.get();
					}
				}
			}
		}
		return TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(new TimeSpanWithRounding(calcRange.getStart(), calcRange.getEnd(), Finally.empty()), 
																			  calcRange, 
																			  Collections.emptyList(), 
																			  Collections.emptyList(), 
																			  Collections.emptyList(), 
																			  Optional.empty(), 
																			  Optional.empty(), 
																			  Optional.empty(), 
																			  DeductionClassification.CHILD_CARE);
	}
	
}
