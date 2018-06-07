package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 控除項目の時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class TimeSheetOfDeductionItem extends CalculationTimeSheet{
	private Finally<GoingOutReason> goOutReason;
	private Finally<BreakClassification> breakAtr;
	private final DeductionClassification deductionAtr;
	
	/**
	 * 控除項目の時間帯作成
	 * @param timeSpan
	 * @param goOutReason
	 * @param breakAtr
	 * @param deductionAtr
	 * @param withinStatutoryAtr
	 */
	private TimeSheetOfDeductionItem(TimeZoneRounding withRounding
									,TimeSpanForCalc timeSpan
									,List<TimeSheetOfDeductionItem> recorddeductionTimeSheets
									,List<TimeSheetOfDeductionItem> deductionTimeSheets
									,List<BonusPayTimeSheetForCalc> bonusPayTimeSheet
									,List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet
									,Optional<MidNightTimeSheetForCalc> midNighttimeSheet
									,Finally<GoingOutReason> goOutReason
									,Finally<BreakClassification> breakAtr
									,DeductionClassification deductionAtr) {
		super(withRounding,timeSpan,recorddeductionTimeSheets,deductionTimeSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet);
		this.goOutReason = goOutReason;
		this.breakAtr = breakAtr;
		this.deductionAtr = deductionAtr;
	}
	
	/**
	 * 控除項目の時間帯作成　
	 * @param timeSpan
	 * @param goOutReason
	 * @param breakAtr
	 * @param deductionAtr
	 * @param withinStatutoryAtr
	 * @return
	 */
	public static TimeSheetOfDeductionItem createTimeSheetOfDeductionItemAsFixed(TimeZoneRounding withRounding
			,TimeSpanForCalc timeSpan
			,List<TimeSheetOfDeductionItem> recorddeductionTimeSheets
			,List<TimeSheetOfDeductionItem> deductionTimeSheets
			,List<BonusPayTimeSheetForCalc> bonusPayTimeSheet
			,List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet
			,Optional<MidNightTimeSheetForCalc> midNighttimeSheet
			,Finally<GoingOutReason> goOutReason
			,Finally<BreakClassification> breakAtr
			,DeductionClassification deductionAtr) {
		
		return new TimeSheetOfDeductionItem(
				withRounding
				,timeSpan
				,recorddeductionTimeSheets
				,deductionTimeSheets
				,bonusPayTimeSheet
				,specifiedBonusPayTimeSheet
				,midNighttimeSheet
				,goOutReason
				,breakAtr
				,deductionAtr);
	}
	
	/**
	 * 受けとった計算範囲で時間を入れ替える
	 * @param timeSpan　時間帯
	 * @return　控除項目の時間帯
	 */
	public TimeSheetOfDeductionItem replaceTimeSpan(TimeSpanForCalc timeSpan) {
		return new TimeSheetOfDeductionItem(
											new TimeZoneRounding(timeSpan.getStart(), timeSpan.getEnd(), this.timeSheet.getRounding()),
											timeSpan,
											this.recordedTimeSheet,
											this.deductionTimeSheet,
											this.bonusPayTimeSheet,
											this.specBonusPayTimesheet,
											this.midNightTimeSheet,
											this.goOutReason,
											this.breakAtr,
											this.deductionAtr
											);
	}
	
	/**
	 * 範囲を比較したい対象
	 * @return
	 */
	public Optional<TimeSheetOfDeductionItem> createDuplicateRange(TimeSpanForCalc timeSpan) {
		//重複範囲取得
		val duplicateSpan = timeSpan.getDuplicatedWith(this.calcrange);
		//重複有
		if(duplicateSpan.isPresent())
			return Optional.of(this.replaceTimeSpan(duplicateSpan.get()));
		//重複無
		return Optional.empty();
	}
	

	/**
	 * 控除項目の時間帯の法定内区分を法定外へ置き換える
	 * @return 法定内区分を法定外に変更した控除項目の時間帯
	 */
	public TimeSheetOfDeductionItem StatutoryAtrFromWithinToExcess() {
		return new TimeSheetOfDeductionItem(this.timeSheet, 
											this.calcrange, 
											this.recordedTimeSheet,
											this.deductionTimeSheet, 
											this.bonusPayTimeSheet,
											this.specBonusPayTimesheet, 
											this.midNightTimeSheet, 
											this.goOutReason, 
											this.breakAtr, 
											this.deductionAtr);
	}
	
	/**
	 * 控除時間帯と控除時間帯の重複チェック
	 * @param baseTimeSheet 現ループ中のリスト　
	 * @param compareTimeSheet　次のループで取り出すリスト　
	 */
	public List<TimeSheetOfDeductionItem> DeplicateBreakGoOut(TimeSheetOfDeductionItem compareTimeSheet,WorkTimeMethodSet setMethod,RestClockManageAtr clockManage
															,boolean useFixedRestTime,FluidFixedAtr fluidFixedAtr,WorkTimeDailyAtr workTimeDailyAtr) {
		List<TimeSheetOfDeductionItem> map = new ArrayList<TimeSheetOfDeductionItem>();
	
		/*両方とも育児　*/
		/*if文の中身を別メソッドに実装する*/
		if(this.getDeductionAtr().isChildCare() && compareTimeSheet.getDeductionAtr().isChildCare()) {
			map.add(this);
			map.add(compareTimeSheet.replaceTimeSpan(compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()));
			return map;
		}
		/*前半育児　　後半外出*/
		else if(this.getDeductionAtr().isChildCare() && compareTimeSheet.getDeductionAtr().isGoOut()) {
			map.add(this);
			map.add(compareTimeSheet.replaceTimeSpan(compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()));
			return map;
		}
		/*前半外出、、後半育児*/
		else if(this.getDeductionAtr().isGoOut() && compareTimeSheet.getDeductionAtr().isChildCare()) {
			map.add(this.replaceTimeSpan(this.calcrange.getNotDuplicationWith(compareTimeSheet.calcrange).get()));
			map.add(compareTimeSheet);
			return map;
		}
		
		/*前半休憩、後半外出*/
		else if((this.getDeductionAtr().isBreak() && compareTimeSheet.getDeductionAtr().isGoOut())){
			if(!fluidFixedAtr.isFluidWork()) {
				TimeSpanForCalc duplicationSpan = this.getCalcrange().getDuplicatedWith(compareTimeSheet.getCalcrange()).get();
				map.add(this.replaceTimeSpan(this.calcrange.getNotDuplicationWith(compareTimeSheet.calcrange).get()));
				//List<TimeSheetOfDeductionItem> replaceDeductionItemList = new ArrayList();//this.deductionTimeSheets;
				compareTimeSheet.deductionTimeSheet.add(new TimeSheetOfDeductionItem(new TimeZoneRounding(duplicationSpan.getStart(), duplicationSpan.getEnd(), null)
																		 , duplicationSpan
																		 , Collections.emptyList()
																		 , Collections.emptyList()
																		 , Collections.emptyList()
																		 , Collections.emptyList()
																		 , Optional.empty()
																		 , this.getGoOutReason()
																		 , this.breakAtr
																		 , this.getDeductionAtr()));
				
				map.add(compareTimeSheet);
				return map;
			}
			else {
				if(setMethod.isFluidWork()) {
					if(!useFixedRestTime) {
						if(clockManage.isNotClockManage()) {
							return collectionBreakTime(this,compareTimeSheet);
						}
					}
				}
			}
			map.add(this);
			map.add(compareTimeSheet.replaceTimeSpan(compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()));
			return map;
		}
		/*前半外出、後半休憩*/
		else if(this.getDeductionAtr().isGoOut() && compareTimeSheet.getDeductionAtr().isBreak()){
			if(!fluidFixedAtr.isFluidWork()) {
				TimeSpanForCalc duplicationSpan = compareTimeSheet.getCalcrange().getDuplicatedWith(this.getCalcrange()).get();
				map.add(compareTimeSheet.replaceTimeSpan(compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()));
				this.deductionTimeSheet.add(new TimeSheetOfDeductionItem(new TimeZoneRounding(duplicationSpan.getStart(), duplicationSpan.getEnd(), null)
																		, duplicationSpan
																		, Collections.emptyList()
																		, Collections.emptyList()
																		, Collections.emptyList()
																		, Collections.emptyList()
																		, Optional.empty()
																		, this.getGoOutReason()
																		, this.getBreakAtr()
																		, this.getDeductionAtr()));
				map.add(this);
				return map;
			}
			else {
				if(setMethod.isFluidWork()||workTimeDailyAtr.isFlex()) {
					if(!useFixedRestTime) {
						if(clockManage.isNotClockManage()) {
							return collectionBreakTime(compareTimeSheet,this);
						}
					}
				}
			}
			map.add(this.replaceTimeSpan(this.calcrange.getNotDuplicationWith(compareTimeSheet.calcrange).get()));
			map.add(compareTimeSheet);
			return map;
		}
		/*休憩系と休憩系*/
		else if(this.getDeductionAtr().isBreak() && compareTimeSheet.getDeductionAtr().isBreak()) {
			/*前半休憩、後半休憩打刻*/
			if(this.getBreakAtr().get().isBreak() && compareTimeSheet.getBreakAtr().get().isBreakStamp()) {
				map.add(this.replaceTimeSpan(this.calcrange.getNotDuplicationWith(compareTimeSheet.calcrange).get()));
				map.add(compareTimeSheet);
				return map;
			}
			/*前半休憩打刻、後半休憩*/
			else if((this.getBreakAtr().get().isBreakStamp() && compareTimeSheet.getBreakAtr().get().isBreak())){
				map.add(this);
				map.add(compareTimeSheet.replaceTimeSpan(compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()));
				return map;
			}
			/*休憩と休憩　→　育児と育児の重複と同じにする(後ろにある時間の開始を前の終了に合わせる)*/
			else if(this.getBreakAtr().get().isBreak() && compareTimeSheet.getBreakAtr().get().isBreak()) {
				map.add(this);
				if(compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).isPresent()) {
					map.add(compareTimeSheet.replaceTimeSpan(compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()));
				}
				else {
					map.add(compareTimeSheet.replaceTimeSpan(new TimeSpanForCalc(this.timeSheet.getEnd(),this.timeSheet.getEnd())));
				}
				
				return map;
			}
		}
		map.add(this);
		map.add(compareTimeSheet);
		return map; 
	}
	
	
	
	/**
	 * 休憩と外出時間帯の重複部分を補正する
	 * @param frontBreakTimeSheet 休憩時間帯
	 * @param goOutTimeSheet　外出時間帯
	 * @return 補正後の休憩、外出時間帯
	 */
	public List<TimeSheetOfDeductionItem> collectionBreakTime(TimeSheetOfDeductionItem frontBreakTimeSheet, TimeSheetOfDeductionItem backGoOutTimeSheet){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		switch(frontBreakTimeSheet.calcrange.checkDuplication(backGoOutTimeSheet.calcrange)) {
		case CONNOTATE_ENDTIME:
		case SAME_SPAN:
			returnList.add(frontBreakTimeSheet.replaceTimeSpan(frontBreakTimeSheet.calcrange.shiftAhead(frontBreakTimeSheet.calcrange.getDuplicatedWith(backGoOutTimeSheet.calcrange).get().lengthAsMinutes())));
			returnList.add(backGoOutTimeSheet);
			return returnList;
		case CONTAINED:
			/*休憩を外出の後ろにずらす*/
			returnList.add(frontBreakTimeSheet.replaceTimeSpan(frontBreakTimeSheet.calcrange.shiftAhead(backGoOutTimeSheet.calcrange.getEnd().valueAsMinutes() - frontBreakTimeSheet.calcrange.getStart().valueAsMinutes())));
			returnList.add(backGoOutTimeSheet);
		case CONTAINS:
		case CONNOTATE_BEGINTIME:
			returnList.add(frontBreakTimeSheet.replaceTimeSpan(new TimeSpanForCalc(frontBreakTimeSheet.start(),backGoOutTimeSheet.start())));
			returnList.add(backGoOutTimeSheet);
			returnList.add(frontBreakTimeSheet.replaceTimeSpan(new TimeSpanForCalc(backGoOutTimeSheet.end(),frontBreakTimeSheet.calcrange.getEnd().backByMinutes(backGoOutTimeSheet.calcrange.lengthAsMinutes()))));
			return returnList;
		case NOT_DUPLICATE:
			returnList.add(frontBreakTimeSheet);
			returnList.add(backGoOutTimeSheet);
			return returnList;
		default:
			throw new RuntimeException("unknown duplicate Atr" + frontBreakTimeSheet.calcrange.checkDuplication(backGoOutTimeSheet.calcrange));
		}
	}
	
	
	public TimeWithDayAttr start() {
		return ((CalculationTimeSheet)this).calcrange.getStart();
		//return this.span.getStart();
	}
	
	public TimeWithDayAttr end() {
		return ((CalculationTimeSheet)this).calcrange.getEnd();
	}
	
	public boolean contains(TimeWithDayAttr baseTime) {
		return ((CalculationTimeSheet)this).calcrange.contains(baseTime);
	}
	
	
	/**
	 * 終了時間と基準時間の早い方の時間を取得する
	 * @param basePoint　基準時間
	 * @return 時刻が早い方
	 */
	public TimeSpanForCalc decisionNewSpan(TimeSpanForCalc timeSpan,TimeWithDayAttr baseTime,boolean isDateBefore) {
		if(isDateBefore) {
			return new TimeSpanForCalc(timeSpan.getStart(),baseTime);
		}
		else {
			return new TimeSpanForCalc(baseTime,timeSpan.getEnd());
		}
	}
	
	/**
	 * 再帰中に自分自身を作り直す処理
	 * @param baseTime
	 * @return
	 */
	public TimeSheetOfDeductionItem reCreateOwn(TimeWithDayAttr baseTime,boolean isDateBefore) {
			List<TimeSheetOfDeductionItem>  recorddeductionTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore,DeductionAtr.Appropriate);
			List<TimeSheetOfDeductionItem>  deductionTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore,DeductionAtr.Deduction);
			List<BonusPayTimeSheetForCalc>         bonusPayTimeSheet = this.recreateBonusPayListBeforeBase(baseTime,isDateBefore);
			List<SpecBonusPayTimeSheetForCalc>specifiedBonusPayTimeSheet = this.recreateSpecifiedBonusPayListBeforeBase(baseTime, isDateBefore);
			Optional<MidNightTimeSheetForCalc>     midNighttimeSheet = this.recreateMidNightTimeSheetBeforeBase(baseTime,isDateBefore);
			TimeSpanForCalc renewSpan = decisionNewSpan(this.calcrange,baseTime,isDateBefore);
			return new TimeSheetOfDeductionItem(this.getTimeSheet(),
												renewSpan,
												recorddeductionTimeSheets,
												deductionTimeSheets,
												bonusPayTimeSheet,
												specifiedBonusPayTimeSheet,
												midNighttimeSheet,this.goOutReason,this.breakAtr,this.deductionAtr);
	}
	
	/**
	 * 法定内区分を法定外にして自分自身を作り直す
	 */
	public TimeSheetOfDeductionItem createWithExcessAtr(){
		return new TimeSheetOfDeductionItem(this.getTimeSheet(),this.calcrange,this.recordedTimeSheet,this.deductionTimeSheet,this.bonusPayTimeSheet,this.specBonusPayTimesheet,this.midNightTimeSheet,this.goOutReason,this.breakAtr,this.deductionAtr);
	}
	
	/**
	 * 休憩時間帯の計算範囲の取得 
	 * @param timeList 出勤退勤の時間リスト
	 * @param calcMethod　休憩時間中に退勤した場合の計算方法
	 * @param deplicateoneTimeRange 1日の範囲と控除時間帯の重複部分
	 * @return
	 */
	public List<TimeSpanForCalc> getBreakCalcRange(List<TimeLeavingWork> timeList,RestTimeOfficeWorkCalcMethod calcMethod,Optional<TimeSpanForCalc> deplicateOneTimeRange) {
		if(!deplicateOneTimeRange.isPresent()) {
			return Collections.emptyList();
		}
		List<TimeSpanForCalc> timesheets = new ArrayList<TimeSpanForCalc>();
		for(TimeLeavingWork time : timeList) {
			Optional<TimeSpanForCalc> timeSpan = getIncludeAttendanceOrLeaveDuplicateTimeSheet(time, calcMethod, deplicateOneTimeRange.get());
			if(timeSpan.isPresent()) {
				timesheets.add(timeSpan.get());
			}
		}
		return timesheets;
	}
	
	/**
	 * 休憩時間帯に出勤、退勤が含まれているかの判定ののち重複時間帯の取得
	 * @param time 出退勤クラス
	 * @param calcMethod　休憩時間中に退勤した場合の計算方法
	 * @param oneDayRange 1日の範囲
	 * @return
	 */
	public Optional<TimeSpanForCalc> getIncludeAttendanceOrLeaveDuplicateTimeSheet(TimeLeavingWork time,RestTimeOfficeWorkCalcMethod calcMethod,TimeSpanForCalc oneDayRange) {
		
		TimeWithDayAttr newStart = oneDayRange.getStart();
		TimeWithDayAttr newEnd = oneDayRange.getEnd();
		
		//退勤時間を含んでいるかチェック
		if(oneDayRange.contains(time.getTimespan().getEnd())) {
			//出勤時間を含んでいるチェック
			if(oneDayRange.contains(time.getTimespan().getStart())){
				newStart = time.getTimespan().getStart();
			}
		
			switch(calcMethod) {
				//計上しない
				case NOT_APPROP_ALL:
					return Optional.empty();
				//全て計上
				case APPROP_ALL:
					return Optional.of(new TimeSpanForCalc(newStart,newEnd));
				//退勤時間まで計上
				case OFFICE_WORK_APPROP_ALL:
					return Optional.of(new TimeSpanForCalc(newStart,time.getTimespan().getEnd()));
				default:
					throw new RuntimeException("unknown CalcMethodIfLeaveWorkDuringBreakTime:" + calcMethod);
			}
		}
		else
		{
			//1日の計算範囲と出退勤の重複範囲取得
			return oneDayRange.getDuplicatedWith(time.getTimespan());
		}
	}
	
	/**
	 * 控除区分と条件が一致しているかを判定
	 * @param atr　条件
	 * @return　一致している。
	 */
	public boolean checkIncludeCalculation(ConditionAtr atr) {
		if(this.deductionAtr.isBreak() && atr.isBreak()) {
			return true;
		}
		else if(this.deductionAtr.isGoOut() && this.goOutReason.isPresent() && this.goOutReason.get().equalReason(atr)) {
			return true;
		}
		else if(this.deductionAtr.isChildCare() ) {//&& false/*育児介護区分追加後作成*/) {
			return false;
		}
		return false;
	}
	

}
