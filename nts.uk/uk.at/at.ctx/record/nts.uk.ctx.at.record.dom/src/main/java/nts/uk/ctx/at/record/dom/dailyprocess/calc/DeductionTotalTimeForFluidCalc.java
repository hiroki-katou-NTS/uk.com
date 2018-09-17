package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.calcset.RelationSetOfGoOutAndFluBreakTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestClockCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 控除合計時間　（local）
 * @author ken_takasu
 *
 */
@Getter
@AllArgsConstructor
public class DeductionTotalTimeForFluidCalc {

	private AttendanceTime totalTime;
	private AttendanceTime goOutTimeOffsetRemainingTime;
	
	
//	/**
//	 * 流動休憩時間帯の作成
//	 * @param fluRestTimeSetting　流動休憩設定
//	 * @param getGoOutStartClock 外出取得開始時刻
//	 * @param roopNo ループ回数
//	 * @param fluidWorkSetting 流動勤務設定
//	 * @param deductionTimeSheet 控除時間帯(親ロジックで取得した控除時間帯)
//	 * @return
//	 */
//	public TimeSheetOfDeductionItem createDeductionFluidRestTime(
//			FluRestTimeSetting fluRestTimeSetting,
//			AttendanceTime getGoOutStartClock,/*intで渡せばよくね？*/
//			int roopNo,
//			FluidWorkSetting fluidWorkSetting,
//			DeductionTimeSheet deductionTimeSheet,
//			RelationSetOfGoOutAndFluBreakTime relation,
//			FlowRestClockCalcMethod clockCalcMethod,
//			TimeLeavingWork time,
//			CalcMethodIfLeaveWorkDuringBreakTime calcMethod) {
//
//		// 流動休憩開始までの間にある外出分、休憩をズラす
//		//外出のみの控除時間帯リストを作成する
//		List<TimeSheetOfDeductionItem> goOutDeductionTimelist = 
//				this.forDeductionTimeZoneList.stream().filter(ts -> ts.getGoOutReason().isPresent()).collect(Collectors.toList());
//		if(restTimeSheetList.size()>0) {
//			goOutDeductionTimelist.addAll(restTimeSheetList);
//			goOutDeductionTimelist.stream().sorted((first,second) -> first.calcrange.getStart().compareTo(second.calcrange.getStart())).collect(Collectors.toList());
//		}
//		
//		collectDeductionTotalTime(
//				goOutDeductionTimelist,
//				getGoOutStartClock,
//				fluidWorkSetting,
//				roopNo,
//				relation);
//		//休憩時間帯クラスを作成  
//		TimeSheetOfDeductionItem breakTimeSheet = ;
//		//休憩と外出の重複処理
//		duplicationProcessingOfFluidBreakTime(breakTimeSheet,goOutDeductionTimelist);
//		
//		//流動休憩設定を取得する
//		if(fluidWorkSetting.getRestSetting().getFluidWorkBreakSettingDetail().getFluidBreakTimeSet().isConbineStamp()) {//併用する場合 
//			//外出と休憩の合計時間相殺処理
//			subtractionGoOutTimeOffsetRemainingTime(
//					breakTimeSheet,
//					this.goOutTimeOffsetRemainingTime,
//					clockCalcMethod);		
//		}else {//併用しない場合
//	
//		}
//		//退勤が含まれている場合の補正
//		//deductionTimeSheet.getIncludeAttendanceOrLeaveDuplicateTimeSheet(time, calcMethod, oneDayRange);
//		Optional<TimeSpanForCalc> newTimeSpan = breakTimeSheet.getIncludeAttendanceOrLeaveDuplicateTimeSheet(time, calcMethod, breakTimeSheet.calcrange);
//		if(newTimeSpan.isPresent()) {
//			breakTimeSheet = TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(new TimeSpanWithRounding(newTimeSpan.get().getStart(), newTimeSpan.get().getEnd(), breakTimeSheet.timeSheet.getRounding()),
//																							newTimeSpan.get(),
//																							deductionTimeSheets,
//																							bonusPayTimeSheet,
//																							specifiedBonusPayTimeSheet,
//																							midNighttimeSheet,
//																							breakTimeSheet.getGoOutReason(),
//																							breakTimeSheet.getBreakAtr(),
//																							breakTimeSheet.getDeductionAtr());
//		}
//		//休憩時間帯を返す
//		return breakTimeSheet;		
//	}
	
