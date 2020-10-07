package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BonusPayAutoCalcSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.GetCalcAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.someitems.BonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.SpecBonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.enums.BonusPayAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 実働時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
@Setter
public abstract class ActualWorkingTimeSheet extends CalculationTimeSheet{

	//時間休暇溢れ時間
	protected Optional<AttendanceTime> timeVacationOverflowTime = Optional.empty();
	//加給
	protected List<BonusPayTimeSheetForCalc> bonusPayTimeSheet = new ArrayList<>();
	//特定日加給
	protected List<SpecBonusPayTimeSheetForCalc> specBonusPayTimesheet = new ArrayList<>();
	//深夜
	protected Optional<MidNightTimeSheetForCalc> midNightTimeSheet = Optional.empty();
	
	public ActualWorkingTimeSheet(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding) {
		super(timeSheet, rounding);
	}
	
	public ActualWorkingTimeSheet(
			TimeSpanForDailyCalc timeSheet,
			TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,
			List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet,
			Optional<MidNightTimeSheetForCalc> midNighttimeSheet) {
		
		super(timeSheet, rounding, recorddeductionTimeSheets,deductionTimeSheets);
		this.bonusPayTimeSheet = bonusPayTimeSheet;
		this.specBonusPayTimesheet = specifiedBonusPayTimeSheet;
		this.midNightTimeSheet = midNighttimeSheet;		
	}
	
	public AttendanceTime calcTotalTime() {
		return super.calcTotalTime().addMinutes(this.timeVacationOverflowTime.orElse(AttendanceTime.ZERO).valueAsMinutes());
	}
	
	/**
	 * 加給時間帯のリストを作り直す
	 * @param baseTime 基準時間
	 * @param isDateBefore 基準時間より早い時間を切り出す
	 * @return 切り出した加給時間帯
	 */
	public List<BonusPayTimeSheetForCalc> recreateBonusPayListBeforeBase(TimeWithDayAttr baseTime,boolean isDateBefore){
		List<BonusPayTimeSheetForCalc> bonusPayList = new ArrayList<>();
	//	for(BonusPayTimeSheetForCalc bonusPay : bonusPayList) {
//			if(bonusPay..contains(baseTime)) {
//				bonusPayList.add(bonusPay.reCreateOwn(baseTime,isDateBefore));
//			}
//			else if(bonusPay.calcrange.getEnd().lessThan(baseTime) && isDateBefore) {
//				bonusPayList.add(bonusPay);
//			}
//			else if(bonusPay.calcrange.getStart().greaterThan(baseTime) && !isDateBefore) {
//				bonusPayList.add(bonusPay);
//			}
		//}
		return bonusPayList; 
	}
	
	/**
	 * 特定日加給時間帯のリストを作り直す
	 * @param baseTime 基準時間
	 * @param isDateBefore 基準時間より早い時間を切り出す
	 * @return 切り出した加給時間帯
	 */
	public List<SpecBonusPayTimeSheetForCalc> recreateSpecifiedBonusPayListBeforeBase(TimeWithDayAttr baseTime,boolean isDateBefore){
		List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayList = new ArrayList<>();
//		for(SpecBonusPayTimeSheetForCalc specifiedBonusPay : specifiedBonusPayList) {
//			if(specifiedBonusPay.contains(baseTime)) {
//				specifiedBonusPayList.add(specifiedBonusPay.reCreateOwn(baseTime,isDateBefore));
//			}
//			else if(specifiedBonusPay.calcrange.getEnd().lessThan(baseTime) && isDateBefore) {
//				specifiedBonusPayList.add(specifiedBonusPay);
//			}
//			else if(specifiedBonusPay.calcrange.getStart().greaterThan(baseTime) && !isDateBefore) {
//				specifiedBonusPayList.add(specifiedBonusPay);
//			}
//		}
		return specifiedBonusPayList; 
	}
	
