package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	public TimeSheetOfDeductionItem createDeductionFluidRestTime(
			FluRestTimeSetting fluRestTimeSetting,
			AttendanceTime getGoOutStartClock,/*intで渡せばよくね？*/
			TimeSheetOfDeductionItem ｔimeSheetOfDeductionItem,
			int roopNo,
			FluidWorkSetting fluidWorkSetting,
			DeductionTimeSheet deductionTimeSheet,
			List<TimeSheetOfDeductionItem> goOutDeductionTimelist) {

		// 流動休憩開始までの間にある外出分、休憩をズラす
		collectDeductionTotalTime(
				goOutDeductionTimelist,
				getGoOutStartClock,
				fluidWorkSetting,
				roopNo);
		//休憩時間帯クラスを作成  
		
		//流動休憩設定を取得する
		if(fluidWorkSetting.getRestSetting().getFluidWorkBreakSettingDetail().getFluidBreakTimeSet().isConbineStamp()) {//併用する場合
			//休憩と外出の重複処理
			duplicationProcessingOfFluidBreakTime(
					ｔimeSheetOfDeductionItem,/*1つ前の処理で作成した休憩時間帯（控除項目の時間帯）*/
					goOutDeductionTimelist);
		}else {//併用しない場合
			//休憩と外出の重複処理
			duplicationProcessingOfFluidBreakTime();
			//休憩時間を取得
			AttendanceTime restTime = ｔimeSheetOfDeductionItem;/*上で作成した休憩時間帯クラスから休憩時間を作成*/ 
			//外出と休憩の合計時間相殺処理
			subtractionGoOutTimeOffsetRemainingTime(
					ｔimeSheetOfDeductionItem,/*1つ前の処理で作成した休憩時間帯（控除項目の時間帯）*/
					restTime);			
		}
		//退勤が含まれている場合の補正
		deductionTimeSheet.getIncludeAttendanceOrLeaveDuplicateTimeSheet(time, calcMethod, oneDayRange);
		//休憩時間帯クラスの開始時刻を退避
		
		//休憩時間帯を返す
		return ;		
	}
	
	/**
	 * 流動休憩開始までの間にある外出分、休憩をズラす (この処理は合計時間をズラすだけ)
	 */
	public void collectDeductionTotalTime(
			List<TimeSheetOfDeductionItem> list,/*外出時間帯リスト*/
			AttendanceTime getGoOutStartClock,/*intで渡せばよくね？*/
			FluidWorkSetting fluidWorkSetting,
			int roopNo) {
		
		//経過時間を取得（一時的に作成、後々削除予定）
		AttendanceTime elpsedTime = fluidWorkSetting.getWeekdayWorkTime().getWorkTimeSheet().
				getMatchWorkNoOverTimeWorkSheet(roopNo).getFluidWorkTimeSetting().getElapsedTime();	
		//流動休憩開始時刻の作成
		AttendanceTime startTime = new AttendanceTime(getGoOutStartClock.valueAsMinutes() + elpsedTime.valueAsMinutes() + this.totalTime.valueAsMinutes());
		//外出時間帯リスト作成に使用する時間帯の作成
		TimeSpanForCalc timeSheet = new TimeSpanForCalc(new TimeWithDayAttr(getGoOutStartClock.valueAsMinutes()),new TimeWithDayAttr(startTime.valueAsMinutes()));
		//外出時間帯リストの作成
		List<TimeSheetOfDeductionItem> duplicateList = createGoOutTimeSheetList(timeSheet, list);
		//外出時間帯分ループ
		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : duplicateList) {
			//外出時間を計算
			int calcTime = timeSheetOfDeductionItem.calcTotalTime();
			//丸め処理(今は一時的に丸めていない値を直接入れている)
			int goOutdeductionTime = calcTime;
			//控除時間分、流動休憩時間の開始をズラす
			AttendanceTime collectStartTime = startTime;
			collectStartTime.addMinutes(goOutdeductionTime);
			//外出控除時間を控除合計時間に加算
			this.totalTime.addMinutes(goOutdeductionTime);
			//ズラした事によって間に外出が増えた場合は追加
			if(!startTime.equals(collectStartTime)) {
				//ズラした時間分の時間帯を作成
				TimeSpanForCalc increaseTimeSpan = new TimeSpanForCalc(new TimeWithDayAttr(startTime.valueAsMinutes()),new TimeWithDayAttr(collectStartTime.valueAsMinutes()));
				//
				for(TimeSheetOfDeductionItem deductionTimeSheet : list) {
					TimeSpanForCalc duplicatetime = increaseTimeSpan.getDuplicatedWith(deductionTimeSheet.getTimeSheet()).orElse(null);
					if(duplicatetime!=null) {
						duplicateList.add(deductionTimeSheet);
					}
				}
			}
		}
		//休憩時間を取得
		AttendanceTime restTime = fluidWorkSetting.getWeekdayWorkTime().getRestTime().getFluidRestTime().getMatchElapsedTime(elpsedTime).getFluidRestTime();
		//休憩時間を控除合計時間．合計時間に加算
		this.totalTime.addMinutes(restTime.valueAsMinutes());		
	}
	
	
	/*
	 * 外出時間帯リストを作成する
	 */
	public List<TimeSheetOfDeductionItem> createGoOutTimeSheetList(
			TimeSpanForCalc timeSpan,
			List<TimeSheetOfDeductionItem> list/*外出時間帯リスト*/){
		
		List<TimeSheetOfDeductionItem> duplicateList = new ArrayList<>();
		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : list) {
			TimeSpanForCalc t = timeSpan.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet()).orElse(null);
			if(t!=null) {
				duplicateList.add(timeSheetOfDeductionItem);
			}
		}
		return duplicateList;
	}
	
	
	/**
	 * 流動休憩の重複処理
	 * @param ｔimeSheetOfDeductionItem
	 * @param deductionTimeList 外出時間帯のみの控除時間帯
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> duplicationProcessingOfFluidBreakTime(
			TimeSheetOfDeductionItem ｔimeSheetOfDeductionItem,
			List<TimeSheetOfDeductionItem> deductionTimeList){
		//休憩時間帯と重複している控除時間帯を取得
		List<TimeSheetOfDeductionItem> duplicateList = deductionTimeList.stream().filter(ts -> ts.getTimeSheet().getSpan().compare(ｔimeSheetOfDeductionItem.getTimeSheet().getSpan())).collect(Collectors.toList());
		//控除時間帯分ループ
		for(TimeSheetOfDeductionItem goOutTime : duplicateList) {
			ｔimeSheetOfDeductionItem.DeplicateBreakGoOut(compareTimeSheet, setMethod, clockManage, useFixedRestTime);
		}
		return ;
	}
	
	/**
	 * 外出と休憩の相殺処理　　（外出相殺残時間）
	 */
	public void subtractionGoOutTimeOffsetRemainingTime(
			TimeSheetOfDeductionItem ｔimeSheetOfDeductionItem,
			AttendanceTime restTime) {
		//休憩時間から相殺できる時間を計算　　　(ValueObject相殺時間の作成)
		AttendanceTime OffsetTime;
		if(this.goOutTimeOffsetRemainingTime.greaterThanOrEqualTo(restTime)) {
			OffsetTime = restTime;
		}else {
			OffsetTime = this.goOutTimeOffsetRemainingTime;
		}
		//相殺時間＞0の場合の処理
		if(OffsetTime.valueAsMinutes()>0) {
			//相殺時間帯を相殺時間分縮める
			TimeSpanForCalc afterShrinkingTimeSheet = ｔimeSheetOfDeductionItem.syukusyou(OffsetTime.valueAsMinutes());
			//afterShrinkingTimeSheetを時間帯にした休憩時間帯クラスを再作成する
			
			//外出相殺残時間から相殺時間を減算
			this.goOutTimeOffsetRemainingTime.addMinutes(-OffsetTime.valueAsMinutes());
		}
	}
	
	
}
