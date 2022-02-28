package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BPTimeItemSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryFrameTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSheetRoundingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.service.ActualWorkTimeSheetListService;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordTimeCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneExtraordTimeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 臨時時間帯
 * @author shuichi_ishida
 */
@Getter
public class TemporaryTimeSheet {

	/** 臨時枠時間帯 */
	private List<TemporaryFrameTimeSheet> frameTimeSheets;
	/** 臨時回数 */
	private TemporaryTimes temporaryTimes;
	
	/**
	 * Constractor
	 * @param frameTimeSheets 臨時枠時間帯List
	 * @param temporaryTimes 臨時回数
	 */
	private TemporaryTimeSheet(
			List<TemporaryFrameTimeSheet> frameTimeSheets,
			TemporaryTimes temporaryTimes) {
		
		this.frameTimeSheets = frameTimeSheets;
		this.temporaryTimes = temporaryTimes;
	}
	
	/**
	 * 作成する
	 * @param companySet 会社別設定管理
	 * @param personDailySet 社員設定管理
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param workType 勤務種類
	 * @param oneDayOfRange 1日の範囲
	 * @return 臨時時間帯
	 */
	public static Optional<TemporaryTimeSheet> create(
			ManagePerCompanySet companySet,
			ManagePerPersonDailySet personDailySet,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			WorkType workType,
			TimeSpanForDailyCalc oneDayOfRange) {
		
		// 労働制を取得
		if (personDailySet.getPersonInfo().getLaborSystem().isExcludedWorkingCalculate()) return Optional.empty();
		// 補正後臨時出退勤を作成する
		List<TimeLeavingWork> correctedTempTimeLeavingList = TemporaryTimeSheet.createCorrectedTimeLeavingWork(
				integrationOfWorkTime, integrationOfDaily);
		if (correctedTempTimeLeavingList.size() <= 0) return Optional.empty();
		// 臨時枠時間帯を作成する
		List<TemporaryFrameTimeSheet> temporaryFrameList = TemporaryTimeSheet.createTemporaryFrameTimeSheet(
				companySet, personDailySet, integrationOfWorkTime, integrationOfDaily,
				workType, oneDayOfRange, correctedTempTimeLeavingList);
		// 臨時時間帯を仮作成する
		TemporaryTimeSheet interimTimeSheet = new TemporaryTimeSheet(temporaryFrameList, new TemporaryTimes(0));
		// 臨時枠時間の計算
		List<TemporaryFrameTimeOfDaily> frameTimeList = interimTimeSheet.calcTemporaryFrameTime();
		// 臨時時間帯を作成する
		return Optional.of(new TemporaryTimeSheet(
				interimTimeSheet.frameTimeSheets,
				new TemporaryTimes((int)frameTimeList.stream()
						.filter(c -> c.getTemporaryTime().valueAsMinutes() > 0).count())));
	}
	
	/**
	 * 補正後臨時出退勤を作成する
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(WORK)
	 * @return 補正後臨時出退勤
	 */
	private static List<TimeLeavingWork> createCorrectedTimeLeavingWork(
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily) {
		
		// 日別勤怠の臨時枠時間の取得
		if (!integrationOfDaily.getTempTime().isPresent()) return new ArrayList<>();
		// ジャスト遅刻、早退による時刻補正
		RoundingTime roundingTime = integrationOfWorkTime.getCommonSetting().getStampSet().getRoundingTime();
		List<TimeLeavingWork> justTimeLeavingWorks = roundingTime.justTImeCorrection(
				JustCorrectionAtr.USE, integrationOfDaily.getTempTime().get().getTimeLeavingWorks());
		// 出退勤時刻を丸める
		List<TimeLeavingWork> roundedTimeLeavingWorks = roundingTime.roundingttendance(justTimeLeavingWorks);
		// 補正後臨時出退勤を返す
		return roundedTimeLeavingWorks;
	}