	/**
	 * 深夜時間帯のリストを作り直す
	 * @param baseTime 基準時間
	 * @param isDateBefore 基準時間より早い時間を切り出す
	 * @return 切り出した深夜時間帯
	 */
	public Optional<MidNightTimeSheetForCalc> recreateMidNightTimeSheetBeforeBase(TimeWithDayAttr baseTime,boolean isDateBefore){
		if(this.midNightTimeSheet.isPresent()) {
			if(midNightTimeSheet.get().getTimeSheet().getTimeSpan().contains(baseTime)) {
				return midNightTimeSheet.get().reCreateOwn(baseTime,isDateBefore);
				//return midNightTimeSheet;
			}
			else if(midNightTimeSheet.get().getTimeSheet().getTimeSpan().getEnd().lessThan(baseTime) && isDateBefore) {
				return midNightTimeSheet;
			}
			else if(midNightTimeSheet.get().getTimeSheet().getTimeSpan().getStart().greaterThan(baseTime) && !isDateBefore) {
				return midNightTimeSheet;
			}
		}
		return Optional.empty();
	}
	
	/**
	 * 深夜時間帯の作成(再帰)
	 * @param timeSpan
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> duplicateTimeSpan(TimeSpanForDailyCalc timeSpan) {
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		for(TimeSheetOfDeductionItem deductionTimeSheet : deductionTimeSheet) {
			if(midNightTimeSheet.isPresent()) {
				Optional<TimeSpanForDailyCalc> duplicateSpan = midNightTimeSheet.get().getTimeSheet().getDuplicatedWith(deductionTimeSheet.getTimeSheet());
				if(duplicateSpan.isPresent()) {
					returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
																								deductionTimeSheet.getTimeSheet()
																							   ,deductionTimeSheet.getRounding()
																							   ,deductionTimeSheet.getRecordedTimeSheet()
																							   ,deductionTimeSheet.getDeductionTimeSheet()
																							   ,deductionTimeSheet.getWorkingBreakAtr()
																							   ,deductionTimeSheet.getGoOutReason()
																							   ,deductionTimeSheet.getBreakAtr()
																							   ,deductionTimeSheet.getShortTimeSheetAtr()
																							   ,deductionTimeSheet.getDeductionAtr()
																							   ,deductionTimeSheet.getChildCareAtr()));
				}
			}
		}
		return returnList;
	}
	
	/**
	 * 加給時間の計算
	 * アルゴリズム：時間の計算
	 * @param actualWorkAtr 実働区分
	 * @param raisingAutoCalcSet 加給の自動計算設定
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param calcAtrOfDaily 日別実績の計算区分
	 * @return 加給時間(List)
	 */
	public List<BonusPayTime> calcBonusPay(
			ActualWorkTimeSheetAtr actualWorkAtr,
			AutoCalRaisingSalarySetting raisingAutoCalcSet,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			CalAttrOfDailyAttd calcAtrOfDaily,
			BonusPayAtr bonusPayAtr) {
		
		List<BonusPayTime> bonusPayTimeList = new ArrayList<>();
		for(BonusPayTimeSheetForCalc bonusPaySheet : this.bonusPayTimeSheet){
			//加給時間の計算
			AttendanceTime calcTime = raisingAutoCalcSet.isRaisingSalaryCalcAtr()?bonusPaySheet.calcTotalTime(): new AttendanceTime(0);
			AttendanceTime time = raisingAutoCalcSet.isRaisingSalaryCalcAtr()?bonusPaySheet.calcTotalTime(): new AttendanceTime(0);
			
			bonusPayTimeList.add(new BonusPayTime(
					bonusPaySheet.getRaiseSalaryTimeItemNo().v().intValue(),
					calcTime,
					TimeWithCalculation.createTimeWithCalculation(actualWorkAtr.isWithinWorkTime()?time:new AttendanceTime(0),actualWorkAtr.isWithinWorkTime()?calcTime:new AttendanceTime(0)),
					TimeWithCalculation.createTimeWithCalculation(actualWorkAtr.isWithinWorkTime()?new AttendanceTime(0):time,actualWorkAtr.isWithinWorkTime()?new AttendanceTime(0):calcTime)));
		}
		//加給時間帯計算設定の取得
		if(!GetCalcAtr.isCalc(calcAtrOfDaily.getRasingSalarySetting().isRaisingSalaryCalcAtr(), calcAtrOfDaily, bonusPayAutoCalcSet, actualWorkAtr)) {
			bonusPayTimeList.forEach(tc ->{tc.getWithinBonusPay().setTime(new AttendanceTime(0));
										tc.getExcessBonusPayTime().setTime(new AttendanceTime(0));});
		}
		return bonusPayTimeList;
	}
	
