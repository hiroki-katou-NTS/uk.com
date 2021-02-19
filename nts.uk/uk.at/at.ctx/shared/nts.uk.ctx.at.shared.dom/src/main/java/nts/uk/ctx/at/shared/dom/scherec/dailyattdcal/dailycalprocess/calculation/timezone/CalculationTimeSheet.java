package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 計算時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public abstract class CalculationTimeSheet {
	//時間帯
	protected TimeSpanForDailyCalc timeSheet;
	//丸め
	protected TimeRoundingSetting rounding;
	@Setter
	//計上用
	protected List<TimeSheetOfDeductionItem> recordedTimeSheet= new ArrayList<>();
	@Setter
	//控除用
	protected List<TimeSheetOfDeductionItem> deductionTimeSheet = new ArrayList<>();
	
	/**
	 * @param 控除用
	 * @param 計上用
	 */
	public CalculationTimeSheet(TimeSpanForDailyCalc timeSheet, 
								TimeRoundingSetting rounding) {
		this.timeSheet = timeSheet;
		this.rounding = rounding;
	}

	
	/**
	 * Constructor
	 * @param timeSheet 時間帯(丸め付き)
	 * @param calcrange 計算範囲
	 * @param midNighttimeSheet 深夜時間帯
	 */
	public CalculationTimeSheet(TimeSpanForDailyCalc timeSheet,
								TimeRoundingSetting rounding,
								List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
								List<TimeSheetOfDeductionItem> deductionTimeSheets
								) {
		this.timeSheet = timeSheet;
		this.rounding = rounding;
		
		this.recordedTimeSheet = recorddeductionTimeSheets;
		this.deductionTimeSheet = deductionTimeSheets;
	}
	
	public void replaceTimeSheet(TimeSpanForDailyCalc calcRange) {
		this.timeSheet = calcRange;
	}
	
	public void replaceOwnDedTimeSheet() {
		List<TimeSheetOfDeductionItem> ded = new ArrayList<>(this.deductionTimeSheet);
		this.deductionTimeSheet.clear();
		this.addDuplicatedDeductionTimeSheet(ded, DeductionAtr.Deduction, Optional.empty());
		List<TimeSheetOfDeductionItem> rec = new ArrayList<>(this.recordedTimeSheet);
		this.recordedTimeSheet.clear();
		this.addDuplicatedDeductionTimeSheet(rec, DeductionAtr.Appropriate, Optional.empty());
	}
	
	/**
	 * 控除時間帯を入れ替える(この処理は「残業枠時間帯作成」で使用する想定で作成)
	 * @param deductionList 入れ替える控除時間帯(List)
	 */
	public void replaceDeductionTimeSheet(List<DeductionTimeSheet> deductionList) {
		this.deductionTimeSheet.clear();
		this.deductionTimeSheet.addAll(deductionTimeSheet);
	}
	
	
	/**
	 * 指定時間を終了とする時間帯作成
	 * @return
	 */
	public Optional<TimeSpanForDailyCalc> reCreateTreatAsSiteiTimeEnd(AttendanceTime transTime,OverTimeFrameTimeSheetForCalc overTimeWork) {
		return overTimeWork.contractTimeSheet(new TimeWithDayAttr(this.calcTotalTime().valueAsMinutes() - transTime.valueAsMinutes()));
	}
	
//	/**
//	 * 指定時間帯を指定時間に従って縮小
//	 * @param assingnTime 指定時間
//	 * @return 縮小後の時間帯
//	 */
//	public TimeSpanForDailyCalc reduceUntilSpecifiedTime(AttendanceTime assignTime) {
//		//開始時間からの経過時間を求める
//		AttendanceTime shortened = calcTotalTime(DeductionAtr.Deduction).minusMinutes(assignTime.valueAsMinutes());
//		//開始時間と経過時間から新しいEnd時刻を求める
//		AttendanceTime newEnd = new AttendanceTime(timeSheet.getStart().forwardByMinutes(shortened.valueAsMinutes()).valueAsMinutes());
//		TimeZoneRounding newTimeSpan = new TimeZoneRounding(new TimeWithDayAttr(shortened.valueAsMinutes()),new TimeWithDayAttr(newEnd.valueAsMinutes()),this.timeSheet.getRounding());
//		//自身の計算範囲と被っている場所にある控除時間帯を求める
//		List<TimeSheetOfDeductionItem> refineList = getNewSpanIncludeCalcrange(this.deductionTimeSheet,newTimeSpan.timeSpan());
//		
//		while(true) {
//			//控除時間
//			AttendanceTime deductionTime = new AttendanceTime(0);
//			//算出した計算範囲(初期化)
//			newTimeSpan = new TimeZoneRounding(timeSheet.getStart(),new TimeWithDayAttr(newEnd.valueAsMinutes()), this.timeSheet.getRounding());
//			//含んでいる控除時間経過した分だけ終了を未来へずらす
//			for(TimeSheetOfDeductionItem deductionItem : refineList) {
//				deductionTime = deductionItem.calcTotalTime(DeductionAtr.Deduction);
//				newTimeSpan = new TimeZoneRounding(timeSheet.getStart(),newTimeSpan.getEnd().forwardByMinutes(deductionTime.valueAsMinutes()), this.timeSheet.getRounding());
//			}
//			
//			List<TimeSheetOfDeductionItem> moveSpanDuplicateDeductionTimeSheet = duplicateNewTimeSpan(new TimeSpanForDailyCalc(timeSheet.getEnd(), newTimeSpan.getEnd()));
//			if(moveSpanDuplicateDeductionTimeSheet.size()>0) {
//				refineList.addAll(moveSpanDuplicateDeductionTimeSheet);
//			}
//			else {
//				break; 
//			}
//		}
//		
//		return newTimeSpan.timeSpan();
//	}
	
	/**
	 * 指定時間に従って時間帯の縮小
	 * @return 縮小後の時間帯
	 */
	public Optional<TimeSpanForDailyCalc> contractTimeSheet(TimeWithDayAttr timeWithDayAttr) {
		/*ここのcalcTotalTImeは残業時間帯の時間*/
		int afterShort = calcTotalTime().valueAsMinutes() - timeWithDayAttr.valueAsMinutes();
		if(afterShort <= 0) return Optional.empty();
		TimeSpanForDailyCalc newSpan = new TimeSpanForDailyCalc(timeSheet.getTimeSpan().getStart(), timeSheet.getTimeSpan().getStart().forwardByMinutes(afterShort));
		List<TimeSheetOfDeductionItem> copyList = getNewSpanIncludeCalcrange(deductionTimeSheet,newSpan);
		for(int listn = 0 ; listn < copyList.size() ; listn++){
				/*ここのcalcTotalTimeは残業時間帯が持ってる控除時間帯の時間*/
				int differTime = copyList.get(listn).calcTotalTime().valueAsMinutes();
				/*ずらす前に範囲内に入っている時間帯の数を保持*/
				int beforeincludeSpan = copyList.size();//getNewSpanIncludeCalcrange(copyList,newSpan).size();
				//含んでいる控除時間帯分未来へずらす
				newSpan = newSpan.shiftEndAhead(differTime);
//				//
//				val moveAfterNewSpan = newSpan.shiftEndAhead(copyList.stream().map(ts -> ts.calcrange.lengthAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
				//増やしたことで、含む控除時間が増えるかどうかをチェック(含んでいる控除時間帯の数を数えなおす)
				int afterincludeSpan = getNewSpanIncludeCalcrange(deductionTimeSheet,newSpan).size();
				/*ずらした後の範囲に入っている時間帯の数とずらす前のかずを比較し増えていた場合、控除時間帯を保持してる変数に追加する*/
				if(afterincludeSpan > beforeincludeSpan) {
					copyList = Collections.emptyList();
					copyList = getNewSpanIncludeCalcrange(deductionTimeSheet,newSpan);
				}
		}
		return Optional.of(newSpan);
	}
	
	/**
	 *受け取った時間帯に含まれている控除項目の時間帯をリストにする
	 * @param newTimeSpan 時間帯
	 * @return　控除時間帯リスト
	 */
	public List<TimeSheetOfDeductionItem> duplicateNewTimeSpan(TimeSpanForDailyCalc newTimeSpan){
		return deductionTimeSheet.stream().filter(tc -> newTimeSpan.getTimeSpan().contains(tc.timeSheet.getTimeSpan())).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * 　時間帯と重複している控除時間帯のみを抽出する
	 * @param　控除項目の時間帯(List)
	 */
	private List<TimeSheetOfDeductionItem> getNewSpanIncludeCalcrange(List<TimeSheetOfDeductionItem> copyList , TimeSpanForDailyCalc newSpan){
		return copyList.stream().filter(tc -> newSpan.getTimeSpan().checkDuplication(tc.timeSheet.getTimeSpan()).isDuplicated()).collect(Collectors.toList());
	}
	
	/**
	 * 控除時間の合計を算出する
	 * @param dedAtr
	 * @param conditionAtr
	 * @return
	 */
	public AttendanceTime calcDedTimeByAtr(DeductionAtr dedAtr,ConditionAtr conditionAtr) {
		val forCalcList = getDedTimeSheetByAtr(dedAtr,conditionAtr);
		return new AttendanceTime(forCalcList.stream().map(tc -> tc.calcTotalTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
	}
		
	/**
	 * 控除時間の合計を算出する（指定なし)
	 * @param dedAtr
	 * @param conditionAtr
	 * @return
	 */
	public AttendanceTime calcDedTimeByAtr(DeductionAtr dedAtr) {
		val forCalcList = (dedAtr.isDeduction())?this.deductionTimeSheet:this.recordedTimeSheet;
		return new AttendanceTime(forCalcList.stream().map(tc -> tc.calcTotalTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
	}
	
	/**
	 * 条件、控除区分に従って控除項目の時間帯取得
	 * @param dedAtr 控除区分
	 * @param conditionAtr　条件
	 * @return　控除項目の時間帯
	 */
	public List<TimeSheetOfDeductionItem> getDedTimeSheetByAtr(DeductionAtr dedAtr, ConditionAtr conditionAtr) {
		val returnList = (dedAtr.isDeduction())?this.deductionTimeSheet:this.recordedTimeSheet;
		switch(conditionAtr) {
			case BREAK:
				return returnList.stream().filter(tc -> tc.getDeductionAtr().isBreak()).collect(Collectors.toList());
			case Care:
				val list = returnList.stream().filter(tc -> tc.getDeductionAtr().isChildCare()).collect(Collectors.toList());	
				List<TimeSheetOfDeductionItem> list2 = new ArrayList<>();
				for(TimeSheetOfDeductionItem timeSheetOfDeductionItem:list) {
					if(timeSheetOfDeductionItem.getChildCareAtr().isPresent()&&timeSheetOfDeductionItem.getChildCareAtr().get().isCare()) {
						list2.add(timeSheetOfDeductionItem);
					}
				}
				return list2;
			case Child:
				val list3 = returnList.stream().filter(tc -> tc.getDeductionAtr().isChildCare()).collect(Collectors.toList());
				List<TimeSheetOfDeductionItem> list4 = new ArrayList<>();
				for(TimeSheetOfDeductionItem timeSheetOfDeductionItem:list3) {
					if(timeSheetOfDeductionItem.getChildCareAtr().isPresent()&&timeSheetOfDeductionItem.getChildCareAtr().get().isChildCare()) {
						list4.add(timeSheetOfDeductionItem);
					}
				}
				 return list4;					  
			case CompesationGoOut:
				return returnList.stream().filter(tc -> tc.getDeductionAtr().isGoOut() 
													 && tc.getGoOutReason().get().isCompensation()).collect(Collectors.toList());
			case PrivateGoOut:
				return returnList.stream().filter(tc -> tc.getDeductionAtr().isGoOut() 
													 && tc.getGoOutReason().get().isPrivate()).collect(Collectors.toList());
			case PublicGoOut:
				return returnList.stream().filter(tc -> tc.getDeductionAtr().isGoOut()
													&& tc.getGoOutReason().get().isPublic()).collect(Collectors.toList());
			case UnionGoOut:
				return returnList.stream().filter(tc -> tc.getDeductionAtr().isGoOut()
													&& tc.getGoOutReason().get().isUnion()).collect(Collectors.toList());
			default:
				throw new RuntimeException("unknown condition Atr");
		}
	}
	
	/**
	 * 時間の計算(自分自身ー自分が持つ控除時間帯.length)
	 * @return 自分自身ー自分が持つ控除時間帯.length
	 */
	public AttendanceTime calcTotalTime() {
		return deductionLengthMinutes();
	}
	
	/**
	 * 控除時間の計算
	 * @return 控除時間
	 */
	public AttendanceTime calcTotalDeductionTime() {
		return new AttendanceTime(this.getDeductionTimeSheet().stream().map(tc-> tc.calcTotalTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
	}
	
	/**
	 * 時間帯に含んでいる控除時間を差し引いた時間を計算する(メモ：トリガー)
	 * @return 時間
	 */
	private AttendanceTime deductionLengthMinutes() {
		
		if(deductionTimeSheet.isEmpty()) {
			//丸め設定の取得
			if(this.rounding != null) {
				return new AttendanceTime(rounding.round(this.timeSheet.getTimeSpan().lengthAsMinutes()));
			}
			else {
				return new AttendanceTime(this.timeSheet.getTimeSpan().lengthAsMinutes());
			}
		}
		return recursiveTotalTime() ;
	}
	
	/**
	 * 控除時間の合計を求める(メモ：控除項目の時間帯から呼ぶcalcTotalTime()が再帰)
	 * @return　控除の合計時間
	 */
	protected AttendanceTime recursiveTotalTime() {
		List<TimeSheetOfDeductionItem> prodcessList = this.getDeductionTimeSheet();
		if(prodcessList.isEmpty()) return new AttendanceTime(this.timeSheet.getTimeSpan().lengthAsMinutes());
		AttendanceTime totalDedTime = new AttendanceTime(0);
		for(TimeSheetOfDeductionItem dedTimeSheet : prodcessList) {
			totalDedTime = new AttendanceTime(totalDedTime.valueAsMinutes()+dedTimeSheet.calcTotalTime().valueAsMinutes());
		}
		//丸め処理
		return new AttendanceTime(this.rounding.round(this.timeSheet.getTimeSpan().lengthAsMinutes() - totalDedTime.valueAsMinutes()));	
	}

//	/**
//	 * 
//	 * @param basePoint 
//	 * @return 
//	 */
//	public int calcIncludeTimeSheet(int basePoint, List<TimeSheetOfDeductionItem> deductionItemList){
//		return deductionItemList.stream().map(ts -> ts.timeSheet.getTimeSpan().lengthAsMinutes()).collect(Collectors.summingInt(tc -> tc));
//	}
//	
	/**
	 * 開始から指定時間経過後の終了時刻を取得
	 * @param timeSpan　時間帯
	 * @param time　指定時間
	 * @return 指定時間経過後の終了時間
	 */
	public Optional<TimeWithDayAttr> getNewEndTime(TimeSpanForDailyCalc timeSpan, TimeWithDayAttr time) {
		Optional<TimeSpanForDailyCalc> newEnd = createTimeSpan(timeSpan,time);
		if(newEnd.isPresent()) {
			return Optional.of(newEnd.get().getTimeSpan().getEnd());
		}
		else {
			return  Optional.empty();
		}
	}
	
	/**
	 * 開始から指定時間を終了とする時間帯作成
	 * @param timeSpan 時間帯
	 * @param time　指定時間
	 * @return
	 */
	public Optional<TimeSpanForDailyCalc> createTimeSpan(TimeSpanForDailyCalc timeSpan, TimeWithDayAttr time) {
		return contractTimeSheet(time);
	}
	
	/**
	 * 控除時間帯のリストを作り直す
	 * @param baseTime 基準時間
	 * @param isDateBefore 基準時間より早い時間を切り出す
	 * @return 切り出したl控除時間帯
	 */
	public List<TimeSheetOfDeductionItem> recreateDeductionItemBeforeBase(TimeWithDayAttr baseTime,boolean isDateBefore, DeductionAtr dedAtr){
		
		List<TimeSheetOfDeductionItem> deductionList = new ArrayList<>();
		if(dedAtr.isDeduction()) {
			
			deductionList = this.deductionTimeSheet;
		}
		else {
			
			deductionList = this.recordedTimeSheet;
		}
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		
		for(TimeSheetOfDeductionItem deductionItem : deductionList) {
			
			if(deductionItem.contains(baseTime)) {
				returnList.add(deductionItem.reCreateOwn(baseTime,isDateBefore));
				//returnList.add(deductionItem);
			}
			else if(deductionItem.timeSheet.getTimeSpan().getEnd().lessThan(baseTime) && isDateBefore) {
				returnList.add(deductionItem);
			}
			else if(deductionItem.timeSheet.getTimeSpan().getStart().greaterThan(baseTime) && !isDateBefore) {
				returnList.add(deductionItem);
			}
		}
		
		return returnList;
	}
	
//	/**
//	 * 深夜時間帯の作成(トリガー)
//	 * @param midnightTimeSheet
//	 */
//	public Optional<MidNightTimeSheetForCalc> createMidNightTimeSheet() {
//		if(midNightTimeSheet.isPresent()) {
//			if(calcrange.checkDuplication(midNightTimeSheet.get().calcrange).isDuplicated()) { 
//				return Optional.of(new MidNightTimeSheetForCalc(timeSheet,
//										 calcrange.getDuplicatedWith(midNightTimeSheet.get().calcrange).get(),
//										 duplicateTimeSpan(midNightTimeSheet.get().calcrange),
//										 duplicateTimeSpan(midNightTimeSheet.get().calcrange),
//										 bonusPayTimeSheet,
//										 specBonusPayTimesheet,
//										 midNightTimeSheet
//										 ));
//			}
//		}
//		return Optional.empty();
//	}
//	/**
//	 * 特定日加給時間帯の作成
//	 * @param specifiedDayList　特定日
//	 * @return 加給設定
//	 */
//	public List<SpecBonusPayTimeSheetForCalc> createSpecifiedBonusPayTimeSheet(List<Integer> specifiedDayList,List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheetList){
//		if(specifiedDayList.size() == 0) return Collections.emptyList();
//		for(SpecBonusPayTimeSheetForCalc specifiedBonusPayTimeSheet : specifiedBonusPayTimeSheetList) {
//			if(specifiedDayList.contains(specifiedBonusPayTimeSheet.getSpecBonusPayNumber().v().intValue())) {
//				Optional<TimeSpanForDailyCalc> newSpan = this.timeSheet.getTimeSpan().getDuplicatedWith(
//														new TimeSpanForDailyCalc(new TimeWithDayAttr(specifiedBonusPayTimeSheet.getCalcrange().getStart().valueAsMinutes())
//																		   ,new TimeWithDayAttr(specifiedBonusPayTimeSheet.getCalcrange().getEnd().valueAsMinutes())));
//				if(newSpan.isPresent()) {
//					this.specBonusPayTimesheet.add(specifiedBonusPayTimeSheet.convertForCalcCorrectRange(newSpan.get()));
//				}
//			}
//		}
//		return this.specBonusPayTimesheet;
//	}
//	
//	/**
//	 * 深夜時間の計算
//	 * @return 深夜時間
//	 */
//	public AttendanceTime calcMidNight(AutoCalAtrOvertime autoCalcSet) {
//		if(autoCalcSet.isCalculateEmbossing())
//		{
//			if(this.midNightTimeSheet.isPresent()) {
//				return this.midNightTimeSheet.get().calcTotalTime(DeductionAtr.Deduction);
//			}
//			else {
//				return new AttendanceTime(0);
//			}
//		}
//		else {
//			return new AttendanceTime(0);
//		}
//	}
	
	/**
	 * 自身と重複している控除時間帯に絞り込む
	 * @param deductionTimeSheet
	 * @return
	 */
	private List<TimeSheetOfDeductionItem> getDuplicatedDeductionTimeSheet(List<TimeSheetOfDeductionItem> deductionTimeSheet) {
		if(deductionTimeSheet == null) return deductionTimeSheet;
		return deductionTimeSheet.stream()
								 .filter(tc -> tc != null)
						  		 .filter(tc -> this.getTimeSheet().getDuplicatedWith(tc.getTimeSheet()).isPresent())
						  		 .map(tc -> tc.createDuplicateRange(this.getTimeSheet().getDuplicatedWith(tc.getTimeSheet()).get()).get())
						  		 .collect(Collectors.toList());
	}
	
	/**
	 * 自分に重複している控除時間帯になるように補正して保持する
	 * @param dedAtr 渡すリストが控除か計上用か
	 * @param roundingSet 変更したい丸め設定(そのままでいい場合、emptyで)
	 */
	public void addDuplicatedDeductionTimeSheet(List<TimeSheetOfDeductionItem> deductionTimeSheet,DeductionAtr dedAtr,Optional<TimeRoundingSetting> roundingSet) {
		if(roundingSet.isPresent()) {
			deductionTimeSheet = deductionTimeSheet.stream().map(tc -> TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(tc.getTimeSheet(), 
																																	  roundingSet.get(), 
																																	  tc.getRecordedTimeSheet(), 
																																	  tc.getDeductionTimeSheet(), 
																																	  tc.getWorkingBreakAtr(),
																																	  tc.getGoOutReason(), 
																																	  tc.getBreakAtr(), 
																																	  tc.getShortTimeSheetAtr(), 
																																	  tc.getDeductionAtr(),
																																	  tc.getChildCareAtr()))
															.collect(Collectors.toList());
		}
		if(dedAtr.isAppropriate()) {
			if(this.recordedTimeSheet != null && !this.recordedTimeSheet.isEmpty()) {
				val test = getDuplicatedDeductionTimeSheet(deductionTimeSheet);
				if(test != null)
					this.recordedTimeSheet.addAll(test);
			}
			else {
				val test = getDuplicatedDeductionTimeSheet(deductionTimeSheet);
				if(test != null)
					this.recordedTimeSheet = test;
			}
				
		}
		else {
			if(this.deductionTimeSheet != null && !this.deductionTimeSheet.isEmpty()) {
				val test = getDuplicatedDeductionTimeSheet(deductionTimeSheet);
				if(test != null)
					this.deductionTimeSheet.addAll(test);
			}
			else {
				val test = getDuplicatedDeductionTimeSheet(deductionTimeSheet);
				if(test != null)
					this.deductionTimeSheet = test;

			}
		}
	}
	

//	/**
//	 * 日別実績の特定日区分を基に加給として使用する日を判定する
//	 * @param bpTimeSheets　加給時間帯
//	 * @param specNoList　特定日NOリスト
//	 * @return 使用する加給時間帯
//	 */
//	private static List<BonusPayTimeSheetForCalc> getUseBpTimeSheet(List<BonusPayTimeSheetForCalc> bpTimeSheets,List<SpecificDateItemNo> specNoList){
//		List<BonusPayTimeSheetForCalc> returnList = new ArrayList<>();
//		for(BonusPayTimeSheetForCalc bpTimeSheet:bpTimeSheets) {
//			if(!specNoList.contains(new SpecificDateItemNo(bpTimeSheet.getRaiseSalaryTimeItemNo().v().intValue())))
//				returnList.add(bpTimeSheet);
//		}
//		return returnList;
//	}
	
	/**
	 *　指定条件の控除項目だけの控除時間(再起のトリガー)
	 * @param forcsList
	 * @param atr
	 * @return
	 */
	public AttendanceTime forcs(ConditionAtr atr,DeductionAtr dedAtr){
		AttendanceTime dedTotalTime = new AttendanceTime(0);
		dedTotalTime = new AttendanceTime(getDedTimeSheetByAtr(dedAtr, atr).stream().map(tc -> tc.calcTotalTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		return dedTotalTime;
	}

	/**
	 * 短時間勤務時間帯の収集
	 * @return　短時間勤務時間帯List
	 */
	public List<TimeSheetOfDeductionItem> collectShortTimeSheet() {
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		List<TimeSheetOfDeductionItem> shortTimeSheet = new ArrayList<>();
		//控除
		if(this.getDeductionTimeSheet() != null) {
			shortTimeSheet = this.getRecordedTimeSheet().stream().map(tc -> tc.collectShortTime()).flatMap(List::stream).collect(Collectors.toList());
			returnList.addAll(shortTimeSheet);
		}
		return returnList;
	}
	
	/**
	 * 控除時間帯へ丸め設定を付与
	 * @param actualAtr 実働時間帯区分
	 * @param commonSet 就業時間帯の共通設定
	 */
	public void grantRoundingToDeductionTimeSheet(ActualWorkTimeSheetAtr actualAtr, WorkTimezoneCommonSet commonSet){
		
		//計上用控除時間帯に丸め設定を付与
		this.grantRoundingDeductionOrAppropriate(actualAtr, DeductionAtr.Appropriate, commonSet);
		
		//控除用控除時間帯に丸め設定を付与
		this.grantRoundingDeductionOrAppropriate(actualAtr, DeductionAtr.Deduction, commonSet);
	}
	
	/**
	 * ループ処理（控除時間帯へ丸め設定を付与）
	 * @param actualAtr 実働時間帯区分
	 * @param dedAtr 控除 or 計上
	 * @param commonSet 就業時間帯の共通設定
	 */
	private void grantRoundingDeductionOrAppropriate(ActualWorkTimeSheetAtr actualAtr, DeductionAtr dedAtr, WorkTimezoneCommonSet commonSet){
		
		this.deductionTimeSheet.forEach(dt -> {
			if((dt.getDeductionAtr().isBreak() && dt.getBreakAtr().get().isBreakStamp()) || dt.getDeductionAtr().isGoOut()) {
				//付与する丸めを判断
				dt.getRounding().correctData(
						dt.decisionAddRounding(
								this.rounding, 
								actualAtr, 
								dedAtr, 
								commonSet).get());
			}
			else {
				//全て1分切り捨て
				dt.getRounding().setDefaultDataRoundingDown();
			}
		});
	}
	
	/**
	 * 受け取った控除時間帯を自身の計算範囲へ補正＆絞り込む
	 * 
	 * @param timeSpan
	 *            計算範囲
	 * @param atr
	 *            控除区分
	 * @return 控除項目の時間帯リスト(控除区分に従ってList取得)
	 */
	public List<TimeSheetOfDeductionItem> getDupliRangeTimeSheet(List<TimeSheetOfDeductionItem> dedList) {
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		for (TimeSheetOfDeductionItem timeSheet : dedList) {
			val dupCalcRange = timeSheet.getTimeSheet().getDuplicatedWith(this.timeSheet);
			if (dupCalcRange.isPresent()) {
				TimeSheetOfDeductionItem divideStartTime = timeSheet.reCreateOwn(dupCalcRange.get().getTimeSpan().getStart(), false);
				TimeSheetOfDeductionItem correctAfterTimeSheet = divideStartTime.reCreateOwn(dupCalcRange.get().getTimeSpan().getEnd(), true);
				returnList.add(correctAfterTimeSheet);
			}
		}
		return returnList;
	}
	
	/**
	 * 時間帯を指定された時間帯に変更する（計上と控除も補正する）
	 * @param timeSheet 開始時刻～終了時刻
	 */
	public void shiftTimeSheet(TimeSpanForDailyCalc timeSheet) {
		this.timeSheet = timeSheet;
		this.trimRecordedAndDeductionToSelfRange();
	}
	
	/**
	 * 開始時刻を指定された時刻に変更する（計上と控除も補正する）
	 * @param start 開始時刻
	 */
	public void shiftStart(TimeWithDayAttr start) {
		this.timeSheet = this.timeSheet.shiftOnlyStart(start);
		this.trimRecordedAndDeductionToSelfRange();
	}
	
	/**
	 * 終了時刻を指定された時刻に変更する（計上と控除も補正する）
	 * @param end 終了時刻
	 */
	public void shiftEnd(TimeWithDayAttr end) {
		this.timeSheet = this.timeSheet.shiftOnlyEnd(end);
		this.trimRecordedAndDeductionToSelfRange();
	}
	
	/**
	 * 計上と控除を自身の計算範囲へ補正する
	 */
	public void trimRecordedAndDeductionToSelfRange() {
		
		List<TimeSheetOfDeductionItem> rec = new ArrayList<>(this.recordedTimeSheet);
		this.recordedTimeSheet.clear();
		this.recordedTimeSheet.addAll(this.getDupliRangeTimeSheet(rec));
		
		List<TimeSheetOfDeductionItem> ded = new ArrayList<>(this.deductionTimeSheet);
		this.deductionTimeSheet.clear();
		this.deductionTimeSheet.addAll(this.getDupliRangeTimeSheet(ded));
	}
	
	/**
	 * 時間帯を前から指定時間分抜き出す
	 * @param time 指定時間
	 * @return 日別計算時間帯
	 */
	public Optional<TimeSpanForDailyCalc> extractForward(TimeWithDayAttr time){
		// 不要になる時間　←　全体の時間　－　指定時間
		int diff = this.calcTotalTime().valueAsMinutes() - time.valueAsMinutes();
		// 時間帯を指定時間に従って縮小
		return this.contractTimeSheet(new TimeWithDayAttr(diff));
	}
	
	/**
	 * 時間帯を後ろから指定時間分抜き出す
	 * @param time 指定時間
	 * @return 日別計算時間帯
	 */
	public Optional<TimeSpanForDailyCalc> extractBackword(TimeWithDayAttr time){
		// 時間帯を指定時間に従って縮小
		Optional<TimeSpanForDailyCalc> diff = this.contractTimeSheet(time);
		// 必要になる時間帯を判断し、返す
		if (!diff.isPresent()) return Optional.empty();
		return Optional.of(new TimeSpanForDailyCalc(
				new TimeWithDayAttr(diff.get().getEnd().valueAsMinutes()),
				new TimeWithDayAttr(this.getTimeSheet().getEnd().valueAsMinutes())));
	}
}