	/**
	 * 臨時枠時間帯を作成する
	 * @param companySet 会社別設定管理
	 * @param personDailySet 社員設定管理
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param workType 勤務種類
	 * @param oneDayOfRange 1日の範囲
	 * @param correctedTempTimeLeavingList 補正後臨時出退勤
	 * @return 臨時枠時間帯List
	 */
	private static List<TemporaryFrameTimeSheet> createTemporaryFrameTimeSheet(
			ManagePerCompanySet companySet,
			ManagePerPersonDailySet personDailySet,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			WorkType workType,
			TimeSpanForDailyCalc oneDayOfRange,
			List<TimeLeavingWork> correctedTempTimeLeavingList) {
		
		// 控除時間帯の作成（計算用）
		DeductionTimeSheet deductionTimeSheet = DeductionTimeSheet.createDeductionTimeForCalc(
				workType,
				integrationOfWorkTime,
				integrationOfDaily,
				oneDayOfRange,
				correctedTempTimeLeavingList,
				Optional.empty(),
				companySet,
				personDailySet);
		
		List<TemporaryFrameTimeSheet> result = new ArrayList<>();
		for (TimeLeavingWork timeLeavingWork : correctedTempTimeLeavingList) {
			// 枠ごとの作成処理
			Optional<TemporaryFrameTimeSheet> temporaryFrameTimeSheet = TemporaryFrameTimeSheet.create(
					companySet,
					personDailySet,
					integrationOfWorkTime,
					integrationOfDaily,
					timeLeavingWork,
					deductionTimeSheet);
			if (temporaryFrameTimeSheet.isPresent()) result.add(temporaryFrameTimeSheet.get());
		}
		// 臨時枠時間帯を返す
		return result;
	}
	
	/**
	 * 控除時間の計算
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @param roundAtr 丸め区分
	 * @param canOffset 相殺された時間を控除するかどうか
	 * @return 控除時間
	 */
	public AttendanceTime calcDeductionTime(
			ConditionAtr conditionAtr,
			DeductionAtr dedAtr,
			TimeSheetRoundingAtr roundAtr,
			NotUseAtr canOffset) {
		
		// 控除時間の計算
		return ActualWorkTimeSheetListService.calcDeductionTime(conditionAtr, dedAtr, roundAtr,
				this.frameTimeSheets.stream().map(tc -> (ActualWorkingTimeSheet)tc).collect(Collectors.toList()),
				canOffset);
	}
	
	/**
	 * 臨時加給時間の計算
	 * @param isSpec 加給区分（特定日加給=true）
	 * @param bpTimeItemSets 加給自動計算設定
	 * @param calcAtrOfDaily 計算区分
	 * @return 臨時加給時間
	 */
	public List<BonusPayTime> calcTemporaryBonusPayTime(
			boolean isSpec,
			List<BPTimeItemSetting> bpTimeItemSets,
			CalAttrOfDailyAttd calcAtrOfDaily) {
		
		Map<Integer, BonusPayTime> temporaryBonusPayMap = new HashMap<>();
		
		// 臨時枠時間帯の取得
		for (TemporaryFrameTimeSheet frameTimeSheet : this.frameTimeSheets) {
			// 加給時間の計算
			List<BonusPayTime> bonusPayList = new ArrayList<>();
			if (!isSpec) {
				bonusPayList.addAll(frameTimeSheet.calcBonusPay(
						ActualWorkTimeSheetAtr.OverTimeWork, bpTimeItemSets, calcAtrOfDaily));
			}
			else {
				bonusPayList.addAll(frameTimeSheet.calcSpacifiedBonusPay(
						ActualWorkTimeSheetAtr.OverTimeWork, bpTimeItemSets, calcAtrOfDaily));
			}
			for (BonusPayTime bonusPay : bonusPayList) {
				int itemNo = bonusPay.getBonusPayTimeItemNo();
				if (temporaryBonusPayMap.containsKey(itemNo)) {
					BonusPayTime source = temporaryBonusPayMap.get(itemNo);
					BonusPayTime sum = new BonusPayTime(
							itemNo,
							source.getBonusPayTime().addMinutes(bonusPay.getBonusPayTime().valueAsMinutes()),
							source.getWithinBonusPay().addMinutes(
									bonusPay.getWithinBonusPay().getTime(), bonusPay.getWithinBonusPay().getCalcTime()),
							source.getExcessBonusPayTime().addMinutes(
									bonusPay.getExcessBonusPayTime().getTime(), bonusPay.getExcessBonusPayTime().getCalcTime()));
					temporaryBonusPayMap.replace(itemNo, sum);
				}
				else {
					temporaryBonusPayMap.put(bonusPay.getBonusPayTimeItemNo(), bonusPay);
				}
			}
		}
		// 臨時加給時間を返す
		return temporaryBonusPayMap.values().stream().collect(Collectors.toList());
	}
	
	/**
	 * 臨時回数の計算
	 * @return 臨時回数
	 */
	public TemporaryTimes calcTemporaryTimes() {
		
		// 臨時回数を返す
		return this.temporaryTimes;
	}
	