	/**
	 * 特定加給時間の計算
	 * アルゴリズム：時間の計算
	 * @param actualWorkAtr 実働区分
	 * @param raisingAutoCalcSet 加給の自動計算設定
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param calcAtrOfDaily 日別実績の計算区分
	 * @return 特定加給時間クラス(List)
	 */
	public List<BonusPayTime> calcSpacifiedBonusPay(
			ActualWorkTimeSheetAtr actualWorkAtr,
			AutoCalRaisingSalarySetting raisingAutoCalcSet,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			CalAttrOfDailyAttd calcAtrOfDaily,
			BonusPayAtr bonusPayAtr){
		
		List<BonusPayTime> bonusPayTimeList = new ArrayList<>();
		for(SpecBonusPayTimeSheetForCalc bonusPaySheet : this.specBonusPayTimesheet){
			AttendanceTime calcTime = raisingAutoCalcSet.isSpecificRaisingSalaryCalcAtr()?bonusPaySheet.calcTotalTime():new AttendanceTime(0);
			AttendanceTime time = raisingAutoCalcSet.isSpecificRaisingSalaryCalcAtr()?bonusPaySheet.calcTotalTime():new AttendanceTime(0);
			bonusPayTimeList.add(
					new BonusPayTime(bonusPaySheet.getSpecBonusPayNumber().v().intValue(),
					calcTime,
					TimeWithCalculation.createTimeWithCalculation(actualWorkAtr.isWithinWorkTime()?time:new AttendanceTime(0),actualWorkAtr.isWithinWorkTime()?calcTime:new AttendanceTime(0)),
					TimeWithCalculation.createTimeWithCalculation(actualWorkAtr.isWithinWorkTime()?new AttendanceTime(0):time,actualWorkAtr.isWithinWorkTime()?new AttendanceTime(0):calcTime)));
		}
		if(!GetCalcAtr.isCalc(calcAtrOfDaily.getRasingSalarySetting().isSpecificRaisingSalaryCalcAtr(), calcAtrOfDaily, bonusPayAutoCalcSet, actualWorkAtr)) {
			bonusPayTimeList.forEach(tc ->{tc.getWithinBonusPay().setTime(new AttendanceTime(0));
										tc.getExcessBonusPayTime().setTime(new AttendanceTime(0));});
		}
		return bonusPayTimeList;
	}
	
	//---------------------------実働時間帯へ持っていきたい-------------------------------↓
	/**
	 * 指定された時間帯と重複している加給時間帯を取得
	 * @param bonusPayTimeSheet
	 * @param duplicateTimeSheet
	 * @return
	 */
	public static List<BonusPayTimeSheetForCalc> getDuplicatedBonusPay(List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,TimeSpanForDailyCalc timeSpan){
		return bonusPayTimeSheet.stream()
								.filter(tc -> tc.getTimeSheet().getDuplicatedWith(timeSpan).isPresent())
								.map(tc -> tc.convertForCalcCorrectRange(tc.getTimeSheet().getDuplicatedWith(timeSpan).get()))
								.collect(Collectors.toList());
	}
	/**
	 * 指定された時間帯と重複している加給時間帯を取得
	 * @param bonusPayTimeSheet
	 * @param duplicateTimeSheet
	 * @return
	 */
	public List<BonusPayTimeSheetForCalc> getDuplicatedBonusPayNotStatic(List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,TimeSpanForDailyCalc timeSpan){
		return bonusPayTimeSheet.stream()
								.filter(tc -> tc.getTimeSheet().getTimeSpan().checkDuplication(timeSpan.getTimeSpan()).isDuplicated())
								.map(tc -> tc.convertForCalcCorrectRange(tc.getTimeSheet().getDuplicatedWith(timeSpan).get()))
								.collect(Collectors.toList());
	}
	
