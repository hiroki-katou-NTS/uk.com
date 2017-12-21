package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.enums.StatutoryAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.goout.GoOutManagement;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationCategoryOutsideHours;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;
import nts.uk.ctx.at.shared.dom.workrule.overtime.StatutoryPrioritySet;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.OverWorkSet.StatutoryOverTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.primitive.WorkTimeNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 残業枠時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class OverTimeFrameTimeSheetWork extends CalculationTimeSheet{
	/**残業時間帯NO**/
	private final WorkTimeNo frameNo;
	private final OverTimeFrameTime overWorkFrameTime;
	private final boolean goEarly;
	

	private final StatutoryAtr withinStatutoryAtr;
	
	public OverTimeFrameTimeSheetWork(
			TimeSpanWithRounding timesheet,
			TimeSpanForCalc calculationTimeSheet,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			List<BonusPayTimesheet> bonusPayTimeSheet,
			List<SpecBonusPayTimesheet> specifiedBonusPayTimeSheet,
			Optional<MidNightTimeSheet> midNighttimeSheet
			,WorkTimeNo frameNo
			,OverTimeFrameTime overTimeWorkFrameTime
			,boolean goEarly
			,StatutoryAtr withinStatutoryAtr) {
		super(timesheet,calculationTimeSheet,deductionTimeSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet);
		this.frameNo = frameNo;
		this.overWorkFrameTime = overTimeWorkFrameTime;
		this.goEarly = goEarly;
		this.withinStatutoryAtr = withinStatutoryAtr;
	}
	
	/**
	 * 残業枠分ループし残業枠時間帯の作成
	 * @param overTimeHourSetList 固定勤務の時間帯設定クラス
	 * @param workingSystem 労働制クラス
	 * @param attendanceLeave 出退勤クラス
	 * @param secondStartTime 2回目の勤務の開始時間
	 * @param workNo 現在処理をしている勤務回数
	 * @param breakdownTimeDay
	 * @param dailyTime 法定労働時間
	 * @param autoCalculationSet
	 * @param statutorySet
	 * @param dayEndSet
	 * @param overDayEndSet
	 * @param overTimeWorkItem
	 * @param beforeDay
	 * @param toDay
	 * @param afterDay
	 * @param prioritySet
	 * @return
	 */
	public static List<OverTimeFrameTimeSheetWork> createOverWorkFrame(List<OverTimeHourSet> overTimeHourSetList,WorkingSystem workingSystem,
												TimeLeavingWork attendanceLeave,int workNo,
												BreakdownTimeDay breakdownTimeDay,DailyTime dailyTime,AutoCalculationOfOverTimeWork autoCalculationSet,
												StatutoryOverTimeWorkSet statutorySet,StatutoryPrioritySet prioritySet ) {
		List<OverTimeFrameTimeSheetWork> createTimeSheet = new ArrayList<>();
		
		for(OverTimeHourSet overTimeHourSet:overTimeHourSetList) {
			
			if(overTimeHourSet.getTimeSpan().contains(attendanceLeave.getTimeSpan()));
				createTimeSheet.add(OverTimeFrameTimeSheetWork.createOverWorkFramTimeSheet(overTimeHourSet
																						  ,overTimeHourSet.getTimeSpan().getDuplicatedWith(attendanceLeave.getTimeSpan()).get()));
		}
		/*変形残業　振替*/
		List<OverTimeFrameTimeSheetWork> afterVariableWork = new ArrayList<>();
		afterVariableWork = dicisionCalcVariableWork(workingSystem,createTimeSheet,breakdownTimeDay,dailyTime,autoCalculationSet);
		/*法定内残業　振替*/
		List<OverTimeFrameTimeSheetWork> afterCalcStatutoryOverTimeWork = new ArrayList<>();
		afterCalcStatutoryOverTimeWork = diciaionCalcStatutory(statutorySet ,dailyTime ,OverTimeOfDaily.sortedByPriority(afterVariableWork,prioritySet),autoCalculationSet);
		/*return*/
		return afterCalcStatutoryOverTimeWork;
	}
	
	/**
	 * 変形基準内時間の計算をするか判定
	 * @param workingSystem 労働制
	 */
	public static List<OverTimeFrameTimeSheetWork> dicisionCalcVariableWork(WorkingSystem workingSystem,List<OverTimeFrameTimeSheetWork> overTimeWorkFrameTimeSheetList
															,BreakdownTimeDay breakdownTimeDay,DailyTime dailyTime,AutoCalculationOfOverTimeWork autoCalculationSet) {
		if(workingSystem.isVariableWorkingTimeWork()) {
			/*振替処理  基準内残業時間を計算する*/
			return reclassified(new AttendanceTime(dailyTime.minusMinutes(breakdownTimeDay.getPredetermineWorkTime()).valueAsMinutes()),overTimeWorkFrameTimeSheetList,autoCalculationSet,StatutoryAtr.Statutory);
		}
		return overTimeWorkFrameTimeSheetList;
	}

	
	/**
	 * 法定内残業時間の計算をするか判定
	 * @param statutoryOverWorkSet 法定内残業設定クラス
	 * @param statutorySet
	 * @param dailyTime 法定労働時間
	 * @param overTimeWorkFrameTimeSheetList
	 * @param autoCalculationSet
	 * @param prioritySet
	 * @return
	 */
	public static List<OverTimeFrameTimeSheetWork> diciaionCalcStatutory(StatutoryOverTimeWorkSet statutorySet,DailyTime dailyTime,List<OverTimeFrameTimeSheetWork> overTimeWorkFrameTimeSheetList
																	,AutoCalculationOfOverTimeWork autoCalculationSet) {
		if(statutorySet.isAutoCalcStatutoryOverWork()) {
			/*振替処理   法定内基準時間を計算する*/
			return reclassified(new AttendanceTime(dailyTime.minusMinutes(480).valueAsMinutes()),overTimeWorkFrameTimeSheetList,autoCalculationSet,StatutoryAtr.DeformationCriterion);
		}
		return overTimeWorkFrameTimeSheetList;
	}

	/**
	 * 振替処理
	 * @param ableRangeTime 振替できる時間
	 * @param overTimeWorkFrameTimeSheetList　残業時間枠時間帯クラス
	 * @param autoCalculationSet　時間外の自動計算設定
	 */
	public static List<OverTimeFrameTimeSheetWork> reclassified(AttendanceTime ableRangeTime,List<OverTimeFrameTimeSheetWork> overTimeWorkFrameTimeSheetList,AutoCalculationOfOverTimeWork autoCalculationSet
																,StatutoryAtr statutoryAtr) {
		AttendanceTime overTime = new AttendanceTime(0);
		AttendanceTime transTime = new AttendanceTime(0);
		for(int number = 0; number < overTimeWorkFrameTimeSheetList.size(); number++) {
				
			if(decisionCalcAtr(overTimeWorkFrameTimeSheetList.get(number),autoCalculationSet)) {
				overTime = overTimeWorkFrameTimeSheetList.get(number).calcTotalTime();
			}
				
			else {
				overTime = new AttendanceTime(0);
			}
			if(ableRangeTime.greaterThan(overTime)) {
				transTime = overTime;
			}
			else {
				transTime = ableRangeTime;
			}
			if(transTime.lessThanOrEqualTo(0))
				break;
			//終了時間の判断
			TimeWithDayAttr endTime = reCreateSiteiTimeFromStartTime(transTime,overTimeWorkFrameTimeSheetList.get(number));
			
			/*ここで分割*/
			overTimeWorkFrameTimeSheetList = correctTimeSpan(overTimeWorkFrameTimeSheetList.get(number).splitTimeSpan(endTime,statutoryAtr),overTimeWorkFrameTimeSheetList,number);
			ableRangeTime.minusMinutes(transTime.valueAsMinutes()) ; 
		}
		return overTimeWorkFrameTimeSheetList;
	}
	
	/**
	 * 計算区分の判定処理
	 * @return 打刻から計算する
	 */
	public static boolean decisionCalcAtr(OverTimeFrameTimeSheetWork overTimeWorkFrameTimeSheet,AutoCalculationOfOverTimeWork autoCalculationSet) {
		if(overTimeWorkFrameTimeSheet.getWithinStatutoryAtr().isStatutory()) {
			if(overTimeWorkFrameTimeSheet.isGoEarly()) {
				/*早出残業区分を参照*/
				return autoCalculationSet.getEarlyOvertimeHours().getCalculationClassification().isCalculateEmbossing();
			}
			else {
				/*普通残業計算区分を参照*/
				return autoCalculationSet.getNormalOvertimeHours().getCalculationClassification().isCalculateEmbossing();
			}
		}
		else {
			/*法定内の場合*/
			return autoCalculationSet.getLegalOvertimeHours().getCalculationClassification().isCalculateEmbossing();
		}
	}
	
	/**
	 * 開始から指定時間経過後の終了時刻を取得
	 * @param transTime
	 * @return
	 */
	public static TimeWithDayAttr reCreateSiteiTimeFromStartTime(AttendanceTime transTime,OverTimeFrameTimeSheetWork overTimeWork) {
		return overTimeWork.reCreateTreatAsSiteiTimeEnd(transTime,overTimeWork).getEnd();
	}
	
	/**
	 * 分割後の残業時間枠時間帯を受け取り
	 * @param insertList　補正した時間帯
	 * @param originList　補正する前の時間帯
	 * @return　
	 */
	public static List<OverTimeFrameTimeSheetWork> correctTimeSpan(List<OverTimeFrameTimeSheetWork> insertList,List<OverTimeFrameTimeSheetWork> originList,int nowNumber){
		originList.remove(nowNumber);
		originList.addAll(insertList);
		return originList;
	}
	
	
	/**
	 * 振替処理において残業時間帯を分割するための再作成処理
	 * @param statutoryAtr 	法定内区分
	 * @param newTimeSpan　　  重複している時間帯
	 * @return
	 */
	public OverTimeFrameTimeSheetWork reCreate(StatutoryAtr statutoryAtr, TimeSpanForCalc newTimeSpan) {
		return new OverTimeFrameTimeSheetWork(this.getTimeSheet(),
				  							  newTimeSpan,
											  this.deductionTimeSheet,
											  this.bonusPayTimeSheet,
											  this.specBonusPayTimesheet,
											  this.midNightTimeSheet,
											  this.getFrameNo(),
											  this.overWorkFrameTime,
											  this.goEarly,
											  statutoryAtr);
	}
	
	/**
	 *残業枠時間帯の作成
	 * @param overTimeHourSet
	 * @param timeSpan
	 * @return
	 */
	public static OverTimeFrameTimeSheetWork createOverWorkFramTimeSheet(OverTimeHourSet overTimeHourSet,TimeSpanForCalc timeSpan) {
		
		DeductionTotalTime timeCalc = DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
															 TimeWithCalculation.sameTime(new AttendanceTime(0)),
															 TimeWithCalculation.sameTime(new AttendanceTime(0)));
		BreakTimeManagement breakmanagement = new BreakTimeManagement(BreakTimeOfDaily.sameTotalTime(timeCalc), Collections.emptyList());
		GoOutManagement outmanage = new GoOutManagement();
		
		DeductionTimeSheet deductionTimeSheet = new DeductionTimeSheet(Collections.emptyList(),Collections.emptyList(),breakmanagement, outmanage);//t = /*実働時間の時間帯を跨いだ控除時間帯を分割する*/
	    //List<TimeSheetOfDeductionItem> deductionItem = deductionTimeSheet.getForDeductionTimeZoneList().//getForRecordTimeZoneList();/*法定内区分の置き換え*/
		return new OverTimeFrameTimeSheetWork(overTimeHourSet.getTimeSpan(),
											  timeSpan,
											  deductionTimeSheet.getForDeductionTimeZoneList(),
											  Collections.emptyList(),
											  Collections.emptyList(),
											  Optional.empty(),
											  overTimeHourSet.getFrameNo(),
											  new OverTimeFrameTime(overTimeHourSet.getOverTimeWorkFrameNo(),
													  					TimeWithCalculation.sameTime(new AttendanceTime(0)),
													  					TimeWithCalculation.sameTime(new AttendanceTime(0)),
													  					new AttendanceTime(0)),
											 overTimeHourSet.isTreatAsGoEarlyOverTimeWork(),
											 StatutoryAtr.Statutory);
	}
	

	/**
	 * 残業時間帯時間枠に残業時間を埋める
	 * @param autoCalcSet 時間外の自動計算区分
	 * @return 残業時間枠時間帯クラス
	 */
	public OverTimeFrameTime calcOverTimeWorkTime(AutoCalculationOfOverTimeWork autoCalcSet) {
		int overTimeWorkTime;
		if(getExcessTimeAutoCalcAtr(autoCalcSet).isCalculateEmbossing()) {
			overTimeWorkTime = 0;
		}
		else {
			overTimeWorkTime = this.calcTotalTime().valueAsMinutes();
		}
		return new OverTimeFrameTime(this.overWorkFrameTime.getOverWorkFrameNo()
				,this.overWorkFrameTime.getTransferTime()
				,TimeWithCalculation.sameTime(new AttendanceTime(overTimeWorkTime))
				,this.overWorkFrameTime.getBeforeApplicationTime());
	}
	
	
	/**
	 * 計算区分の判断処理
	 * @param overTimeWorkFrameTime　残業時間枠時間帯クラス
	 * @param autoCalcSet　残業時間の自動計算設定
	 * @return　時間外の自動計算区分
	 */
	public AutoCalculationCategoryOutsideHours getExcessTimeAutoCalcAtr(AutoCalculationOfOverTimeWork autoCalcSet) {
		switch(withinStatutoryAtr) {
			case DeformationCriterion:
			case Excess:
				if(goEarly) {
					return autoCalcSet.getEarlyOvertimeHours().getCalculationClassification();
				}
				else {
					return autoCalcSet.getNormalOvertimeHours().getCalculationClassification();
				}
			case Statutory:
				return autoCalcSet.getLegalOvertimeHours().getCalculationClassification();
			default:
				throw new RuntimeException("unkwon WithinStatutory Atr:" + withinStatutoryAtr);
		
		}
	}
	
	/**
	 * 時間帯の分割
	 * @return
	 */
	public List<OverTimeFrameTimeSheetWork> splitTimeSpan(TimeWithDayAttr baseTime,StatutoryAtr statutoryAtr){
		List<OverTimeFrameTimeSheetWork> returnList = new ArrayList<>();
		if(this.calcrange.getEnd().equals(baseTime)) {
			returnList.add(this);
			return returnList;
		}
		else {
			returnList.add(new OverTimeFrameTimeSheetWork(this.timeSheet
														 ,new TimeSpanForCalc(this.calcrange.getStart(), baseTime)
														 ,this.recreateDeductionItemBeforeBase(baseTime, true)
														 ,this.recreateBonusPayListBeforeBase(baseTime, true)
														 ,this.recreateSpecifiedBonusPayListBeforeBase(baseTime, true)
														 ,this.recreateMidNightTimeSheetBeforeBase(baseTime, true)
														 ,this.frameNo
														 ,this.getOverWorkFrameTime()
														 ,this.goEarly
														 ,statutoryAtr));
			
			returnList.add(new OverTimeFrameTimeSheetWork(this.timeSheet
					 									 ,new TimeSpanForCalc(baseTime, this.calcrange.getEnd())
														 ,this.recreateDeductionItemBeforeBase(baseTime, false)
														 ,this.recreateBonusPayListBeforeBase(baseTime, false)
														 ,this.recreateSpecifiedBonusPayListBeforeBase(baseTime, false)
														 ,this.recreateMidNightTimeSheetBeforeBase(baseTime, false)
					 									 ,this.frameNo
					 									 ,this.getOverWorkFrameTime()
					 									 ,this.goEarly
					 									 ,statutoryAtr));
			return returnList;
		}
	}
	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	
