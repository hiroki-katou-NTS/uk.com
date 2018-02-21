package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.overtimework.enums.StatutoryAtr;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.overtime.StatutoryPrioritySet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 残業枠時間帯(WORK)
 * @author keisuke_hoshina
 *
 */
@Getter
public class OverTimeFrameTimeSheetForCalc extends CalculationTimeSheet{
	
	//枠時間
	private OverTimeFrameTime frameTime;
	
	//法定内区分
	private  StatutoryAtr withinStatutryAtr;
	
	//早出区分
	private boolean goEarly;
	
	//残業時間帯No
	private EmTimezoneNo overTimeWorkSheetNo;
	
	//拘束時間として扱う
	private boolean asTreatBindTime;
	
	//精算順序
	//private Optional<精算順序> payOrder;

	//調整時間
	private Optional<AttendanceTime> adjustTime;

	
	
	/**
	 * Constrcotr
	 * @param timeSheet
	 * @param calcrange
	 * @param deductionTimeSheets
	 * @param bonusPayTimeSheet
	 * @param specifiedBonusPayTimeSheet
	 * @param midNighttimeSheet
	 * @param frameTime
	 * @param withinStatutryAtr
	 * @param goEarly
	 * @param overTimeWorkSheetNo
	 * @param asTreatBindTime
	 * @param adjustTime
	 */
	public OverTimeFrameTimeSheetForCalc(TimeZoneRounding timeSheet, TimeSpanForCalc calcrange,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets, List<BonusPayTimesheet> bonusPayTimeSheet,
			List<SpecBonusPayTimesheet> specifiedBonusPayTimeSheet, Optional<MidNightTimeSheet> midNighttimeSheet,
			OverTimeFrameTime frameTime, StatutoryAtr withinStatutryAtr, boolean goEarly, EmTimezoneNo overTimeWorkSheetNo,
			boolean asTreatBindTime, Optional<AttendanceTime> adjustTime) {
		super(timeSheet, calcrange, recorddeductionTimeSheets,deductionTimeSheets, bonusPayTimeSheet, specifiedBonusPayTimeSheet,
				midNighttimeSheet);
		this.frameTime = frameTime;
		this.withinStatutryAtr = withinStatutryAtr;
		this.goEarly = goEarly;
		this.overTimeWorkSheetNo = overTimeWorkSheetNo;
		this.asTreatBindTime = asTreatBindTime;
		this.adjustTime = adjustTime;
	}
	
