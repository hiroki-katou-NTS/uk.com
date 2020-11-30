package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BonusPayAutoCalcSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.BonusPayAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.OutsideWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSheetOfDeductionItemList;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.UseTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareCalcRange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareTimezoneResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 残業時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class OverTimeSheet {
	//残業枠時間帯
	private List<OverTimeFrameTimeSheetForCalc> frameTimeSheets;

	
	/**
	 * Constrctor
	 * @param frameTimeSheets
	 */
	public OverTimeSheet(List<OverTimeFrameTimeSheetForCalc> frameTimeSheets) {
		super();
		this.frameTimeSheets = frameTimeSheets;
	}
	
	
	/**
	 * 分割後の残業時間枠時間帯を受け取り
	 * @param insertList　補正した時間帯
	 * @param originList　補正する前の時間帯
	 * @return　
	 */
	public static List<OverTimeFrameTimeSheet> correctTimeSpan(List<OverTimeFrameTimeSheet> insertList,List<OverTimeFrameTimeSheet> originList,int nowNumber){
		originList.remove(nowNumber);
		originList.addAll(insertList);
		return originList;
	}
	
	/**
	 * 残業時間枠時間帯をループさせ時間を計算する
	 * アルゴリズム：ループ処理
	 * @param autoCalcSet 残業時間の自動計算設定
	 * @param workType 勤務種類
	 * @param eachWorkTimeSet 就業時間帯別代休時間設定
	 * @param eachCompanyTimeSet 会社別代休時間設定
	 * @param integrationOfDaily 日別実績(Work)
	 * @param statutoryFrameNoList 法定内残業枠(List)
	 * @param declareResult 申告時間帯作成結果
	 * @param upperControl 事前申請上限制御
	 * @return 残業枠時間(List)
	 */
	public List<OverTimeFrameTime> collectOverTimeWorkTime(
			AutoCalOvertimeSetting autoCalcSet,
			WorkType workType,
			Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet,
			IntegrationOfDaily integrationOfDaily,
			List<OverTimeFrameNo> statutoryFrameNoList,
			DeclareTimezoneResult declareResult,
			boolean upperControl) {
		
		Map<Integer,OverTimeFrameTime> overTimeFrameList = new HashMap<Integer, OverTimeFrameTime>();
		List<OverTimeFrameNo> numberOrder = new ArrayList<>();
		val sortedFrameTimeSheet = sortFrameTime(frameTimeSheets, workType, eachWorkTimeSet, eachCompanyTimeSet);

		//時間帯の計算
		for(OverTimeFrameTimeSheetForCalc overTimeFrameTime : sortedFrameTimeSheet) {
			val forceAtr = autoCalcSet.decisionUseCalcSetting(overTimeFrameTime.getWithinStatutryAtr(), overTimeFrameTime.isGoEarly()).getCalAtr();
			//残業時間　－　控除時間算出
			AttendanceTime calcDedTime = overTimeFrameTime.correctCalculationTime(Optional.empty(), autoCalcSet,DeductionAtr.Deduction).getTime();
			AttendanceTime calcRecTime = overTimeFrameTime.correctCalculationTime(Optional.empty(), autoCalcSet,DeductionAtr.Appropriate).getTime();
			if(!numberOrder.contains(overTimeFrameTime.getFrameTime().getOverWorkFrameNo()))
				numberOrder.add(overTimeFrameTime.getFrameTime().getOverWorkFrameNo());
			//加算だけ
			if(overTimeFrameList.containsKey(overTimeFrameTime.getFrameTime().getOverWorkFrameNo().v())) {
				val frame = overTimeFrameList.get(overTimeFrameTime.getFrameTime().getOverWorkFrameNo().v());
				val addFrame = frame.addOverTime(forceAtr.isCalculateEmbossing()?calcRecTime:new AttendanceTime(0),calcDedTime);
				overTimeFrameList.replace(overTimeFrameTime.getFrameTime().getOverWorkFrameNo().v(), addFrame);
			}
			//枠追加
			else {
				overTimeFrameList.put(overTimeFrameTime.getFrameTime().getOverWorkFrameNo().v(),
									  overTimeFrameTime.getFrameTime().addOverTime(forceAtr.isCalculateEmbossing()?calcRecTime:new AttendanceTime(0),calcDedTime));
			}
		}
		List<OverTimeFrameTime> calcOverTimeWorkTimeList = new ArrayList<>(overTimeFrameList.values()); 
		
		//staticがついていなので、4末緊急対応
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()
			&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily() != null
			&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null
			&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getActualTime() != null
			&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily() != null
			&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
			calcOverTimeWorkTimeList.forEach(ts -> {
				val wantAddTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime()
									.stream()
									.filter(tc -> tc.getOverWorkFrameNo().equals(ts.getOverWorkFrameNo()))
									.findFirst();
				if(wantAddTime.isPresent())
					ts.addBeforeTime(wantAddTime.get().getBeforeApplicationTime());
							
			});
			List<OverTimeFrameTime> reOrderList = new ArrayList<>();
			for(OverTimeFrameNo no : numberOrder){
				val item = calcOverTimeWorkTimeList.stream().filter(tc -> tc.getOverWorkFrameNo().equals(no)).findFirst();
				item.ifPresent(tc -> reOrderList.add(tc));
			}
			calcOverTimeWorkTimeList = reOrderList;
		}
		List<OverTimeFrameTime> afterCalcUpperTimeList = calcOverTimeWorkTimeList;
		if (upperControl){
			//事前申請を上限とする制御
			afterCalcUpperTimeList = afterUpperControl(calcOverTimeWorkTimeList,autoCalcSet,statutoryFrameNoList);
		}
		//振替処理
		List<OverTimeFrameTime> aftertransTimeList = transProcess(workType, afterCalcUpperTimeList, eachWorkTimeSet, eachCompanyTimeSet);
		if (declareResult.getCalcRangeOfOneDay().isPresent()){
			//ループ処理
			CalculationRangeOfOneDay declareCalcRange = declareResult.getCalcRangeOfOneDay().get();
			OutsideWorkTimeSheet declareOutsideWork = declareCalcRange.getOutsideWorkTimeSheet().get();
			if (declareOutsideWork.getOverTimeWorkSheet().isPresent()){
				OverTimeSheet declareSheet = declareOutsideWork.getOverTimeWorkSheet().get();
				List<OverTimeFrameTime> declareFrameTimeList = declareSheet.collectOverTimeWorkTime(
						autoCalcSet,
						workType,
						eachWorkTimeSet,
						eachCompanyTimeSet,
						integrationOfDaily,
						statutoryFrameNoList,
						new DeclareTimezoneResult(),
						false);
				//申告残業反映後リストの取得
				OverTimeSheet.getListAfterReflectDeclare(aftertransTimeList, declareFrameTimeList, declareResult);
			}
		}
		//大塚モードの確認
		if (true){
			//マイナスの乖離時間を0にする
			for (val aftertransTime : aftertransTimeList){
				aftertransTime.getOverTimeWork().divergenceMinusValueToZero();
				aftertransTime.getTransferTime().divergenceMinusValueToZero();
			}
		}
		return aftertransTimeList;
	}
	



	private List<OverTimeFrameTimeSheetForCalc> sortFrameTime(List<OverTimeFrameTimeSheetForCalc> frameTimeSheets, WorkType workType, Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet, Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet) {
		val useSetting = decisionUseSetting(workType, eachWorkTimeSet, eachCompanyTimeSet);
		if(!useSetting.isPresent())
			return frameTimeSheets;
		//指定した時間分振り替える
		//開始時刻のASC
		//&& 普通残業を優先するであれば普通残業、早出残業順になるようにする
		if(useSetting.get().getSubHolTransferSetAtr().isSpecifiedTimeSubHol()) {
			return frameTimeSheets.stream().sorted((first,second) -> first.getTimeSheet().getStart().compareTo(second.getTimeSheet().getStart())).collect(Collectors.toList());
		}
		//一定時間
		//開始時刻のDESC
		else {
			return frameTimeSheets.stream().sorted((first,second) -> second.getTimeSheet().getStart().compareTo(first.getTimeSheet().getStart())).collect(Collectors.toList());
		}
	}


	/**
	 * 事前申請上限制御処理
	 * @param calcOverTimeWorkTimeList 残業時間枠リスト
	 * @param autoCalcSet 残業時間の自動計算設定
	 */
	private List<OverTimeFrameTime> afterUpperControl(List<OverTimeFrameTime> calcOverTimeWorkTimeList,AutoCalOvertimeSetting autoCalcSet,List<OverTimeFrameNo> statutoryFrameNoList) {
		List<OverTimeFrameTime> returnList = new ArrayList<>();
		for(OverTimeFrameTime loopOverTimeFrame:calcOverTimeWorkTimeList) {
			
			TimeLimitUpperLimitSetting autoSet = autoCalcSet.getNormalOtTime().getUpLimitORtSet();
			if(statutoryFrameNoList != null && statutoryFrameNoList.contains(loopOverTimeFrame.getOverWorkFrameNo()))
					autoSet =  autoCalcSet.getLegalOtTime().getUpLimitORtSet();
													 
													
			//時間の上限時間算出
			AttendanceTime upperTime = desictionUseUppserTime(autoSet,loopOverTimeFrame, loopOverTimeFrame.getOverTimeWork().getTime());
			//計算時間の上限算出
//			AttendanceTime upperCalcTime = desictionUseUppserTime(autoSet,loopOverTimeFrame,  loopOverTimeFrame.getOverTimeWork().getCalcTime());
			//振替処理
			loopOverTimeFrame = loopOverTimeFrame.changeOverTime(TimeDivergenceWithCalculation.createTimeWithCalculation(upperTime.greaterThan(loopOverTimeFrame.getOverTimeWork().getTime())?loopOverTimeFrame.getOverTimeWork().getTime():upperTime,
//																														 upperCalcTime.greaterThan(loopOverTimeFrame.getOverTimeWork().getCalcTime())?loopOverTimeFrame.getOverTimeWork().getCalcTime():upperCalcTime)
																														 loopOverTimeFrame.getOverTimeWork().getCalcTime()));
			returnList.add(loopOverTimeFrame);
		}
		return returnList;
	}
	
	
	public AttendanceTime desictionUseUppserTime(TimeLimitUpperLimitSetting autoSet, OverTimeFrameTime loopOverTimeFrame, AttendanceTime attendanceTime) {
		switch(autoSet) {
		//上限なし
		case NOUPPERLIMIT:
			return attendanceTime;
		//指示時間を上限とする
		case INDICATEDYIMEUPPERLIMIT:
			return loopOverTimeFrame.getOrderTime();
		//事前申請を上限とする
		case LIMITNUMBERAPPLICATION:
			return loopOverTimeFrame.getBeforeApplicationTime();
		default:
			throw new RuntimeException("uknown AutoCalcAtr Over Time When Ot After Upper Control");
		}
	}


	/**
	 * 全枠の中に入っている控除時間(控除区分に従って)を合計する
	 * @return 控除合計時間
	 */
	public AttendanceTime calculationAllFrameDeductionTime(DeductionAtr dedAtr,ConditionAtr atr) {
		AttendanceTime totalTime = new AttendanceTime(0);
		for(OverTimeFrameTimeSheetForCalc frameTime : this.frameTimeSheets) {
			totalTime = totalTime.addMinutes(frameTime.forcs(atr,dedAtr).valueAsMinutes());
		}
		return totalTime;
	}
	
	/**
	 * 残業枠時間帯(WORK)を全て残業枠時間帯へ変換する
	 * アルゴリズム：残業枠時間帯の作成
	 * @return 残業枠時間帯List
	 */
	public List<OverTimeFrameTimeSheet> changeOverTimeFrameTimeSheet(){
		return this.frameTimeSheets.stream().map(tc -> tc.changeNotWorkFrameTimeSheet())
											.sorted((first,second) -> first.getFrameNo().v().compareTo(second.getFrameNo().v()))
											.collect(Collectors.toList());
	}
	
	/**
	 * 残業時間帯に入っている加給時間の計算
	 * アルゴリズム：加給時間の計算
	 * @param raisingAutoCalcSet 加給の自動計算設定
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param bonusPayAtr 加給区分
	 * @param calcAtrOfDaily 日別実績の計算区分
	 * @return 加給時間(List)
	 */
	public List<BonusPayTime> calcBonusPayTimeInOverWorkTime(
			AutoCalRaisingSalarySetting raisingAutoCalcSet,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			BonusPayAtr bonusPayAtr,
			CalAttrOfDailyAttd calcAtrOfDaily) {
		
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		ActualWorkTimeSheetAtr sheetAtr;
		for(OverTimeFrameTimeSheetForCalc timeFrame : frameTimeSheets) {
			sheetAtr = ActualWorkTimeSheetAtr.OverTimeWork;
			if(timeFrame.getWithinStatutryAtr().isStatutory()) {
				sheetAtr = ActualWorkTimeSheetAtr.StatutoryOverTimeWork;
				if(timeFrame.isGoEarly()) {
					sheetAtr = ActualWorkTimeSheetAtr.EarlyWork;
				}
			}
			bonusPayList.addAll(timeFrame.calcBonusPay(sheetAtr,raisingAutoCalcSet,bonusPayAutoCalcSet,calcAtrOfDaily,bonusPayAtr));
		}
		return sumBonusPayTime(bonusPayList);
	}
	
	/**
	 * 残業時間帯に入っている特定加給時間の計算
	 * アルゴリズム：加給時間の計算
	 * @param raisingAutoCalcSet 加給の自動計算設定
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param bonusPayAtr 加給区分
	 * @param calcAtrOfDaily 日別実績の計算区分
	 * @return 特定加給時間(List)
	 */
	public List<BonusPayTime> calcSpecBonusPayTimeInOverWorkTime(
			AutoCalRaisingSalarySetting raisingAutoCalcSet,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			BonusPayAtr bonusPayAtr,
			CalAttrOfDailyAttd calcAtrOfDaily) {
		
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		ActualWorkTimeSheetAtr sheetAtr;
		for(OverTimeFrameTimeSheetForCalc timeFrame : frameTimeSheets) {
			sheetAtr = ActualWorkTimeSheetAtr.OverTimeWork;
			if(timeFrame.getWithinStatutryAtr().isStatutory()) {
				sheetAtr = ActualWorkTimeSheetAtr.StatutoryOverTimeWork;
				if(timeFrame.isGoEarly()) {
					sheetAtr = ActualWorkTimeSheetAtr.EarlyWork;
				}
			}
			bonusPayList.addAll(timeFrame.calcSpacifiedBonusPay(sheetAtr,raisingAutoCalcSet,bonusPayAutoCalcSet,calcAtrOfDaily,bonusPayAtr));
		}
		return sumBonusPayTime(bonusPayList);
	}
	
	/**
	 * 同じ加給時間Ｎｏを持つものを１つにまとめる
	 * @param bonusPayTime　加給時間
	 * @return　Noでユニークにした加給時間List
	 */
	private List<BonusPayTime> sumBonusPayTime(List<BonusPayTime> bonusPayTime){
		List<BonusPayTime> returnList = new ArrayList<>();
		List<BonusPayTime> refineList = new ArrayList<>();
		for(int bonusPayNo = 1 ; bonusPayNo <= 10 ; bonusPayNo++) {
			refineList = getByBonusPayNo(bonusPayTime, bonusPayNo);
			if(refineList.size()>0) {
				returnList.add(new BonusPayTime(bonusPayNo,
												new AttendanceTime(refineList.stream().map(tc -> tc.getBonusPayTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))),
												TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(refineList.stream().map(tc -> tc.getWithinBonusPay().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))),
																							  new AttendanceTime(refineList.stream().map(tc -> tc.getWithinBonusPay().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)))),
												TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(refineList.stream().map(tc -> tc.getExcessBonusPayTime().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))),
																							  new AttendanceTime(refineList.stream().map(tc -> tc.getExcessBonusPayTime().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))))
												));
			}
		}
		return returnList;
	}
	
	/**
	 * 受け取った加給時間Ｎｏを持つ加給時間を取得
	 * @param bonusPayTime 加給時間
	 * @param bonusPayNo　加給時間Ｎｏ
	 * @return　加給時間リスト
	 */
	private List<BonusPayTime> getByBonusPayNo(List<BonusPayTime> bonusPayTime,int bonusPayNo){
		return bonusPayTime.stream().filter(tc -> tc.getBonusPayTimeItemNo() == bonusPayNo).collect(Collectors.toList());
	}
	
	/**
	 * 深夜時間計算
	 * @return 計算時間get
	 */
	public AttendanceTime calcMidNightTime(AutoCalOvertimeSetting autoCalcSet) {
		
		AttendanceTime calcTime = new AttendanceTime(0);
		for(OverTimeFrameTimeSheetForCalc timeSheet:frameTimeSheets) {
			val calcSet = getCalcSetByAtr(autoCalcSet, timeSheet.getWithinStatutryAtr(),timeSheet.isGoEarly());
			if(timeSheet.getMidNightTimeSheet().isPresent()) {
				val calcValue = timeSheet.getMidNightTimeSheet().get().calcTotalTime();
				calcTime = calcTime.addMinutes(calcSet.getCalAtr().isCalculateEmbossing()?calcValue.valueAsMinutes():0);
			}
		}
		return calcTime;
	}
	
	/**
	 * 法定内区分、早出区分に従って計算区分の取得
	 * @param autoCalcSet 自動計算設定
	 * @param statutoryAtr　法定内区分
	 * @param goEarly　早出区分
	 * @return　自動計算設定
	 */
	private AutoCalSetting getCalcSetByAtr(AutoCalOvertimeSetting autoCalcSet,StatutoryAtr statutoryAtr, boolean goEarly) {
		//法内である
		if(statutoryAtr.isStatutory() ) {
			return autoCalcSet.getLegalMidOtTime();
		}
		else {
			//早出である
			if(goEarly) {
				return autoCalcSet.getEarlyMidOtTime();
			}
			else {
				return autoCalcSet.getNormalMidOtTime();
			}
		}
	}
	
	/**
	 * 代休の振替処理(残業用)
	 * アルゴリズム：振替処理
	 * @param workType　当日の勤務種類
	 * @param eachWorkTimeSet 就業時間帯別代休時間設定
	 * @param eachCompanyTimeSet 会社別代休時間設定
	 * 
	 */
	public List<OverTimeFrameTime> transProcess(WorkType workType, List<OverTimeFrameTime> afterCalcUpperTimeList,
												Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
												Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet) {
		
		val useSettingAtr = decisionUseSetting(workType, eachWorkTimeSet, eachCompanyTimeSet);
		
		if(!useSettingAtr.isPresent())
			return afterCalcUpperTimeList;
		//代休振替設定判定
		switch(useSettingAtr.get().getSubHolTransferSetAtr()) {
			//一定時間を超えたら代休とする
			case CERTAIN_TIME_EXC_SUB_HOL:
				return periodOfTimeTransfer(useSettingAtr.get().getCertainTime(),afterCalcUpperTimeList);
			//指定した時間を代休とする
			case SPECIFIED_TIME_SUB_HOL:
				return transAllTime(useSettingAtr.get().getDesignatedTime().getOneDayTime(),
								    useSettingAtr.get().getDesignatedTime().getHalfDayTime(),
									afterCalcUpperTimeList);
			default:
				throw new RuntimeException("unknown daikyuSet:");
		}
	}

	/**
	 * 代休の振替処理(残業用)
	 * @param workType　当日の勤務種類
	 * @param eachWorkTimeSet 就業時間帯別代休時間設定
	 * @param eachCompanyTimeSet 会社別代休時間設定
	 * 
	 */
	public Optional<SubHolTransferSet> decisionUseSetting(WorkType workType,
													  Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
													  Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet) {
		//平日ではない
		if(!workType.isWeekDayAttendance()) 
			return Optional.empty();
		val transSet = getTransSet(eachWorkTimeSet,eachCompanyTimeSet);
		//就業時間帯の代休設定取得できない
		if(!transSet.isPresent()||!transSet.get().isUseDivision()) {
			return Optional.empty();
		}
		else {
			//代休振替設定判定
			return transSet;
		}
	}
	
	/**
	 * 代休振替設定の取得
	 * @param eachWorkTimeSet 就業時間帯代休設定
	 * @param eachCompanyTimeSet　会社別代休設定
	 * @return　代休振替設定
	 */
	private Optional<SubHolTransferSet> getTransSet(Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
										  Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet) {
		if(eachWorkTimeSet.isPresent() && eachWorkTimeSet.get().getSubHolTimeSet().isUseDivision()) {
			return Optional.of(eachWorkTimeSet.get().getSubHolTimeSet());
		}
		else {
			if(eachCompanyTimeSet.isPresent())
				return Optional.of(eachCompanyTimeSet.get().getTransferSetting());
		}
		return Optional.empty();
	}


	/**
	 * 一定時間の振替処理
	 * @param 一定時間
	 */
	public List<OverTimeFrameTime> periodOfTimeTransfer(OneDayTime periodTime,List<OverTimeFrameTime> afterCalcUpperTimeList) {
		/*振替可能時間の計算*/
		AttendanceTime transAbleTime = calcTransferTimeOfPeriodTime(new AttendanceTime(periodTime.v()),afterCalcUpperTimeList,UseTimeAtr.TIME);
		AttendanceTime transAbleCalcTime = calcTransferTimeOfPeriodTime(new AttendanceTime(periodTime.v()),afterCalcUpperTimeList,UseTimeAtr.CALCTIME);
		/*振り替える*/
		val afterTransOverTime = trans(transAbleTime ,afterCalcUpperTimeList ,UseTimeAtr.TIME);
		val afterTransOverCalcTime = trans(transAbleCalcTime ,afterTransOverTime ,UseTimeAtr.CALCTIME);
		return afterTransOverCalcTime;
	}
	
	/**
	 * 代休の振替可能時間の計算
	 * @param periodTime 一定時間
	 * @param afterCalcUpperTimeList 残業時間枠リスト
	 * @param useTimeAtr 使用時間区分
	 * @return 振替可能時間
	 */
	private AttendanceTime calcTransferTimeOfPeriodTime(AttendanceTime periodTime,List<OverTimeFrameTime> afterCalcUpperTimeList, UseTimeAtr useTimeAtr) {
		int totalFrameTime =  useTimeAtr.isTime()
								?afterCalcUpperTimeList.stream().map(tc -> tc.getOverTimeWork().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))
								:afterCalcUpperTimeList.stream().map(tc -> tc.getOverTimeWork().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc));
		if(totalFrameTime > periodTime.valueAsMinutes()) {
			return new AttendanceTime(totalFrameTime).minusMinutes(periodTime.valueAsMinutes());
		}
		else {
			return new AttendanceTime(0);
		}
	}

	
	public List<OverTimeFrameTime> trans(AttendanceTime restTransAbleTime, List<OverTimeFrameTime> afterCalcUpperTimeList,UseTimeAtr useTimeAtr) {
		List<OverTimeFrameTime> returnList = new ArrayList<>();
		//振替時間
		AttendanceTime transAbleTime = restTransAbleTime;
		//振替残時間
		AttendanceTime transRestAbleTime = restTransAbleTime;
		for(OverTimeFrameTime overTimeFrameTime : afterCalcUpperTimeList) {

			
			transAbleTime = calcTransferTime(useTimeAtr, overTimeFrameTime, transRestAbleTime);
			//振替
			val overTime = useTimeAtr.isTime()?overTimeFrameTime.getOverTimeWork().getTime().minusMinutes(transAbleTime.valueAsMinutes())
											  :overTimeFrameTime.getOverTimeWork().getCalcTime().minusMinutes(transAbleTime.valueAsMinutes());
			//振替可能時間から減算
			transRestAbleTime = transRestAbleTime.minusMinutes(transAbleTime.valueAsMinutes());
			returnList.add(calcTransTimeInFrame(useTimeAtr, overTimeFrameTime, overTime, transAbleTime));
		}
		
		return returnList;
	}
	
	/**
	 * 振替可能時間算出(振替処理前)
	 * @param useTimeAtr
	 * @param overTimeFrameTime
	 * @param transAbleTime
	 * @param transRestAbleTime
	 * @return
	 */
	private AttendanceTime calcTransferTime(UseTimeAtr useTimeAtr, OverTimeFrameTime overTimeFrameTime, AttendanceTime transRestAbleTime) {
		if(useTimeAtr.isTime()) {
			return overTimeFrameTime.getOverTimeWork().getTime().greaterThanOrEqualTo(transRestAbleTime)
																		  ?transRestAbleTime
																		  :overTimeFrameTime.getOverTimeWork().getTime();
		}
		else {
			return overTimeFrameTime.getOverTimeWork().getCalcTime().greaterThanOrEqualTo(transRestAbleTime)
					  													  ?transRestAbleTime
					  													  :overTimeFrameTime.getOverTimeWork().getCalcTime();
		}
	}
	
	
	/**
	 * 振替残時間(振替後)の算出
	 * @param useTimeAtr 使用する時間区分
	 * @param overTimeFrameTime　残業時間枠
	 * @param overTime　振替処理後の残業時間
	 * @param transAbleTime　振替時間
	 * @return 振替処理後の残業時間枠
	 */
	private OverTimeFrameTime calcTransTimeInFrame(UseTimeAtr useTimeAtr, OverTimeFrameTime overTimeFrameTime,AttendanceTime overTime, AttendanceTime transAbleTime){
		if(useTimeAtr.isTime()) {
			val changeOverTimeFrame = overTimeFrameTime.changeOverTime(TimeDivergenceWithCalculation.createTimeWithCalculation(overTime,
																															   overTimeFrameTime.getOverTimeWork().getCalcTime()));
			return changeOverTimeFrame.changeTransTime(TimeDivergenceWithCalculation.createTimeWithCalculation(transAbleTime, overTimeFrameTime.getTransferTime().getCalcTime()));
		}
		else {
			val changeOverTimeFrame = overTimeFrameTime.changeOverTime(TimeDivergenceWithCalculation.createTimeWithCalculation(overTimeFrameTime.getOverTimeWork().getTime(),
																															   overTime));
			return changeOverTimeFrame.changeTransTime(TimeDivergenceWithCalculation.createTimeWithCalculation(overTimeFrameTime.getTransferTime().getTime(), transAbleTime));
		}
	}
	
	/**
	 * 指定時間の振替処理
	 * @param prioritySet 優先設定
	 */
	public List<OverTimeFrameTime> transAllTime(OneDayTime oneDay,OneDayTime halfDay,List<OverTimeFrameTime> afterCalcUpperTimeList) {
		AttendanceTime transAbleTime = calsTransAllTime(oneDay,halfDay,afterCalcUpperTimeList,UseTimeAtr.TIME);
		AttendanceTime transAbleCalcTime = calsTransAllTime(oneDay,halfDay,afterCalcUpperTimeList,UseTimeAtr.CALCTIME);
		/*振り替える*/
		val afterTransOverTime = trans(transAbleTime ,afterCalcUpperTimeList ,UseTimeAtr.TIME);
		val afterTransOverCalcTime = trans(transAbleCalcTime ,afterTransOverTime ,UseTimeAtr.CALCTIME);
		return afterTransOverCalcTime;
	}
	
	/**
	 * 指定合計時間の計算
	 * @param 指定時間クラス 
	 */
	private AttendanceTime calsTransAllTime(OneDayTime oneDay,OneDayTime halfDay,List<OverTimeFrameTime> afterCalcUpperTimeList,UseTimeAtr useTimeAtr) {
		int totalFrameTime = useTimeAtr.isTime()
										?afterCalcUpperTimeList.stream().map(tc -> tc.getOverTimeWork().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))
										:afterCalcUpperTimeList.stream().map(tc -> tc.getOverTimeWork().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc));
		if(totalFrameTime >= oneDay.valueAsMinutes()) {
			return  new AttendanceTime(oneDay.valueAsMinutes());
		}
		else {
			if(totalFrameTime >= halfDay.valueAsMinutes()) {
				return new AttendanceTime(halfDay.valueAsMinutes());
			}
			else {
				return new AttendanceTime(0);
			}
		}
	}
	/**
	 * 変形法定内残業時間の計算
	 * @return　変形法定内残業時間
	 */
	public AttendanceTime calcIrregularTime() {
		val irregularTimeSheetList = this.frameTimeSheets.stream().filter(tc -> tc.getWithinStatutryAtr().isDeformationCriterion()).collect(Collectors.toList());
		return new AttendanceTime(irregularTimeSheetList.stream().map(tc -> tc.overTimeCalculationByAdjustTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
	}
	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊

//	/**
//	 * 流動勤務（就外、平日）
//	 * @return
//	 */
//	public OverTimeWorkSheet createOverTimeWorkSheet(
//			TimeSpanForDailyCalc calcRange,/*1日の計算範囲の計算範囲*/
//			WithinWorkTimeSheet withinWorkTimeSheet,/*流動勤務（平日・就内）で作成した就業時間内時間帯*/
//			DeductionTimeSheet deductionTimeSheet,
//			FluidWorkTimeSetting fluidWorkTimeSetting
//			) {
//		
//		//計算範囲の取得
//		TimeSpanForDailyCalc timeSpan = new TimeSpanForDailyCalc(
//				withinWorkTimeSheet.getFrameAt(0).getTimeSheet().getEnd(),
//				calcRange.getEnd());
//		
//		//控除時間帯を取得　（保科くんが作ってくれた処理を呼ぶ）
//		
//		//残業枠の開始時刻
//		TimeWithDayAttr startClock = calcRange.getStart();
//		//残業枠設定分ループ
//		for(FluidOverTimeWorkSheet fluidOverTimeWorkSheet: fluidWorkTimeSetting.overTimeWorkSheet) {
//			//残業枠n+1の経過時間を取得
////			AttendanceTime nextElapsedTime = getnextElapsedTime(
////					fluidOverTimeWorkSheet,
////					fluidWorkTimeSetting,
////					new AttendanceTime(calcRange.lengthAsMinutes()));
//			//控除時間から残業時間帯を作成
//			OverTimeWorkFrameTimeSheet overTimeWorkFrameTimeSheet;
//			
//			
//			//次の残業枠の開始時刻に終了時刻を入れる。
//			startClock = overTimeWorkFrameTimeSheet.getTimeSheet().getEnd();
//		}
//		//時間休暇溢れ分の割り当て
//			
//		
//	}
	
	
	/**
	 * 残業枠ｎ+1．経過時間を取得する(流動勤務用)
	 * @param fluidOverTimeWorkSheet
	 * @param fluidWorkTimeSetting
	 * @param timeOfCalcRange
	 * @return
	 */
//	public AttendanceTime getnextElapsedTime(
//			FlowOTTimezone fluidOverTimeWorkSheet,
//			FlowWorkTimezoneSetting fluidWorkTimeSetting,
//			AttendanceTime timeOfCalcRange) {
//		int nextOverWorkTimeNo = fluidOverTimeWorkSheet.getWorktimeNo() + 1;
//		AttendanceTime nextlapsedTime;
//		Optional<FlowOTTimezone> nextFluidOverTimeWorkSheet = 
//				fluidWorkTimeSetting.getMatchWorkNoOverTimeWorkSheet(nextOverWorkTimeNo);
//		if(nextFluidOverTimeWorkSheet==null) {
//			nextlapsedTime = timeOfCalcRange;
//			return nextlapsedTime;
//		}
//		nextlapsedTime = nextFluidOverTimeWorkSheet.get().getFlowTimeSetting().getElapsedTime();
//		return nextlapsedTime;
//	}
	
	
	/**
	 * 流動勤務(平日・就外)
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param timeSheetOfDeductionItems 控除項目の時間帯(List)
	 * @param createdWithinWorkTimeSheet 就業時間内時間帯
	 * @return 残業時間帯
	 */
	public static Optional<OverTimeSheet> createAsFlow(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems,
			WithinWorkTimeSheet createdWithinWorkTimeSheet) {
		
		//計算範囲の取得
		Optional<TimeSpanForDailyCalc> calcRange = getStartEnd(integrationOfDaily, createdWithinWorkTimeSheet);
		
		if(!calcRange.isPresent())	return Optional.empty();
		
		//Listクラスへ変換
		TimeSheetOfDeductionItemList timeSheetOfDeductionItemList = new TimeSheetOfDeductionItemList(timeSheetOfDeductionItems);
		//重複している控除項目の時間帯
		List<TimeSheetOfDeductionItem> overlappingTimeSheets = timeSheetOfDeductionItemList.getOverlappingTimeSheets(calcRange.get());
		
		List<OverTimeFrameTimeSheetForCalc> overTimeFrameTimeSheets = new ArrayList<>();
		
		for(FlowOTTimezone processingFlowOTTimezone : integrationOfWorkTime.getFlowWorkSetting().get().getHalfDayWorkTimezoneLstOTTimezone()) {
			//残業開始時刻
			TimeWithDayAttr overTimeStartTime = calcRange.get().getStart();
			
			//残業時間帯の開始時刻を計算
			if(overTimeFrameTimeSheets.size() != 0) {
				//枠Noの降順の1件目（前回作成した枠を取得する為）
				overTimeStartTime = overTimeFrameTimeSheets.stream()
						.sorted((f,s) -> s.getOverTimeWorkSheetNo().compareTo(f.getOverTimeWorkSheetNo()))
						.map(frame -> frame.getTimeSheet().getEnd())
						.findFirst().get();
			}
			//控除時間から残業時間帯を作成
			overTimeFrameTimeSheets.add(OverTimeFrameTimeSheetForCalc.createAsFlow(
					integrationOfWorkTime.getFlowWorkSetting().get(),
					processingFlowOTTimezone,
					overlappingTimeSheets,
					overTimeStartTime,
					calcRange.get().getEnd(),
					personDailySetting.getBonusPaySetting(),
					integrationOfDaily.getSpecDateAttr(),
					companyCommonSetting.getMidNightTimeSheet()));
		}
		
		//時間休暇溢れ分の割り当て
		List<OverTimeFrameTimeSheetForCalc> afterAllocateVacation = OverTimeSheet.allocateTimeVacationToOverTime(
				integrationOfWorkTime.getFlowWorkSetting().get(),
				personDailySetting.getAddSetting(),
				createdWithinWorkTimeSheet.getTimeVacationAdditionRemainingTime().get(),
				calcRange.get().getStart(),
				integrationOfDaily.getCalAttr().getOvertimeSetting(),
				overTimeFrameTimeSheets);
		
		//変形基準内残業を分割
		List<OverTimeFrameTimeSheetForCalc> afterVariableWork = OverTimeFrameTimeSheetForCalc.dicisionCalcVariableWork(
				companyCommonSetting,
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				predetermineTimeSetForCalc,
				afterAllocateVacation);
		
		//法定内残業分割処理
		List<OverTimeFrameTimeSheetForCalc> afterCalcStatutoryOverTimeWork = OverTimeFrameTimeSheetForCalc.diciaionCalcStatutory(
				companyCommonSetting,
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				predetermineTimeSetForCalc,
				afterVariableWork,
				createdWithinWorkTimeSheet);
		
		return Optional.of(new OverTimeSheet(afterCalcStatutoryOverTimeWork));
	}
	
	/**
	 * 時間休暇溢れ分を割り当て
	 * @param flowWorkSetting 流動勤務設定
	 * @param addSetting 加算設定 
	 * @param timeVacationAdditionRemainingTime 休暇使用合計残時間未割当
	 * @param overTimeStartTime 残業開始時刻
	 * @param autoCalcSet 残業時間の自動計算設定
	 * @param overTimeframeTimeSheets 残業枠時間帯(WORK)
	 */
	private static List<OverTimeFrameTimeSheetForCalc> allocateTimeVacationToOverTime(
			FlowWorkSetting flowWorkSetting,
			AddSetting addSetting,
			AttendanceTime timeVacationAdditionRemainingTime,
			TimeWithDayAttr overTimeStartTime,
			AutoCalOvertimeSetting autoCalcSet,
			List<OverTimeFrameTimeSheetForCalc> overTimeframeTimeSheets) {
		
		//input.休暇使用合計残時間未割当のチェック
		if(timeVacationAdditionRemainingTime.lessThanOrEqualTo(AttendanceTime.ZERO))
			return overTimeframeTimeSheets;
		
		//割増計算方法をチェック
		if(addSetting.getCalculationByActualTimeAtr(PremiumAtr.Premium).isCalclationByActualTime())
			return overTimeframeTimeSheets;
		
		if(!overTimeframeTimeSheets.isEmpty()){
			overTimeframeTimeSheets.sort((f,s) -> s.getOverTimeWorkSheetNo().compareTo(f.getOverTimeWorkSheetNo()));
			//勤務した残業時間帯に割り当てる
			timeVacationAdditionRemainingTime = overTimeframeTimeSheets.get(overTimeframeTimeSheets.size()-1).allocateToOverTimeFrame(
					timeVacationAdditionRemainingTime,
					flowWorkSetting.getHalfDayWorkTimezone().getWorkTimeZone(),
					autoCalcSet);
		}
		
		if(timeVacationAdditionRemainingTime.lessThanOrEqualTo(AttendanceTime.ZERO))
			return overTimeframeTimeSheets;
		
		//勤務した残業時間帯以降に割り当てる
		overTimeframeTimeSheets.addAll(
				OverTimeSheet.createFrameWorkSheetAsNonWorkingHours(
					new EmTimezoneNo(overTimeframeTimeSheets.size()),
					timeVacationAdditionRemainingTime,
					overTimeStartTime,
					flowWorkSetting.getHalfDayWorkTimezone().getWorkTimeZone()));
		
		return overTimeframeTimeSheets;
	}
	
	/**
	 * 以降に割当（勤務していない残業枠時間帯を作成して時間休暇溢れ時間を持たせる）
	 * @param overTimeWorkSheetNo 就業時間帯NO
	 * @param timeVacationAdditionRemainingTime 休暇使用合計残時間未割当
	 * @param overTimeStartTime 残業開始時刻
	 * @param flowWorkTimezoneSetting 流動勤務設定
	 * @return 時間休暇溢れ時間を持たせた残業枠時間帯
	 */
	private static List<OverTimeFrameTimeSheetForCalc> createFrameWorkSheetAsNonWorkingHours(
			EmTimezoneNo overTimeWorkSheetNo,
			AttendanceTime timeVacationAdditionRemainingTime,
			TimeWithDayAttr overTimeStartTime,
			FlowWorkTimezoneSetting flowWorkTimezoneSetting){
		
		//残業枠を作成する
		List<OverTimeFrameTimeSheetForCalc> overTimeFrames = flowWorkTimezoneSetting.getLstOTTimezone().stream()
				.filter(timezone -> overTimeWorkSheetNo.lessThan(new EmTimezoneNo(timezone.getWorktimeNo())))
				.map(timezone -> OverTimeFrameTimeSheetForCalc.createEmpty(
						new TimeSpanForDailyCalc(overTimeStartTime, overTimeStartTime),
						timezone))
				.sorted((f,s) -> f.getOverTimeWorkSheetNo().compareTo(s.getOverTimeWorkSheetNo()))
				.collect(Collectors.toList());
		
		//残業枠へ割り当てる
		for(int i=0; i<overTimeFrames.size(); i++) {
			timeVacationAdditionRemainingTime = overTimeFrames.get(i).allocateOverflowTimeVacation(
					i == overTimeFrames.size()-1,
					timeVacationAdditionRemainingTime,
					flowWorkTimezoneSetting);
			
			if(timeVacationAdditionRemainingTime.lessThanOrEqualTo(AttendanceTime.ZERO)) break;
		}
		return overTimeFrames;
	}
	
	/**
	 * 残業開始終了時刻を取得する
	 * @param integrationOfDaily 日別実績(Work)
	 * @param createdWithinWorkTimeSheet 就業時間内時間帯
	 * @return 残業開始終了時刻
	 */
	private static Optional<TimeSpanForDailyCalc> getStartEnd(IntegrationOfDaily integrationOfDaily, WithinWorkTimeSheet createdWithinWorkTimeSheet) {
		if(!createdWithinWorkTimeSheet.getStartEndToWithinWorkTimeFrame().isPresent())
			return Optional.empty();
		
		if(!integrationOfDaily.getAttendanceLeave().isPresent()
				|| !integrationOfDaily.getAttendanceLeave().get().getLeavingWork().isPresent()
				|| !integrationOfDaily.getAttendanceLeave().get().getLeavingWork().get().getStamp().isPresent())
			return Optional.empty();
		
		//残業開始時刻
		TimeWithDayAttr start = createdWithinWorkTimeSheet.getStartEndToWithinWorkTimeFrame().get().getEnd();
		
		//残業終了時刻
		Optional<TimeWithDayAttr> end = integrationOfDaily.getAttendanceLeave().get().getLeavingWork().get().getStamp().get().getTimeDay().getTimeWithDay();
		if (!end.isPresent()) {
			return Optional.empty();
		}
		if(start.greaterThan(end.get()))
			return Optional.empty();
		
		return Optional.of(new TimeSpanForDailyCalc(start, end.get()));
	}
	
	/**
	 * 申告残業反映後リストの取得
	 * @param recordList 残業枠時間（実績用）
	 * @param declareList 残業枠時間（申告用）
	 * @param declareResult 申告時間帯作成結果
	 */
	private static void getListAfterReflectDeclare(
			List<OverTimeFrameTime> recordList,
			List<OverTimeFrameTime> declareList,
			DeclareTimezoneResult declareResult){

		// 申告Listから反映時間=0:00のデータを削除する
		declareList.removeIf(c ->
		(c.getOverTimeWork().getCalcTime().valueAsMinutes() + c.getTransferTime().getCalcTime().valueAsMinutes() == 0));
		// 残業枠時間を確認する
		for (OverTimeFrameTime record : recordList){
			// 処理中の残業枠NOが申告用Listに存在するか確認
			Optional<OverTimeFrameTime> declare = declareList.stream()
					.filter(c -> c.getOverWorkFrameNo().v() == record.getOverWorkFrameNo().v()).findFirst();
			if (declare.isPresent()){
				// 処理中の残業枠時間に申告用の計算時間を反映
				record.getOverTimeWork().replaceTimeWithCalc(declare.get().getOverTimeWork().getCalcTime());
				record.getTransferTime().replaceTimeWithCalc(declare.get().getTransferTime().getCalcTime());
				// 編集状態．残業に処理中の残業枠NOを追加する
				if (declareResult.getDeclareCalcRange().isPresent()){
					DeclareCalcRange calcRange = declareResult.getDeclareCalcRange().get();
					calcRange.getEditState().getOvertime().add(record.getOverWorkFrameNo());
				}
			}
		}
		// 申告用Listを確認する
		for (OverTimeFrameTime declare : declareList){
			// 処理中の残業枠NOが申告用Listに存在するか確認
			Optional<OverTimeFrameTime> record = recordList.stream()
					.filter(c -> c.getOverWorkFrameNo().v() == declare.getOverWorkFrameNo().v()).findFirst();
			if (!record.isPresent()){
				// 処理中の残業枠時間を反映後Listに追加
				recordList.add(new OverTimeFrameTime(
						declare.getOverWorkFrameNo(),
						TimeDivergenceWithCalculation.createTimeWithCalculation(
								declare.getOverTimeWork().getCalcTime(),
								new AttendanceTime(0)),
						TimeDivergenceWithCalculation.createTimeWithCalculation(
								declare.getTransferTime().getCalcTime(),
								new AttendanceTime(0)),
						new AttendanceTime(0),
						new AttendanceTime(0)));
				// 編集状態．残業に処理中の残業枠NOを追加する
				if (declareResult.getDeclareCalcRange().isPresent()){
					DeclareCalcRange calcRange = declareResult.getDeclareCalcRange().get();
					calcRange.getEditState().getOvertime().add(declare.getOverWorkFrameNo());
				}
			}
		}
	}
}