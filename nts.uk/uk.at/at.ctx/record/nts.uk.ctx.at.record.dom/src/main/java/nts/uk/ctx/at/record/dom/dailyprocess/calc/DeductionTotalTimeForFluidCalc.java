package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidWorkSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 控除合計時間　（local）
 * @author ken_takasu
 *
 */
@Value
public class DeductionTotalTimeForFluidCalc {

	private AttendanceTime totalTime;
	private AttendanceTime goOutTimeOffsetRemainingTime;
	
	
	/**
	 * 流動休憩時間帯の作成
	 * @return
	 */
	public DeductionTotalTimeForFluidCalc createDeductionTotalTime(
			FluRestTimeSetting fluRestTimeSetting,
			FluidWorkSetting fluidWorkSetting,
			List<TimeSheetOfDeductionItem> goOutDeductionTimelist) {

		// 流動休憩開始までの間にある外出分、休憩をズラす
		
		//流動休憩設定を取得する
		if(fluidWorkSetting.getRestSetting().getFluidWorkBreakSettingDetail().getFluidBreakTimeSet().isConbineStamp()) {//併用する場合
			//休憩と外出の重複処理
			duplicationProcessingOfFluidBreakTime(fluRestTimeSetting.getFluidRestTime(),goOutDeductionTimelist);
		}else {//併用しない場合
			//休憩と外出の重複処理
			duplicationProcessingOfFluidBreakTime(fluRestTimeSetting.getFluidRestTime(),goOutDeductionTimelist);
			//外出と休憩の合計時間相殺処理
						
		}
		
	}
	
	/**
	 * 流動休憩開始までの間にある外出分、休憩をズラす
	 * @return
	 */
	public DeductionTotalTimeForFluidCalc collectDeductionTotalTime(
			List<TimeSheetOfDeductionItem> list,/*外出時間帯リスト*/
			AttendanceTime getGoOutStartClock,
			FluidWorkSetting fluidWorkSetting,
			int roopNo) {
		
		//経過時間を取得（一時的に作成、後々削除予定）
		AttendanceTime elpsedTime = fluidWorkSetting.getWeekdayWorkTime().getWorkTimeSheet().
				getMatchWorkNoOverTimeWorkSheet(roopNo).getFluidWorkTimeSetting().getElapsedTime();	
		//流動休憩開始時刻の作成
		AttendanceTime startTime = getGoOutStartClock.addMinutes(elpsedTime.addMinutes(this.totalTime.valueAsMinutes()));
		//外出時間帯リスト作成に使用する時間帯の作成
		TimeSpanForCalc timeSheet = new TimeSpanForCalc(timeSpan.getStart().valueAsMinutes(), startTime.valueAsMinutes());
		//外出時間帯リストの作成
		List<TimeSpanForCalc> duplicateList = createGoOutTimeSheetList(timeSheet, list);
		//外出時間帯分ループ
		for(TimeSpanForCalc goOutTimeSheet : duplicateList) {
			//外出時間を計算
			
			//丸め処理
			
			//
			
		}
			
	}
	
	
	/*
	 * 外出時間帯リストを作成する
	 */
	public List<TimeSpanForCalc> createGoOutTimeSheetList(
			TimeSpanForCalc timeSpan,
			List<TimeSheetOfDeductionItem> list/*外出時間帯リスト*/){
		
		List<TimeSpanForCalc> duplicateList = new ArrayList<>();
		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : list) {
			TimeSpanForCalc t = timeSpan.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet()).orElse(null);
			if(t!=null) {
				duplicateList.add(t);
			}
		}
		return duplicateList;
	}
	
	
	/**
	 * 流動休憩の重複処理
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> duplicationProcessingOfFluidBreakTime(AttendanceTime restTime,List<TimeSheetOfDeductionItem> deductionTimeList){
		//
				
	}
	
}