	/**
	 * 臨時時間の計算
	 * @return 臨時時間
	 */
	public AttendanceTime calcTemporaryTime() {
	
		// 臨時枠時間の計算
		List<TemporaryFrameTimeOfDaily> frameTimeList = this.calcTemporaryFrameTime();
		// 臨時時間を合計する
		int temporaryMinutes = frameTimeList.stream().mapToInt(c -> c.getTemporaryTime().valueAsMinutes()).sum();
		// 臨時時間を返す
		return new AttendanceTime(temporaryMinutes);
	}
	
	/**
	 * 臨時枠時間の計算
	 * @return 臨時枠時間List
	 */
	public List<TemporaryFrameTimeOfDaily> calcTemporaryFrameTime() {
		
		List<TemporaryFrameTimeOfDaily> temporaryFrameTimeList = new ArrayList<>();
		
		// 臨時枠時間帯を確認
		for (TemporaryFrameTimeSheet frameTimeSheet : this.frameTimeSheets) {
			// 時間の計算
			AttendanceTime temporaryTime = frameTimeSheet.calcTotalTime();
			// 深夜時間の計算
			TimeDivergenceWithCalculation midnightTime = frameTimeSheet.calcMidNightTime(
					new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS));
			// 日別勤怠の臨時枠時間の作成
			temporaryFrameTimeList.add(new TemporaryFrameTimeOfDaily(
					frameTimeSheet.getWorkNo(), temporaryTime, midnightTime.getTime()));
		}
		// 臨時枠時間を返す
		return temporaryFrameTimeList;
	}
	
	/**
	 * 臨時深夜時間の計算
	 * @return 臨時深夜時間
	 */
	public AttendanceTime calcTemporaryMidnightTime() {
		
		// 臨時枠時間の計算
		List<TemporaryFrameTimeOfDaily> frameTimeList = this.calcTemporaryFrameTime();
		// 臨時深夜時間を合計する
		int midnightMinutes = frameTimeList.stream().mapToInt(c -> c.getTemporaryLateNightTime().valueAsMinutes()).sum();
		// 臨時深夜時間を返す
		return new AttendanceTime(midnightMinutes);
	}
	
	/**
	 * 臨時時間帯に含まない時間帯の取得
	 * @param timeSpan 計算用時間帯
	 * @return 計算用時間帯List
	 */
	public List<TimeSpanForCalc> getTimeSheetNotDupTemporary(TimeSpanForCalc timeSpan){
		
		List<TimeSpanForCalc> target = new ArrayList<>();	// 比較対象List
		// 臨時枠時間帯を確認する
		for (TemporaryFrameTimeSheet frame : this.frameTimeSheets){
			// 比較対象Listに追加する
			target.add(frame.getTimeSheet().getTimeSpan());
		}
		// 指定Listと重複していない時間帯の取得
		List<TimeSpanForCalc> results = timeSpan.getNotDuplicatedWith(target);
		// 結果を返す
		return results;
	}

	/**
	 * 臨時時間帯の取得
	 * @param companySet 会社別設定管理
	 * @param personDailySet 社員設定管理
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param workType 勤務種類
	 * @param oneDayOfRange 1日の範囲
	 * @return 臨時時間帯
	 */
	public static Optional<TemporaryTimeSheet> getTemporaryTimeSheet(
			ManagePerCompanySet companySet,
			ManagePerPersonDailySet personDailySet,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			WorkType workType,
			TimeSpanForDailyCalc oneDayOfRange) {
		
		// 臨時時間帯を作成する
		Optional<TemporaryTimeSheet> myclass = TemporaryTimeSheet.create(companySet, personDailySet,
				integrationOfWorkTime, integrationOfDaily, workType, oneDayOfRange);
		if (!myclass.isPresent()) return Optional.empty();
		// 計算方法を確認する
		WorkTimezoneExtraordTimeSet extraordTimeSet = integrationOfWorkTime.getCommonSetting().getExtraordTimeSet();
		if (extraordTimeSet.getCalculateMethod() == ExtraordTimeCalculateMethod.OVERTIME_RESTTIME_RECORD) {
			// 臨時枠時間帯を空にして返す
			return Optional.of(new TemporaryTimeSheet(new ArrayList<>(), myclass.get().getTemporaryTimes()));
		}
		// 臨時時間帯をそのまま返す
		return myclass;
	}
}