	/**
	 * 計算用残業枠時間帯から残業枠時間帯へ変換する
	 * @return　残業枠時間帯
	 */
	public OverTimeFrameTimeSheet changeNotWorkFrameTimeSheet() {
		return new OverTimeFrameTimeSheet(this.calcrange, new OverTimeFrameNo(this.overTimeWorkSheetNo.v()));
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
	public static List<OverTimeFrameTimeSheetForCalc> createOverWorkFrame(List<OverTimeOfTimeZoneSet> overTimeHourSetList,WorkingSystem workingSystem,
												TimeLeavingWork attendanceLeave,int workNo,
												BreakDownTimeDay breakdownTimeDay,DailyTime dailyTime,AutoCalculationOfOverTimeWork autoCalculationSet,
												LegalOTSetting statutorySet,StatutoryPrioritySet prioritySet ) {
		List<OverTimeFrameTimeSheetForCalc> createTimeSheet = new ArrayList<>();
		
		for(OverTimeOfTimeZoneSet overTimeHourSet:overTimeHourSetList) {
			
			//if(overTimeHourSet.getTimezone().contains(attendanceLeave.getTimeSpan()));
			//if(attendanceLeave.getTimeZone().isBetweenOrEqual(overTimeHourSet.getTimezone())) {
			Optional<TimeSpanForCalc> calcrange = overTimeHourSet.getTimezone().getDuplicatedWith(attendanceLeave.getTimespan());
			if(calcrange.isPresent()) {
				createTimeSheet.add(OverTimeFrameTimeSheetForCalc.createOverWorkFramTimeSheet(overTimeHourSet,calcrange.get()));
			}
																					  
			//}

		}
//		/*変形残業　振替*/
//		List<OverTimeFrameTimeSheet> afterVariableWork = new ArrayList<>();
//		afterVariableWork = dicisionCalcVariableWork(workingSystem,createTimeSheet,breakdownTimeDay,dailyTime,autoCalculationSet);
//		/*法定内残業　振替*/
//		List<OverTimeFrameTimeSheet> afterCalcStatutoryOverTimeWork = new ArrayList<>();
//		afterCalcStatutoryOverTimeWork = diciaionCalcStatutory(statutorySet ,dailyTime ,OverTimeOfDaily.sortedByPriority(afterVariableWork,prioritySet),autoCalculationSet);
		
		/*return*/
		//return afterCalcStatutoryOverTimeWork;
		return createTimeSheet;
	}
	

	/**
	 * 残業枠時間帯の作成
	 * @param overTimeHourSet 残業時間の時間帯設定
	 * @param timeSpan 計算範囲
	 * @return
	 */
	public static OverTimeFrameTimeSheetForCalc createOverWorkFramTimeSheet(OverTimeOfTimeZoneSet overTimeHourSet,TimeSpanForCalc timeSpan) {
		
		//TODO: get DeductionTimeSheet
		DeductionTimeSheet deductionTimeSheet = new DeductionTimeSheet(Collections.emptyList(), Collections.emptyList());/*実働時間の時間帯を跨いだ控除時間帯を分割する*/;
//		deductionTimeSheet.getForRecordTimeZoneList();/*法定内区分の置き換え*/
//		deductionTimeSheet.getForDeductionTimeZoneList();/*法定内区分の置き換え*/
		
		
		return new OverTimeFrameTimeSheetForCalc(new TimeZoneRounding(timeSpan.getStart(),timeSpan.getEnd(),overTimeHourSet.getTimezone().getRounding()),
											  	timeSpan,
											  	deductionTimeSheet.getForRecordTimeZoneList().stream().map(tc ->tc.createWithExcessAtr()).collect(Collectors.toList()),
											  	deductionTimeSheet.getForDeductionTimeZoneList().stream().map(tc ->tc.createWithExcessAtr()).collect(Collectors.toList()),
											  	Collections.emptyList(),
											  	Collections.emptyList(),
											  	Optional.empty(),
											  	new OverTimeFrameTime(new OverTimeFrameNo(overTimeHourSet.getOtFrameNo().v()),
													  					TimeWithCalculation.sameTime(new AttendanceTime(0)),
													  					TimeWithCalculation.sameTime(new AttendanceTime(0)),
													  					new AttendanceTime(0),
													  					new AttendanceTime(0)),
											  	StatutoryAtr.Statutory,
											  	false,
											  	overTimeHourSet.getWorkTimezoneNo(),
											  	false,
											  	Optional.empty()
											  	);
	}


	
	
	/**
	 * 計算処理
	 * 残業時間の計算
	 * @param forceCalcTime 強制時間区分
	 * @param autoCalcSet 
	 */
	public AttendanceTime correctCalculationTime(Optional<Boolean> forceCalcTime,AutoCalculationOfOverTimeWork autoCalcSet) {
		//区分をみて、計算設定を設定
		//一旦、打刻から計算する場合　を入れとく
		//if(){
		val forceAtr = AutoCalAtrOvertime.CALCULATEMBOSS;
		//}
		//	else {
		//		autoCalcSet.
		//	}
		val calcTime = overTimeCalculationByAdjustTime();
		if(!forceAtr.isApplyOrManuallyEnter()) {
			
		}
		return calcTime;
	}
	
	/**
	 * 調整時間を考慮した時間の計算
	 * @param autoCalcSet 時間外の自動計算区分
	 * @return 残業時間枠時間帯クラス
	 */
	public AttendanceTime overTimeCalculationByAdjustTime() {
		//調整時間を加算
		this.calcrange.getEnd().forwardByMinutes(this.adjustTime.orElse(new AttendanceTime(0)).valueAsMinutes());
		AttendanceTime calcTime = this.calcTotalTime();
		//調整時間を減算(元に戻す)
		this.calcrange.getEnd().backByMinutes(this.adjustTime.orElse(new AttendanceTime(0)).valueAsMinutes());
		return calcTime;
	}
	
	/**
	 *　指定条件の控除項目だけの控除時間
	 * @param forcsList
	 * @param atr
	 * @return
	 */
	public AttendanceTime forcs(List<TimeSheetOfDeductionItem> forcsList,ConditionAtr atr,DeductionAtr dedAtr){
		AttendanceTime dedTotalTime = new AttendanceTime(0);
		val loopList = (dedAtr.isAppropriate())?this.getRecordedTimeSheet():this.deductionTimeSheet;
		for(TimeSheetOfDeductionItem deduTimeSheet: loopList) {
			if(deduTimeSheet.checkIncludeCalculation(atr)) {
				dedTotalTime.addMinutes(deduTimeSheet.calcTotalTime().valueAsMinutes());
			}
		}
		return dedTotalTime;
	}
}
