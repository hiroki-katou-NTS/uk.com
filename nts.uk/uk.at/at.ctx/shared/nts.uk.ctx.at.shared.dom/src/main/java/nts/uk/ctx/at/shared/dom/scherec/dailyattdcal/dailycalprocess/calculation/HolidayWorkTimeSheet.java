package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BonusPayAutoCalcSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.BonusPayAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 休日出勤時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class HolidayWorkTimeSheet{
	//休出枠時間帯
	private List<HolidayWorkFrameTimeSheetForCalc> workHolidayTime;
	

	/**
	* Constructor 
 	*/
	public HolidayWorkTimeSheet(List<HolidayWorkFrameTimeSheetForCalc> workHolidayTime) {
		super();
		this.workHolidayTime = workHolidayTime;
	}
		
	/**
	 * 休出枠時間帯をループさせ時間計算をする
	 * アルゴリズム：ループ処理
	 * @param holidayAutoCalcSetting 自動計算設定
	 * @param workType 勤務種類
	 * @param eachWorkTimeSet 就業時間帯別代休時間設定
	 * @param eachCompanyTimeSet 会社別代休時間設定
	 * @param integrationOfDaily 日別実績(Work)
	 * @return 休出枠時間(List)
	 */
	public List<HolidayWorkFrameTime> collectHolidayWorkTime(
			AutoCalSetting holidayAutoCalcSetting,
			WorkType workType,
			Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet,
			IntegrationOfDaily integrationOfDaily){
		
		Map<Integer,HolidayWorkFrameTime> holidayTimeFrameList = new HashMap<Integer, HolidayWorkFrameTime>();
		//強制区分
		val forceAtr = holidayAutoCalcSetting.getCalAtr();
		//枠時間のソート
		val sortedFrameTimeSheet = sortFrameTime(workHolidayTime, workType, eachWorkTimeSet, eachCompanyTimeSet);
		
		List<HolidayWorkFrameNo> numberOrder = new ArrayList<>();
		
		for(HolidayWorkFrameTimeSheetForCalc holidayWorkFrameTime:sortedFrameTimeSheet) {
			AttendanceTime calcDedTime = holidayWorkFrameTime.correctCalculationTime(holidayAutoCalcSetting,DeductionAtr.Deduction);
			AttendanceTime calcRecTime = holidayWorkFrameTime.correctCalculationTime(holidayAutoCalcSetting,DeductionAtr.Appropriate);
			if(!numberOrder.contains(holidayWorkFrameTime.getFrameTime().getHolidayFrameNo()))
				numberOrder.add(holidayWorkFrameTime.getFrameTime().getHolidayFrameNo());
			//加算だけ
			if(holidayTimeFrameList.containsKey(holidayWorkFrameTime.getFrameTime().getHolidayFrameNo().v())) {
				val frame = holidayTimeFrameList.get(holidayWorkFrameTime.getFrameTime().getHolidayFrameNo().v());
				val addFrame = frame.addHolidayTimeExistReturn(forceAtr.isCalculateEmbossing()?calcRecTime:new AttendanceTime(0),calcDedTime);
				holidayTimeFrameList.replace(holidayWorkFrameTime.getFrameTime().getHolidayFrameNo().v(), addFrame);
			}
			//枠追加
			else {
				holidayTimeFrameList.put(holidayWorkFrameTime.getFrameTime().getHolidayFrameNo().v(),
										holidayWorkFrameTime.getFrameTime().addHolidayTimeExistReturn(forceAtr.isCalculateEmbossing()?calcRecTime:new AttendanceTime(0),calcDedTime));
			}
		}
		//Map→List変換
		List<HolidayWorkFrameTime> calcHolidayTimeWorkTimeList = new ArrayList<>(holidayTimeFrameList.values()); 
		//staticがついていなので、4末緊急対応
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()
			&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily() != null
			&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null
			&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getActualTime() != null
			&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily() != null
			&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()) {
			calcHolidayTimeWorkTimeList.forEach(ts -> {
				val wantAddTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime()
									.stream()
									.filter(tc -> tc.getHolidayFrameNo().equals(ts.getHolidayFrameNo()))
									.findFirst();
				if(wantAddTime.isPresent())
					ts.addBeforeTime(wantAddTime.get().getBeforeApplicationTime().isPresent()?wantAddTime.get().getBeforeApplicationTime().get():new AttendanceTime(0));
			});
			List<HolidayWorkFrameTime> reOrderList = new ArrayList<>();
			for(HolidayWorkFrameNo no : numberOrder){
				val item = calcHolidayTimeWorkTimeList.stream().filter(tc -> tc.getHolidayFrameNo().equals(no)).findFirst();
				item.ifPresent(tc -> reOrderList.add(tc));
			}
			calcHolidayTimeWorkTimeList = reOrderList;
		}
		//staticがついていなので、4末緊急対応	
		//事前申請を上限とする制御
		val afterCalcUpperTimeList = afterUpperControl(calcHolidayTimeWorkTimeList,holidayAutoCalcSetting);
		//振替処理
		val aftertransTimeList = transProcess(workType, afterCalcUpperTimeList, eachWorkTimeSet, eachCompanyTimeSet);
		return aftertransTimeList;
	}
	
	private List<HolidayWorkFrameTimeSheetForCalc> sortFrameTime(List<HolidayWorkFrameTimeSheetForCalc> frameTimeSheets, WorkType workType, Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet, Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet) {
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
	 * 代休の振替処理(残業用)
	 * @param workType　当日の勤務種類
	 * @param eachWorkTimeSet 就業時間帯別代休時間設定
	 * @param eachCompanyTimeSet 会社別代休時間設定
	 * 
	 */
	public static Optional<SubHolTransferSet> decisionUseSetting(WorkType workType,
													  Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
													  Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet) {
		//平日ではない
		if(!workType.getDailyWork().isHolidayWork() || !workType.isGenSubHolidayForHolidayWork()) 
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
	 * 休出枠時間帯(WORK)を全て休出枠時間帯へ変換す
	 * アルゴリズム：休出枠時間帯の作成
	 * @return　休出枠時間帯List
	 */
	public List<HolidayWorkFrameTimeSheet> changeHolidayWorkTimeFrameTimeSheet(){
		return this.workHolidayTime.stream().map(tc -> tc.changeNotWorkFrameTimeSheet())
											.sorted((first,second) -> first.getHolidayWorkTimeSheetNo().v().compareTo(second.getHolidayWorkTimeSheetNo().v()))
											.collect(Collectors.toList());
	}
	
	/**
	 * 全枠の中に入っている控除時間(控除区分に従って)を合計する
	 * @return 控除合計時間
	 */
	public AttendanceTime calculationAllFrameDeductionTime(DeductionAtr dedAtr,ConditionAtr atr) {
		AttendanceTime totalTime = new AttendanceTime(0);
		for(HolidayWorkFrameTimeSheetForCalc frameTime : this.workHolidayTime) {
			totalTime = totalTime.addMinutes(frameTime.forcs(atr,dedAtr).valueAsMinutes());
		}
		return totalTime;
	}
	
	/**
	 * 休出時間帯に入っている加給時間の計算
	 * @param raisingAutoCalcSet 加給の自動計算設定
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param bonusPayAtr 加給区分
	 * @param calcAtrOfDaily 日別実績の計算区分
	 * @return 加給時間(List)
	 */
	public List<BonusPayTime> calcBonusPayTimeInHolidayWorkTime(
			AutoCalRaisingSalarySetting raisingAutoCalcSet,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			BonusPayAtr bonusPayAtr,
			CalAttrOfDailyAttd calcAtrOfDaily) {
		
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(HolidayWorkFrameTimeSheetForCalc timeFrame : workHolidayTime) {
			bonusPayList.addAll(timeFrame.calcBonusPay(ActualWorkTimeSheetAtr.HolidayWork,raisingAutoCalcSet,bonusPayAutoCalcSet,calcAtrOfDaily,bonusPayAtr));
		}
		//同じNo同士はここで加算し、Listのサイズを減らす
		return sumBonusPayTime(bonusPayList);
	}
	
	/**
	 * 休出時間帯に入っている特定加給時間の計算
	 * @param raisingAutoCalcSet 加給の自動計算設定
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param bonusPayAtr 加給区分
	 * @param calcAtrOfDaily 日別実績の計算区分
	 * @return 特定加給時間(List)
	 */
	public List<BonusPayTime> calcSpecBonusPayTimeInHolidayWorkTime(
			AutoCalRaisingSalarySetting raisingAutoCalcSet,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			BonusPayAtr bonusPayAtr,
			CalAttrOfDailyAttd calcAtrOfDaily) {
		
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(HolidayWorkFrameTimeSheetForCalc timeFrame : workHolidayTime) {
			bonusPayList.addAll(timeFrame.calcSpacifiedBonusPay(ActualWorkTimeSheetAtr.HolidayWork,raisingAutoCalcSet,bonusPayAutoCalcSet,calcAtrOfDaily,bonusPayAtr));
		}
		//同じNo同士はここで加算し、Listのサイズを減らす
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
	 * 事前申請上限制御処理
	 * @param calcHolidayTimeWorkTimeList 残業時間枠リスト
	 * @param autoCalcSet 残業時間の自動計算設定
	 * @return 休出枠時間(List)
	 */
	private List<HolidayWorkFrameTime> afterUpperControl(List<HolidayWorkFrameTime> calcHolidayTimeWorkTimeList,AutoCalSetting autoCalcSet) {
		List<HolidayWorkFrameTime> returnList = new ArrayList<>();
		for(HolidayWorkFrameTime loopHolidayTimeFrame:calcHolidayTimeWorkTimeList) {
			//時間の上限時間算出
			AttendanceTime upperTime = desictionUseUppserTime(autoCalcSet, loopHolidayTimeFrame,loopHolidayTimeFrame.getHolidayWorkTime().get().getTime());
			//振替処理
			loopHolidayTimeFrame = loopHolidayTimeFrame.changeOverTime(
					TimeDivergenceWithCalculation.createTimeWithCalculation(
							upperTime.greaterThan(loopHolidayTimeFrame.getHolidayWorkTime().get().getTime())
								?loopHolidayTimeFrame.getHolidayWorkTime().get().getTime()
								:upperTime,
							loopHolidayTimeFrame.getHolidayWorkTime().get().getCalcTime()));
			
			returnList.add(loopHolidayTimeFrame);
		}
		return returnList;
	}
	
	public AttendanceTime desictionUseUppserTime(AutoCalSetting autoCalcSet, HolidayWorkFrameTime loopHolidayTimeFrame,AttendanceTime attendanceTime) {
		switch(autoCalcSet.getUpLimitORtSet()) {
		//上限なし
		case NOUPPERLIMIT:
			return attendanceTime;
		//指示時間を上限とする
		case INDICATEDYIMEUPPERLIMIT:
		//事前申請を上限とする
		case LIMITNUMBERAPPLICATION:
			return loopHolidayTimeFrame.getBeforeApplicationTime().get();
		default:
			throw new RuntimeException("uknown AutoCalcAtr Over Time When Ot After Upper Control");
		}
	}
	
	/**
	 * 代休の振替処理(休出用)
	 * @param workType 当日の勤務種類
	 * @param afterCalcUpperTimeList 休出枠時間(List)
	 * @param eachWorkTimeSet 就業時間帯別代休時間設定
	 * @param eachCompanyTimeSet 会社別代休時間設定
	 * @return 休出枠時間(List)
	 */
	public static List<HolidayWorkFrameTime> transProcess(
			WorkType workType,
			List<HolidayWorkFrameTime> afterCalcUpperTimeList,
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
	 * 代休振替設定の取得
	 * @param eachWorkTimeSet 就業時間帯代休設定
	 * @param eachCompanyTimeSet　会社別代休設定
	 * @return　代休振替設定
	 */
	private static Optional<SubHolTransferSet> getTransSet(Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
										  Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet) {
		//就業時間帯の振替設定参照
		if(eachWorkTimeSet.isPresent() && eachWorkTimeSet.get().getSubHolTimeSet().isUseDivision()) {
			return Optional.of(eachWorkTimeSet.get().getSubHolTimeSet());
		}
		//会社共通の振替設定参照
		else {
			if(eachCompanyTimeSet.isPresent()) {
				if(eachCompanyTimeSet.get().getTransferSetting().isUseDivision()) {
					return Optional.of(eachCompanyTimeSet.get().getTransferSetting());
				}
				else {
					return Optional.empty();
				}
			}
		}
		return Optional.empty();
	}


	/**
	 * 一定時間の振替処理
	 * @param 一定時間
	 */
	public static List<HolidayWorkFrameTime> periodOfTimeTransfer(OneDayTime periodTime,List<HolidayWorkFrameTime> afterCalcUpperTimeList) {
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
	private static AttendanceTime calcTransferTimeOfPeriodTime(AttendanceTime periodTime,List<HolidayWorkFrameTime> afterCalcUpperTimeList, UseTimeAtr useTimeAtr) {
		int totalFrameTime =  useTimeAtr.isTime()
								?afterCalcUpperTimeList.stream().map(tc -> tc.getHolidayWorkTime().get().getTime().v()).collect(Collectors.summingInt(tc -> tc))
								:afterCalcUpperTimeList.stream().map(tc -> tc.getHolidayWorkTime().get().getCalcTime().v()).collect(Collectors.summingInt(tc -> tc));
		if(new AttendanceTime(totalFrameTime).greaterThanOrEqualTo(periodTime)) {
			return new AttendanceTime(totalFrameTime).minusMinutes(periodTime.valueAsMinutes());
		}
		else {
			return new AttendanceTime(0);
		}
	}

	
	public static List<HolidayWorkFrameTime> trans(AttendanceTime restTransAbleTime, List<HolidayWorkFrameTime> afterCalcUpperTimeList,UseTimeAtr useTimeAtr) {
		List<HolidayWorkFrameTime> returnList = new ArrayList<>();
		//振替時間
		AttendanceTime transAbleTime = restTransAbleTime;
		//振替残時間
		AttendanceTime transRestAbleTime = restTransAbleTime;
		for(HolidayWorkFrameTime holidayWorkFrameTime : afterCalcUpperTimeList) {

			transAbleTime = calcTransferTime(useTimeAtr, holidayWorkFrameTime, transRestAbleTime);
			//振替
			val overTime = useTimeAtr.isTime()?holidayWorkFrameTime.getHolidayWorkTime().get().getTime().minusMinutes(transAbleTime.valueAsMinutes())
											  :holidayWorkFrameTime.getHolidayWorkTime().get().getCalcTime().minusMinutes(transAbleTime.valueAsMinutes());
			//振替可能時間から減算
			transRestAbleTime = transRestAbleTime.minusMinutes(transAbleTime.valueAsMinutes());
			returnList.add(calcTransTimeInFrame(useTimeAtr, holidayWorkFrameTime, overTime, transAbleTime));
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
	private static AttendanceTime calcTransferTime(UseTimeAtr useTimeAtr, HolidayWorkFrameTime holidayWorkFrameTime,AttendanceTime transRestAbleTime) {
		if(useTimeAtr.isTime()) {
			return holidayWorkFrameTime.getHolidayWorkTime().get().getTime().greaterThanOrEqualTo(transRestAbleTime)
																		  ?transRestAbleTime
																		  :holidayWorkFrameTime.getHolidayWorkTime().get().getTime();
		}
		else {
			return holidayWorkFrameTime.getHolidayWorkTime().get().getCalcTime().greaterThanOrEqualTo(transRestAbleTime)
					  													  ?transRestAbleTime
					  													  :holidayWorkFrameTime.getHolidayWorkTime().get().getCalcTime();
		}
	}
	
	
	/**
	 * 振替残時間(振替後)の算出
	 * @param useTimeAtr 使用する時間区分
	 * @param overTimeFrameTime　残業時間枠
	 * @param overTime　残業時間
	 * @param transAbleTime　振替時間
	 * @return 振替処理後の残業時間枠
	 */
	private static HolidayWorkFrameTime calcTransTimeInFrame(UseTimeAtr useTimeAtr, HolidayWorkFrameTime holidayWorkTimeFrame,AttendanceTime holidayWorkTime, AttendanceTime transAbleTime){
		if(useTimeAtr.isTime()) {
			val changeOverTimeFrame = holidayWorkTimeFrame.changeOverTime(TimeDivergenceWithCalculation.createTimeWithCalculation(holidayWorkTime , 
																																  holidayWorkTimeFrame.getHolidayWorkTime().get().getCalcTime()));
			return changeOverTimeFrame.changeTransTime(TimeDivergenceWithCalculation.createTimeWithCalculation(transAbleTime,
																											   holidayWorkTimeFrame.getTransferTime().get().getCalcTime()));
		}
		else {
			val changeOverTimeFrame = holidayWorkTimeFrame.changeOverTime(TimeDivergenceWithCalculation.createTimeWithCalculation(holidayWorkTimeFrame.getHolidayWorkTime().get().getTime(),
																																  holidayWorkTime));
			
			return changeOverTimeFrame.changeTransTime(TimeDivergenceWithCalculation.createTimeWithCalculation(holidayWorkTimeFrame.getTransferTime().get().getTime(),
																											   transAbleTime));
		}
	}
	
	/**
	 * 指定時間の振替処理
	 * @param prioritySet 優先設定
	 */
	public static List<HolidayWorkFrameTime> transAllTime(OneDayTime oneDay,OneDayTime halfDay,List<HolidayWorkFrameTime> afterCalcUpperTimeList) {
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
	private static AttendanceTime calsTransAllTime(OneDayTime oneDay,OneDayTime halfDay,List<HolidayWorkFrameTime> afterCalcUpperTimeList,UseTimeAtr useTimeAtr) {
		int totalFrameTime = useTimeAtr.isTime()
										?afterCalcUpperTimeList.stream().map(tc -> tc.getHolidayWorkTime().get().getTime().v()).collect(Collectors.summingInt(tc -> tc))
										:afterCalcUpperTimeList.stream().map(tc -> tc.getHolidayWorkTime().get().getCalcTime().v()).collect(Collectors.summingInt(tc -> tc));
		if(totalFrameTime >= oneDay.valueAsMinutes()) {
			return  new AttendanceTime(oneDay.v());
		}
		else {
			if(totalFrameTime >= halfDay.valueAsMinutes()) {
				return new AttendanceTime(halfDay.v());
			}
			else {
				return new AttendanceTime(0);
			}
		}
	}

	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	
//	/**
//	 * 流動勤務（休日出勤）
//	 */
//	public HolidayWorkTimeSheet createholidayWorkTimeSheet(
//			AttendanceLeavingWorkOfDaily attendanceLeavingWork,
//			List<FluWorkHolidayTimeSheet> workingTimes,
//			DeductionTimeSheet deductionTimeSheet,
//			WorkType workType,
//			HolidayWorkTimeOfDaily holidayWorkTimeOfDaily,
//			TimeSpanForDailyCalc calcRange/*1日の計算範囲*/
//			) {
//		//出退勤時間帯を作成
//		TimeSpanForDailyCalc attendanceLeavingWorkTimeSpan = new TimeSpanForDailyCalc(
//				attendanceLeavingWork.getAttendanceLeavingWork(1).getAttendance().getEngrave().getTimesOfDay(),
//				attendanceLeavingWork.getLeavingWork().getEngrave().getTimesOfDay());
//		//出退勤時間帯と１日の計算範囲の重複部分を計算範囲とする
//		TimeSpanForDailyCalc collectCalcRange = calcRange.getDuplicatedWith(attendanceLeavingWorkTimeSpan).orElse(null);
//		//休出枠時間帯を入れるListを作成
//		List<HolidayWorkFrameTimeSheet> holidayWorkFrameTimeSheetList = new ArrayList<>();
//		//前回のループの経過時間保持用(時間は0：00で作成)
//		AttendanceTime previousElapsedTime = new AttendanceTime(0);
//		//全ての休出枠設定分ループ
//		for(FluWorkHolidayTimeSheet fluWorkHolidayTimeSheet : workingTimes) {
//			//控除時間から休出時間帯を作成
//			HolidayWorkFrameTimeSheet holidayWorkFrameTimeSheet;
//			holidayWorkFrameTimeSheet.collectHolidayWorkFrameTimeSheet(fluWorkHolidayTimeSheet, workType, deductionTimeSheet, collectCalcRange, previousElapsedTime);
//			holidayWorkFrameTimeSheetList.add(holidayWorkFrameTimeSheet);
//			//次のループでの休出枠時間計算用に今回の経過時間を退避
//			previousElapsedTime = fluWorkHolidayTimeSheet.getFluidTimeSetting().getElapsedTime();
//		}
//		HolidayWorkTimeSheet holidayWorkTimeSheet = new HolidayWorkTimeSheet();
//		return  holidayWorkTimeSheet;
//	}
	
	/**
	 * 流動勤務(休日出勤)
	 * @param todayWorkType 当日の勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @param timeSheetOfDeductionItems 控除項目の時間帯(List)
	 * @param holidayStartEnd 休出開始終了時刻（出退勤時間帯）
	 * @param bonuspaySetting 加給設定
	 * @param integrationOfDaily 日別実績(Work)
	 * @param midNightTimeSheet 深夜時間帯
	 * @param oneDayOfRange 1日の計算範囲
	 * @return 休日出勤時間帯
	 */
	public static HolidayWorkTimeSheet createAsFlow(
			WorkType todayWorkType,
			FlowWorkSetting flowWorkSetting,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems,
			TimeSpanForDailyCalc holidayStartEnd,
			Optional<BonusPaySetting> bonuspaySetting,
			IntegrationOfDaily integrationOfDaily,
			MidNightTimeSheet midNightTimeSheet,
			TimeSpanForDailyCalc oneDayOfRange) {
		
		TimeSpanForDailyCalc calcRange = holidayStartEnd.getDuplicatedWith(oneDayOfRange).orElse(holidayStartEnd);
		
		List<HolidayWorkFrameTimeSheetForCalc> holidayWorkFrameTimeSheets = new ArrayList<>();
		
		for(FlowWorkHolidayTimeZone processingTimezone : flowWorkSetting.getOffdayWorkTimezoneLstWorkTimezone()) {
			//休出時間帯の開始時刻を計算
			TimeWithDayAttr holidayStart = calcRange.getStart();
			if(holidayWorkFrameTimeSheets.size() != 0) {
				//枠Noの降順の1件目（前回作成した枠を取得する為）
				holidayStart = holidayWorkFrameTimeSheets.stream()
						.sorted((f,s) -> s.getHolidayWorkTimeSheetNo().compareTo(f.getHolidayWorkTimeSheetNo()))
						.map(frame -> frame.getTimeSheet().getEnd())
						.findFirst().get();
			}
			//控除時間から休出時間帯を作成
			holidayWorkFrameTimeSheets.add(HolidayWorkFrameTimeSheetForCalc.createAsFlow(
					todayWorkType,
					flowWorkSetting,
					timeSheetOfDeductionItems,
					new TimeSpanForDailyCalc(holidayStart, calcRange.getEnd()),
					bonuspaySetting,
					integrationOfDaily.getSpecDateAttr(),
					midNightTimeSheet,
					processingTimezone));
		}
		return new HolidayWorkTimeSheet(holidayWorkFrameTimeSheets);
	}
	
	/**
	 * 流動勤務(休日出勤)
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param timeSheetOfDeductionItems 控除項目の時間帯(List)
	 * @param holidayStartEnd 休出開始終了
	 * @param oneDayOfRange 1日の計算範囲
	 * @return 休日出勤時間帯
	 */
	public static HolidayWorkTimeSheet createAsFlow(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems,
			TimeSpanForDailyCalc holidayStartEnd,
			TimeSpanForDailyCalc oneDayOfRange) {
		
		TimeSpanForDailyCalc calcRange = holidayStartEnd.getDuplicatedWith(oneDayOfRange).orElse(holidayStartEnd);
		
		List<HolidayWorkFrameTimeSheetForCalc> holidayWorkFrameTimeSheets = new ArrayList<>();
		
		for(FlowWorkHolidayTimeZone processingTimezone : integrationOfWorkTime.getFlowWorkSetting().get().getOffdayWorkTimezoneLstWorkTimezone()) {
			//休出時間帯の開始時刻を計算
			TimeWithDayAttr holidayStart = calcRange.getStart();
			if(holidayWorkFrameTimeSheets.size() != 0) {
				//枠Noの降順の1件目（前回作成した枠を取得する為）
				holidayStart = holidayWorkFrameTimeSheets.stream()
						.sorted((f,s) -> s.getHolidayWorkTimeSheetNo().compareTo(f.getHolidayWorkTimeSheetNo()))
						.map(frame -> frame.getTimeSheet().getEnd())
						.findFirst().get();
			}
			//控除時間から休出時間帯を作成
			holidayWorkFrameTimeSheets.add(HolidayWorkFrameTimeSheetForCalc.createAsFlow(
					todayWorkType,
					integrationOfWorkTime.getFlowWorkSetting().get(),
					timeSheetOfDeductionItems,
					new TimeSpanForDailyCalc(holidayStart, calcRange.getEnd()),
					personDailySetting.getBonusPaySetting(),
					integrationOfDaily.getSpecDateAttr(),
					companyCommonSetting.getMidNightTimeSheet(),
					processingTimezone));
		}
		return new HolidayWorkTimeSheet(holidayWorkFrameTimeSheets);
	}
}