	/**
	 * 指定された時間帯と重複している特定加給時間帯を取得
	 * @param bonusPayTimeSheet
	 * @param duplicateTimeSheet
	 * @return
	 */
	public static List<SpecBonusPayTimeSheetForCalc> getDuplicatedSpecBonusPay(List<SpecBonusPayTimeSheetForCalc> bonusPayTimeSheet,TimeSpanForDailyCalc timeSpan){
		return bonusPayTimeSheet.stream()
								.filter(tc -> tc.getTimeSheet().getTimeSpan().checkDuplication(timeSpan.getTimeSpan()).isDuplicated())
								.map(tc -> tc.convertForCalcCorrectRange(tc.getTimeSheet().getDuplicatedWith(timeSpan).get()))
								.collect(Collectors.toList());
	}
	
	/**
	 * 指定された時間帯と重複している特定加給時間帯を取得
	 * @param bonusPayTimeSheet
	 * @param duplicateTimeSheet
	 * @return
	 */
	public List<SpecBonusPayTimeSheetForCalc> getDuplicatedSpecBonusPayzNotStatic(List<SpecBonusPayTimeSheetForCalc> bonusPayTimeSheet,TimeSpanForDailyCalc timeSpan){
		return bonusPayTimeSheet.stream()
								.filter(tc -> tc.getTimeSheet().getTimeSpan().checkDuplication(timeSpan.getTimeSpan()).isDuplicated())
								.map(tc -> tc.convertForCalcCorrectRange(tc.getTimeSheet().getDuplicatedWith(timeSpan).get()))
								.collect(Collectors.toList());
	}
	
	/**
	 * 指定された時間帯と重複している深夜時間帯を取得
	 * @param midNightTimeSheet
	 * @param duplicateTimeSheet
	 * @return
	 */
	public static Optional<MidNightTimeSheetForCalc> getDuplicateMidNight(MidNightTimeSheetForCalc midNightTimeSheet, TimeSpanForDailyCalc timeSpan){ 
		val duplicateMidNightSpan = timeSpan.getDuplicatedWith(midNightTimeSheet.getTimeSheet());
		if(duplicateMidNightSpan.isPresent()) {
			return Optional.of(midNightTimeSheet.replaceTime(duplicateMidNightSpan.get().getTimeSpan()));
		}
		return Optional.empty();
	}
	
	/**
	 * 指定された時間帯と重複している深夜時間帯を取得
	 * @param midNightTimeSheet
	 * @param duplicateTimeSheet
	 * @return
	 */
	public Optional<MidNightTimeSheetForCalc> getDuplicateMidNightNotStatic(MidNightTimeSheetForCalc midNightTimeSheet, TimeSpanForDailyCalc timeSpan){ 
		val duplicateMidNightSpan = timeSpan.getDuplicatedWith(midNightTimeSheet.getTimeSheet());
		if(duplicateMidNightSpan.isPresent()) {
			return Optional.of(midNightTimeSheet.replaceTime(duplicateMidNightSpan.get().getTimeSpan()));
		}
		return Optional.empty();
	}
	