	/**
	 * 流動休憩開始までの間にある外出分、休憩をズラす (この処理は合計時間をズラすだけ)
	 * @param list
	 * @param getGoOutStartClock 外出取得開始時刻
	 * @param fluidWorkSetting
	 * @param roopNo
	 */
	public void collectDeductionTotalTime(
			List<TimeSheetOfDeductionItem> list,/*外出時間帯リスト*/
			AttendanceTime getGoOutStartClock,/*intで渡せばよくね？*/
			FixedWorkSetting fluidWorkSetting,
			int roopNo,
			RelationSetOfGoOutAndFluBreakTime relation
			) {
		
//		//経過時間を取得（一時的に作成、後々削除予定）
		AttendanceTime elpsedTime = new AttendanceTime(0);//fluidWorkSetting.getLstHalfDayWorkTimezone().getWorkTimeSheet().
				//getMatchWorkNoOverTimeWorkSheet(roopNo).get().getFluidWorkTimeSetting().getElapsedTime();	
//		//流動休憩開始時刻の作成
		AttendanceTime startTime = new AttendanceTime(getGoOutStartClock.valueAsMinutes() + elpsedTime.valueAsMinutes() + this.totalTime.valueAsMinutes());
//		//外出時間帯リスト作成に使用する時間帯の作成
		TimeSpanForCalc timeSheet;
		if(relation.isCalculation()) {
			timeSheet = new TimeSpanForCalc(new TimeWithDayAttr(getGoOutStartClock.valueAsMinutes()),new TimeWithDayAttr(startTime.valueAsMinutes()));
		}
		else {
			timeSheet = new TimeSpanForCalc(new TimeWithDayAttr(getGoOutStartClock.valueAsMinutes() - 1),new TimeWithDayAttr(startTime.valueAsMinutes() - 1));
		}
		//外出時間帯リストの作成
		List<TimeSheetOfDeductionItem> duplicateList = createGoOutTimeSheetList(timeSheet, list);
		
		AttendanceTime beforeLoopStartTime = startTime;
		AttendanceTime beforeLoopgoOutTimeOffSet = this.goOutTimeOffsetRemainingTime;
		AttendanceTime beforeLoopTotalTime = this.totalTime;
		boolean breakLoop = false;
		while(!breakLoop) {
			//初めからループをやり直すための値リセット
			int beforeLoopListSize = duplicateList.size();
			this.goOutTimeOffsetRemainingTime = beforeLoopgoOutTimeOffSet;
			this.totalTime = beforeLoopTotalTime;
			startTime = beforeLoopStartTime;
			//外出時間帯分ループ
			//for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : duplicateList) {
			for(int itemNumber = 0 ; itemNumber < duplicateList.size() ; itemNumber++) {
				//外出時間を計算
				AttendanceTime calcTime = duplicateList.get(itemNumber).calcTotalTime(DeductionAtr.Deduction);
				//丸め処理(今は一時的に丸めていない値を直接入れている)
				//int goOutdeductionTime = calcTime;
				//控除時間分、流動休憩時間の開始をズラす
				startTime.addMinutes(calcTime.valueAsMinutes());
				//外出控除時間を控除合計時間に加算
				this.totalTime.addMinutes(startTime.valueAsMinutes());
				//外出控除時間を外出相殺残時間に加算
				this.goOutTimeOffsetRemainingTime.addMinutes(startTime.valueAsMinutes());
				List<TimeSheetOfDeductionItem> createDeductionTimeSheetList = createGoOutTimeSheetList(new TimeSpanForCalc(new TimeWithDayAttr(getGoOutStartClock.valueAsMinutes()),new TimeWithDayAttr(startTime.valueAsMinutes())), duplicateList);
				//ループやり直し
				if(createDeductionTimeSheetList.size() > beforeLoopListSize) {
					duplicateList = createDeductionTimeSheetList;
					break;
				}
				//ループを最後までやり切ったか判定
				if(itemNumber == duplicateList.size() - 1) {
					breakLoop = true;
				}
			}
		}
		//休憩時間を取得
		//AttendanceTime restTime = fluidWorkSetting.getWeekdayWorkTime().getRestTime().getFluidRestTime().getMatchElapsedTime(elpsedTime).getFluidRestTime();
		//経過時間を取得
		//AttendanceTime fluidElapsedTime = fluidWorkSetting.getWeekdayWorkTime().getRestTime().getFlowRes.getMatchElapsedTime(elpsedTime).getFluidRestTime();
		AttendanceTime fluidElapsedTime = new AttendanceTime(0);
		//休憩時間を控除合計時間．合計時間に加算
		this.totalTime.addMinutes(fluidElapsedTime.valueAsMinutes());		
	}
	
	
	/*
	 * 外出時間帯リストを作成する
	 */
	public List<TimeSheetOfDeductionItem> createGoOutTimeSheetList(
			TimeSpanForCalc timeSpan,
			List<TimeSheetOfDeductionItem> list/*外出時間帯リスト*/){
		
		List<TimeSheetOfDeductionItem> duplicateList = new ArrayList<>();
		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : list) {
			TimeSpanForCalc t = timeSpan.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet().timeSpan()).orElse(null);
			if(t!=null) {
				duplicateList.add(timeSheetOfDeductionItem);
			}
		}
		return duplicateList;
	}
	
	
	/**
	 * 流動休憩の重複処理
	 * @param ｔimeSheetOfDeductionItem 「休憩時間帯クラスを作成」で作成した休憩
	 * @param deductionTimeList 外出時間帯のみの控除時間帯
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> duplicationProcessingOfFluidBreakTime(
											TimeSheetOfDeductionItem timeSheetOfDeductionItem,
											List<TimeSheetOfDeductionItem> deductionTimeList,
											TimeSheetOfDeductionItem compareTimeSheet,
											WorkTimeMethodSet setMethod,
											RestClockManageAtr clockManage,
											boolean useFixedRestTime,
											FluidFixedAtr fluidFixedAtr,	
											WorkTimeDailyAtr workTimeDailyAtr){
		//休憩時間帯と重複している控除時間帯を取得
		List<TimeSheetOfDeductionItem> duplicateList = deductionTimeList.stream().filter(ts -> timeSheetOfDeductionItem.getTimeSheet().timeSpan().checkDuplication(ts.calcrange) != TimeSpanDuplication.NOT_DUPLICATE ).collect(Collectors.toList());
		while(true) {
			//控除時間帯分ループ
			for(int itemNumber = 0 ; itemNumber < duplicateList.size();itemNumber++) { //TimeSheetOfDeductionItem goOutTime : duplicateList) {
				timeSheetOfDeductionItem.DeplicateBreakGoOut(compareTimeSheet,setMethod,clockManage
															,useFixedRestTime,fluidFixedAtr,workTimeDailyAtr);
			}
		}
	//	return duplicateList;
	}
	

	/**
	 *  外出と休憩の相殺処理　　（外出相殺残時間）
	 *  外出相殺残時間がメンバーになっているため、ここはvoidでOK
	 * @param ｔimeSheetOfDeductionItem
	 * @param restTime 親ロジックで作成した休憩時間
	 */
	public void subtractionGoOutTimeOffsetRemainingTime(
			TimeSheetOfDeductionItem ｔimeSheetOfDeductionItem,
			AttendanceTime restTime,
			FlowRestClockCalcMethod clockCalcMethod) {
		//休憩時間から相殺できる時間を計算　　　(ValueObject相殺時間の作成)
		AttendanceTime offsetTime;
		if(this.goOutTimeOffsetRemainingTime.greaterThanOrEqualTo(restTime)) {
			offsetTime = restTime;
			
		}else {
			offsetTime = this.goOutTimeOffsetRemainingTime;
		}
		//相殺時間＞0の場合の処理
		if(offsetTime.valueAsMinutes()>0) {
			//相殺時間帯を相殺時間分縮める
			Optional<TimeSpanForCalc> afterShrinkingTimeSheet = ｔimeSheetOfDeductionItem.contractTimeSheet(new TimeWithDayAttr(offsetTime.valueAsMinutes()));
			//afterShrinkingTimeSheetを時間帯にした休憩時間帯クラスを再作成する
			
			//外出相殺残時間から相殺時間を減算
			if(afterShrinkingTimeSheet.isPresent())
				this.goOutTimeOffsetRemainingTime.minusHours(afterShrinkingTimeSheet.get().lengthAsMinutes());
		}
		//外出と休憩の相殺処理設定を取得
		if(clockCalcMethod.isRestTimeToCalculate()) {
			
		}
	}
	
	/**
	 * 相殺した外出を休憩とする
	 * @param goOutTimeSheetList 控除用時間帯リストから取得できるすべての外出時間帯
	 * @param offSetTime 相殺時間
	 */
	public void OffsetTimeToBreak(List<TimeSheetOfDeductionItem> goOutTimeSheetList,AttendanceTime offSetTime) {
		AttendanceTime breakTime = new AttendanceTime(0);
		//or(TimeSheetOfDeductionItem goOutTimeSheet : goOutTimeSheetList) {
		for(int itemNumber = 0 ; itemNumber < goOutTimeSheetList.size();itemNumber++) {
			AttendanceTime goOutTime = goOutTimeSheetList.get(itemNumber).calcTotalTime(DeductionAtr.Deduction);
			//goOutTime全てを休憩時間に帰る
			if(offSetTime.greaterThan(goOutTime.v())) {
				breakTime = goOutTime;
			}
			else {
				breakTime = offSetTime;
			}
			Optional<TimeWithDayAttr> baseTime = goOutTimeSheetList.get(itemNumber).getNewEndTime(goOutTimeSheetList.get(itemNumber).getCalcrange(),new TimeWithDayAttr(breakTime.valueAsMinutes()));
			
			if(baseTime.isPresent()) {
				/**
				 * baseTimeを基に時間帯分割
				 */
				List<TimeSheetOfDeductionItem> afterSplitTimeSheet = splitTimeSheet(breakTime,baseTime.get(),goOutTimeSheetList.get(itemNumber));
				//まだ振替できる
				if(breakTime.greaterThan(0)) {
					goOutTimeSheetList.remove(itemNumber);
					goOutTimeSheetList.addAll(afterSplitTimeSheet);
				}
				//振替できない(振替時間 = 0)
				else {
					goOutTimeSheetList.remove(itemNumber);
					goOutTimeSheetList.addAll(afterSplitTimeSheet);
					break;	
				}
				offSetTime.minusMinutes(breakTime.valueAsMinutes());
			}

		}
	}
	
	/**
	 * １つの時間帯を指定時間で分割する
	 * @param
	 * @param
	 * @return 分割後の時間帯たち
	 */
	private List<TimeSheetOfDeductionItem> splitTimeSheet(AttendanceTime breakTime ,TimeWithDayAttr baseTime, TimeSheetOfDeductionItem deductionItem){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		//分割する場合
		if(breakTime.greaterThan(0)) {
			//前を休憩へ
			returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
					   new TimeZoneRounding(deductionItem.timeSheet.getStart(), baseTime, deductionItem.timeSheet.getRounding())
					  ,new TimeSpanForCalc(deductionItem.calcrange.getStart(), baseTime)
					  ,deductionItem.recreateDeductionItemBeforeBase(baseTime, true,DeductionAtr.Appropriate)
					  ,deductionItem.recreateDeductionItemBeforeBase(baseTime, true,DeductionAtr.Deduction)
					  ,deductionItem.recreateBonusPayListBeforeBase(baseTime, true)
					  ,deductionItem.recreateSpecifiedBonusPayListBeforeBase(baseTime, true)
					  ,deductionItem.recreateMidNightTimeSheetBeforeBase(baseTime, true)
					  ,deductionItem.getWorkingBreakAtr()
					 ,deductionItem.getGoOutReason()
					 ,Finally.of(BreakClassification.BREAK_STAMP)
					 ,Optional.empty()
					 ,DeductionClassification.BREAK
					 ,deductionItem.getChildCareAtr()));
			//後ろを外出のままに
			returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed( 
					   new TimeZoneRounding(baseTime, deductionItem.timeSheet.getEnd(), deductionItem.timeSheet.getRounding())
					  ,new TimeSpanForCalc(baseTime, deductionItem.calcrange.getEnd())
					  ,deductionItem.recreateDeductionItemBeforeBase(baseTime, false,DeductionAtr.Appropriate)
					  ,deductionItem.recreateDeductionItemBeforeBase(baseTime, false,DeductionAtr.Deduction)
					  ,deductionItem.recreateBonusPayListBeforeBase(baseTime, false)
					  ,deductionItem.recreateSpecifiedBonusPayListBeforeBase(baseTime, false)
					  ,deductionItem.recreateMidNightTimeSheetBeforeBase(baseTime, false)
					  ,deductionItem.getWorkingBreakAtr()
					  ,deductionItem.getGoOutReason()
					  ,deductionItem.getBreakAtr()
					  ,Optional.empty()
					  ,deductionItem.getDeductionAtr()
					  ,deductionItem.getChildCareAtr()));
		}
		//分割しない場合
		else {
			//全部休憩へ
			returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
					  deductionItem.timeSheet
					 ,deductionItem.calcrange
					 ,deductionItem.recordedTimeSheet
					 ,deductionItem.deductionTimeSheet
					 ,deductionItem.bonusPayTimeSheet
					 ,deductionItem.specBonusPayTimesheet
					 ,deductionItem.midNightTimeSheet
					 ,deductionItem.getWorkingBreakAtr()
					 ,deductionItem.getGoOutReason()
					 ,Finally.of(BreakClassification.BREAK_STAMP)
					 ,Optional.empty()
					 ,DeductionClassification.BREAK
					 ,deductionItem.getChildCareAtr()));
		}
		return returnList;
	}
	
}