//	//控除時間から残業時間帯を作成
//	public OverTimeWorkFrameTimeSheet createOverTimeWorkFrameTimeSheet(
//			FluidOverTimeWorkSheet fluidOverTimeWorkSheet,
//			DeductionTimeSheet deductionTimeSheet,
//			TimeWithDayAttr startClock,/*残業枠の開始時刻*/
//			AttendanceTime nextElapsedTime/*残業枠ｎ+1．経過時間*/
//			) {
//		//今回のループで処理する経過時間
//		AttendanceTime elapsedTime = fluidOverTimeWorkSheet.getFluidWorkTimeSetting().getElapsedTime();
//		//残業枠の時間を計算する  (残業枠ｎ+1．経過時間　-　残業枠n．経過時間)
//		AttendanceTime overTimeWorkFrameTime = new AttendanceTime(nextElapsedTime.valueAsMinutes()-elapsedTime.valueAsMinutes());
//		//残業枠時間から終了時刻を計算する
//		TimeWithDayAttr endClock = startClock.backByMinutes(overTimeWorkFrameTime.valueAsMinutes());
//		//残業枠時間帯を作成する
//		TimeSpanForCalc overTimeWorkFrameTimeSheet = new TimeSpanForCalc(startClock,endClock);
//		//控除時間帯分ループ
//		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet.getForDeductionTimeZoneList()) {
//			TimeSpanForCalc duplicateTime = overTimeWorkFrameTimeSheet.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet().getSpan()).orElse(null);
//			if(duplicateTime!=null) {
//				//控除項目の時間帯に法定内区分をセット
//				timeSheetOfDeductionItem = timeSheetOfDeductionItem.reateBreakTimeSheetAsFixed(
//						timeSheetOfDeductionItem.getTimeSheet().getSpan(),
//						timeSheetOfDeductionItem.getGoOutReason(),
//						timeSheetOfDeductionItem.getBreakAtr(),
//						timeSheetOfDeductionItem.getDeductionAtr(),
//						WithinStatutoryAtr.WithinStatutory);
//				//控除時間分、終了時刻を遅くする
//				TimeSpanForCalc collectTimeSheet = this.timeSheet.shiftEndBack(duplicateTime.lengthAsMinutes());
//				TimeSpanWithRounding newTimeSheet = this.timeSheet;
//				newTimeSheet.newTimeSpan(collectTimeSheet);
//			}
//		}
//		//控除時間帯に丸め設定を付与
//		
//		//加給時間帯を作成
//		
//		//深夜時間帯を作成
//		
//		
//		OverTimeWorkFrameTimeSheet overTimeWorkFrameTimeSheet = new OverTimeWorkFrameTimeSheet();
//		return overTimeWorkFrameTimeSheet;
//	}

}