	/*実働時間帯へジェネリクスに変えて飛ばしたい*/
	//重複している控除を入れたい
	public static List<BonusPayTimeSheetForCalc> bonusPay(List<BonusPayTimeSheetForCalc> calcTimeSheetList,List<TimeSheetOfDeductionItem> dedSheetList,List<TimeSheetOfDeductionItem> recordSheetList){
		calcTimeSheetList.forEach(tc -> tc.addDuplicatedDeductionTimeSheet(dedSheetList, DeductionAtr.Deduction,Optional.empty()));
		calcTimeSheetList.forEach(tc -> tc.addDuplicatedDeductionTimeSheet(recordSheetList, DeductionAtr.Appropriate,Optional.empty()));
		return calcTimeSheetList;
	}
	/*実働時間帯へジェネリクスに変えて飛ばしたい*/
	//重複している控除を入れたい
	public static List<SpecBonusPayTimeSheetForCalc> specBonusPay(List<SpecBonusPayTimeSheetForCalc> calcTimeSheetList,List<TimeSheetOfDeductionItem> dedSheetList,List<TimeSheetOfDeductionItem> recordSheetList){
		calcTimeSheetList.forEach(tc -> tc.addDuplicatedDeductionTimeSheet(dedSheetList, DeductionAtr.Deduction,Optional.empty()));
		calcTimeSheetList.forEach(tc -> tc.addDuplicatedDeductionTimeSheet(recordSheetList, DeductionAtr.Appropriate,Optional.empty()));
		return calcTimeSheetList;
	}
	
	/**
	 * 加給時間帯と重複している控除項目時間帯を加給時間帯へ保持させる 
	 * (実働時間帯へ持っていきたい)
	 */
	public static List<BonusPayTimeSheetForCalc> getBonusPayTimeSheetIncludeDedTimeSheet(Optional<BonusPaySetting> bonuspaySetting,TimeSpanForDailyCalc duplicateTimeSheet,
															   							  List<TimeSheetOfDeductionItem> dedTimeSheet,
															   							  List<TimeSheetOfDeductionItem> recordTimeSheet){
		List<BonusPayTimeSheetForCalc> duplicatedBonusPay = new ArrayList<>();
		if(bonuspaySetting.isPresent()) {
			val bpTimeSheet = bonuspaySetting.get().getLstBonusPayTimesheet().stream()
			   											  					 .filter(tc -> tc.getUseAtr().isUse())
			   											  					 .map(tc ->BonusPayTimeSheetForCalc.convertForCalc(tc))
			   											  					 .collect(Collectors.toList());
			duplicatedBonusPay = getDuplicatedBonusPay(bpTimeSheet,
					  								   duplicateTimeSheet);
		}
		return bonusPay(duplicatedBonusPay,dedTimeSheet,recordTimeSheet);
	}

	/**
	 * 特定加給時間帯と重複している控除項目時間帯を加給時間帯へ保持させる 
	 * (実働時間帯へ持っていきたい)
	 */
	public static List<SpecBonusPayTimeSheetForCalc> getSpecBonusPayTimeSheetIncludeDedTimeSheet(Optional<BonusPaySetting> bonuspaySetting,TimeSpanForDailyCalc duplicateTimeSheet,
															   		   						  List<TimeSheetOfDeductionItem> dedTimeSheet,
															   		   						  List<TimeSheetOfDeductionItem> recordTimeSheet,
															   		   						  Optional<SpecificDateAttrOfDailyAttd> specificDateAttrSheets){
		List<SpecBonusPayTimeSheetForCalc> duplicatedSpecBonusPay = new ArrayList<>();
		if(bonuspaySetting.isPresent()) {
			val specBpTimeSheet = bonuspaySetting.get().getLstSpecBonusPayTimesheet().stream()
			   												   					 .filter(tc -> tc.getUseAtr().isUse())
			   												   					 .collect(Collectors.toList());
			if(specificDateAttrSheets.isPresent()) {
				val useSpecTimeSheet = getUseSpecTimeSheet(specBpTimeSheet,specificDateAttrSheets.get().getUseNo());
				duplicatedSpecBonusPay = getDuplicatedSpecBonusPay(useSpecTimeSheet.stream().map(tc ->SpecBonusPayTimeSheetForCalc.convertForCalc(tc)).collect(Collectors.toList()),
						   											duplicateTimeSheet);
			}
		}
		return specBonusPay(duplicatedSpecBonusPay,dedTimeSheet,recordTimeSheet);
	}
	
	/**
	 * 日別実績の特定日区分を基に特定日として利用するか判定する
	 * @param specBpTimeSheets　特定日時間帯
	 * @param specNoList　特定日NOリスト
	 * @return 使用する特定日時間帯
	 */
	private static List<SpecBonusPayTimesheet> getUseSpecTimeSheet(List<SpecBonusPayTimesheet> specBpTimeSheets,List<SpecificDateItemNo> specNoList){
		List<SpecBonusPayTimesheet> returnList = new ArrayList<>();
		for(SpecBonusPayTimesheet specTimeSheet:specBpTimeSheets) {
			if(specNoList.contains(new SpecificDateItemNo(specTimeSheet.getDateCode())))
				returnList.add(specTimeSheet);
		}
		return returnList;
	}
	
	/**
	 * @param midNightTimeSheet 深夜時間帯
	 * @param commonSetting 就業時間帯の共通設定
	 */
	public void createMidNightTimeSheet(MidNightTimeSheet midNightTimeSheet, Optional<WorkTimezoneCommonSet> commonSetting) {
		this.midNightTimeSheet = ActualWorkingTimeSheet.getMidNightTimeSheetIncludeDedTimeSheet(
				midNightTimeSheet,
				this.timeSheet,
				this.deductionTimeSheet,
				this.recordedTimeSheet,
				commonSetting);
	}
	
	/**
	 * 深夜時間帯と重複している控除項目時間帯を深夜時間帯へ保持させる 
	 * (実働時間帯へ持っていきたい)
	 */
	@SuppressWarnings("static-access")
	public static Optional<MidNightTimeSheetForCalc> getMidNightTimeSheetIncludeDedTimeSheet(MidNightTimeSheet midNightTimeSheet,TimeSpanForDailyCalc duplicateTimeSheet,
															   List<TimeSheetOfDeductionItem> dedTimeSheet,
															   List<TimeSheetOfDeductionItem> recordTimeSheet,Optional<WorkTimezoneCommonSet> commonSetting){
		val midNightTimeForCalc = MidNightTimeSheetForCalc.convertForCalc(midNightTimeSheet,commonSetting);
		val duplicatedMidNight = getDuplicateMidNight(midNightTimeForCalc,duplicateTimeSheet);
		if(duplicatedMidNight.isPresent()) {
			duplicatedMidNight.get().addDuplicatedDeductionTimeSheet(dedTimeSheet, DeductionAtr.Deduction,Optional.of(new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
			duplicatedMidNight.get().addDuplicatedDeductionTimeSheet(recordTimeSheet, DeductionAtr.Appropriate,Optional.of(new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
		}
		return duplicatedMidNight;
	}
	
	/**
	 * 時間帯の作成（加給、特定加給時間帯の作成）
	 * @param bonuspaySetting 加給設定
	 * @param specificDateAttrSheets 日別実績の特定日区分
	 */
	public void createBonusPayTimeSheet(Optional<BonusPaySetting> bonuspaySetting, Optional<SpecificDateAttrOfDailyAttd> specificDateAttrSheets){
		
		ActualWorkingTimeSheet.getBonusPayTimeSheetIncludeDedTimeSheet(bonuspaySetting, this.timeSheet, this.deductionTimeSheet, this.recordedTimeSheet);
		ActualWorkingTimeSheet.getSpecBonusPayTimeSheetIncludeDedTimeSheet(bonuspaySetting, this.timeSheet, this.deductionTimeSheet, this.recordedTimeSheet, specificDateAttrSheets);
	}
}
