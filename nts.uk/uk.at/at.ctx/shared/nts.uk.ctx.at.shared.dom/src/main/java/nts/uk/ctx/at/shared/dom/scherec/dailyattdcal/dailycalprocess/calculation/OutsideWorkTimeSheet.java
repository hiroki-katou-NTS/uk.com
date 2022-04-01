package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.TemporaryTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 就業時間外時間帯
 * @author keisuke_hoshina
 */
@Getter
public class OutsideWorkTimeSheet {

	//残業時間帯
	private Optional<OverTimeSheet> overTimeWorkSheet;

	//休出時間帯
	private Optional<HolidayWorkTimeSheet> holidayWorkTimeSheet;

	/** 臨時時間帯 */
	@Setter
	private Optional<TemporaryTimeSheet> temporaryTimeSheet;
	
	/**
	 * Constructor 
	 */
	public OutsideWorkTimeSheet(
			Optional<OverTimeSheet> overTimeWorkSheet,
			Optional<HolidayWorkTimeSheet> holidayWorkTimeSheet,
			Optional<TemporaryTimeSheet> temporaryTimeSheet) {
		super();
		this.overTimeWorkSheet = overTimeWorkSheet;
		this.holidayWorkTimeSheet = holidayWorkTimeSheet;
		this.temporaryTimeSheet = temporaryTimeSheet;
	}
	
	/**
	 * 空で作成する
	 * @return 就業時間外時間帯
	 */
	public static OutsideWorkTimeSheet createEmpty() {
		return new OutsideWorkTimeSheet(Optional.empty(), Optional.empty(), Optional.empty());
	}
	
	
	/**
	 * 就業時間外時間帯を作成する
	 * アルゴリズム：就業時間外時間帯
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param deductionTimeSheet 控除時間帯
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param timeLeavingWork 出退勤
	 * @param previousAndNextDaily 前日と翌日の勤務
	 * @param createdWithinWorkTimeSheet 就業時間内時間帯
	 * @param oneDayOfRange 1日の範囲
	 * @return 就業時間外時間帯
	 */
	public static OutsideWorkTimeSheet createOutsideWorkTimeSheet(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			DeductionTimeSheet deductionTimeSheet,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			TimeLeavingWork timeLeavingWork,
			PreviousAndNextDaily previousAndNextDaily,
			WithinWorkTimeSheet createdWithinWorkTimeSheet,
			TimeSpanForDailyCalc oneDayOfRange) {
		
		List<HolidayWorkFrameTimeSheetForCalc> holidayWorkFrameTimeSheetForCalc = new ArrayList<>();
		List<OverTimeFrameTimeSheetForCalc> overTimeWorkFrameTimeSheet = new ArrayList<>();
		if (todayWorkType.isWeekDayAttendance()) {
			/* 就業時間外時間帯の平日出勤の処理 */
			overTimeWorkFrameTimeSheet = OverTimeFrameTimeSheetForCalc.createOverWorkFrame(
					companyCommonSetting,
					personDailySetting,
					todayWorkType,
					integrationOfWorkTime,
					integrationOfDaily,
					predetermineTimeSetForCalc,
					deductionTimeSheet,
					timeLeavingWork,
					createdWithinWorkTimeSheet,
					oneDayOfRange);

			/* 0時跨ぎ処理 */
			if(companyCommonSetting.getZeroTime().isPresent()) {
				OverDayEnd overTimeDayEnd = OverDayEnd.forOverTime(
						integrationOfWorkTime.getCommonSetting().isZeroHStraddCalculateSet(),
						overTimeWorkFrameTimeSheet,
						previousAndNextDaily.getPreviousWorkType(),
						todayWorkType,
						previousAndNextDaily.getNextWorkType(),
						previousAndNextDaily.getPreviousInfo(),
						previousAndNextDaily.getNextInfo(),
						companyCommonSetting.getZeroTime().get());
				overTimeWorkFrameTimeSheet = overTimeDayEnd.getOverTimeList();
				holidayWorkFrameTimeSheetForCalc = overTimeDayEnd.getHolList();
			}
		}
		if(todayWorkType.isHolidayWork()) {
			/* 休日出勤の処理 */
			holidayWorkFrameTimeSheetForCalc = HolidayWorkFrameTimeSheetForCalc.createHolidayTimeWorkFrame(
					companyCommonSetting,
					personDailySetting,
					integrationOfWorkTime,
					integrationOfDaily,
					timeLeavingWork,
					todayWorkType,
					deductionTimeSheet,
					oneDayOfRange);

			/* 0時跨ぎ */
			if(companyCommonSetting.getZeroTime().isPresent()) {
				OverDayEnd holidayWorkDayEnd = OverDayEnd.forHolidayWorkTime(
						integrationOfWorkTime.getCommonSetting().isZeroHStraddCalculateSet(),
						holidayWorkFrameTimeSheetForCalc,
						previousAndNextDaily.getPreviousWorkType(),
						todayWorkType,
						previousAndNextDaily.getNextWorkType(),
						previousAndNextDaily.getPreviousInfo(),
						previousAndNextDaily.getNextInfo(),
						companyCommonSetting.getZeroTime().get());
				overTimeWorkFrameTimeSheet = holidayWorkDayEnd.getOverTimeList();
				holidayWorkFrameTimeSheetForCalc = holidayWorkDayEnd.getHolList();
			}
		}
		return new OutsideWorkTimeSheet(
				Optional.of(new OverTimeSheet(overTimeWorkFrameTimeSheet)),
				Optional.of(new HolidayWorkTimeSheet(holidayWorkFrameTimeSheetForCalc)),
				Optional.empty());
	}
	
	/**
	 * 残業時間帯から控除時間を取得
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @param roundAtr 丸め区分
	 * @return 控除時間
	 */
	public AttendanceTime getDeductionTimeFromOverTime(
			ConditionAtr conditionAtr, DeductionAtr dedAtr, Optional<WorkTimezoneGoOutSet> goOutSet, NotUseAtr canOffset) {
		
		if (this.overTimeWorkSheet.isPresent()){
			return this.overTimeWorkSheet.get().getDeductionTime(conditionAtr, dedAtr, goOutSet, canOffset);
		}
		return new AttendanceTime(0);
	}
	
	/**
	 * 休出時間帯から控除時間を取得
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @param roundAtr 丸め区分
	 * @return 控除時間
	 */
	public AttendanceTime getDeductionTimeFromHolidayWork(
			ConditionAtr conditionAtr, DeductionAtr dedAtr, Optional<WorkTimezoneGoOutSet> goOutSet, NotUseAtr canOffset) {
		
		if(this.holidayWorkTimeSheet.isPresent()) {
			return this.holidayWorkTimeSheet.get().getDeductionTime(conditionAtr, dedAtr, goOutSet, canOffset);
		}
		return new AttendanceTime(0);
	}
	
	/**
	 * 臨時時間帯から控除時間を取得
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @param roundAtr 丸め区分
	 * @return 控除時間
	 */
	public AttendanceTime getDeductionTimeFromTemporary(
			ConditionAtr conditionAtr, DeductionAtr dedAtr, Optional<WorkTimezoneGoOutSet> goOutSet, NotUseAtr canOffset) {
		
		if(this.temporaryTimeSheet.isPresent()) {
			return this.temporaryTimeSheet.get().calcDeductionTime(conditionAtr, dedAtr, goOutSet, canOffset);
		}
		return new AttendanceTime(0);
	}
	
	/**
	 * 残業時間帯から控除回数を計算
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @return 控除回数
	 */
	public int calcDeductionCountFromOverTime(ConditionAtr conditionAtr, DeductionAtr dedAtr) {
		
		if (this.overTimeWorkSheet.isPresent()){
			return this.overTimeWorkSheet.get().calcDeductionCount(conditionAtr, dedAtr);
		}
		return 0;
	}
	
	/**
	 * 休出時間帯から控除回数を計算
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @return 控除回数
	 */
	public int calcDeductionCountFromHolidayWork(ConditionAtr conditionAtr, DeductionAtr dedAtr) {
		
		if(this.holidayWorkTimeSheet.isPresent()) {
			return this.holidayWorkTimeSheet.get().calcDeductionCount(conditionAtr, dedAtr);
		}
		return 0;
	}
	
	/**
	 * 流動勤務(平日・就外）
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param deductTimeSheet 控除時間帯
	 * @param createdWithinWorkTimeSheet 就業時間内時間帯
	 * @param previousAndNextDaily 前日と翌日の勤務
	 * @param timeLeavingOfDaily 日別勤怠の出退勤
	 * @param oneDayOfRange 1日の範囲
	 * @return 就業時間外時間帯
	 */
	public static OutsideWorkTimeSheet createOverTimeAsFlow(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			DeductionTimeSheet deductTimeSheet,
			WithinWorkTimeSheet createdWithinWorkTimeSheet,
			PreviousAndNextDaily previousAndNextDaily,
			TimeLeavingOfDailyAttd timeLeavingOfDaily,
			TimeSpanForDailyCalc oneDayOfRange) {
		
		Optional<OverTimeSheet> overTimeSheet = OverTimeSheet.createAsFlow(
				companyCommonSetting,
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				predetermineTimeSetForCalc,
				deductTimeSheet,
				createdWithinWorkTimeSheet,
				timeLeavingOfDaily,
				oneDayOfRange);
		
		if(!overTimeSheet.isPresent())
			return new OutsideWorkTimeSheet(Optional.empty(), Optional.empty(), Optional.empty());
		
		//0時跨ぎの時間帯分割
		OverDayEnd overDayEnd = OverDayEnd.forOverTime(
				integrationOfWorkTime.getFlowWorkSetting().get().getCommonSetting().isZeroHStraddCalculateSet(),
				overTimeSheet.get().getFrameTimeSheets(),
				previousAndNextDaily.getPreviousWorkType(),
				todayWorkType,
				previousAndNextDaily.getNextWorkType(),
				previousAndNextDaily.getPreviousInfo(),
				previousAndNextDaily.getNextInfo(),
				companyCommonSetting.getZeroTime().get());
		
		return new OutsideWorkTimeSheet(
				Optional.of(new OverTimeSheet(overDayEnd.getOverTimeList())),
				Optional.of(new HolidayWorkTimeSheet(overDayEnd.getHolList())),
				Optional.empty());
	}
	
	/**
	 * 流動勤務(休日出勤)
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param deductTimeSheet 控除時間帯
	 * @param holidayStartEnd 休出開始終了
	 * @param oneDayOfRange 1日の計算範囲
	 * @param previousAndNextDaily 前日と翌日の勤務
	 * @return 就業時間外時間帯
	 */
	public static OutsideWorkTimeSheet createHolidayAsFlow(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			DeductionTimeSheet deductTimeSheet,
			TimeSpanForDailyCalc holidayStartEnd,
			TimeSpanForDailyCalc oneDayOfRange,
			PreviousAndNextDaily previousAndNextDaily) {
		
		HolidayWorkTimeSheet hollidayWorkTImeSheet = HolidayWorkTimeSheet.createAsFlow(
				companyCommonSetting,
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				deductTimeSheet,
				holidayStartEnd,
				oneDayOfRange);
		
		//0時跨ぎ処理
		OverDayEnd overDayEnd = OverDayEnd.forHolidayWorkTime(
				integrationOfWorkTime.getCommonSetting().isZeroHStraddCalculateSet(),
				hollidayWorkTImeSheet.getWorkHolidayTime(),
				previousAndNextDaily.getPreviousWorkType(),
				todayWorkType,
				previousAndNextDaily.getNextWorkType(),
				previousAndNextDaily.getPreviousInfo(),
				previousAndNextDaily.getNextInfo(),
				companyCommonSetting.getZeroTime().get());
		
		return new OutsideWorkTimeSheet(
				Optional.of(new OverTimeSheet(overDayEnd.getOverTimeList())),
				Optional.of(new HolidayWorkTimeSheet(overDayEnd.getHolList())),
				Optional.empty());
	}
	
	/**
	 * 重複する時間帯で作り直す
	 * @param timeSpan 時間帯
	 * @param commonSet 就業時間帯の共通設定
	 * @return 就業時間外時間帯
	 */
	public OutsideWorkTimeSheet recreateWithDuplicate(TimeSpanForDailyCalc timeSpan, Optional<WorkTimezoneCommonSet> commonSet) {
		//残業時間帯を重複する時間帯で作り直す
		Optional<OverTimeSheet> overTime = this.overTimeWorkSheet.flatMap(o -> o.recreateWithDuplicate(timeSpan, commonSet));
		//休出時間帯を重複する時間帯で作り直す
		Optional<HolidayWorkTimeSheet> holidayWorkTime = this.holidayWorkTimeSheet.flatMap(h -> h.recreateWithDuplicate(timeSpan, commonSet));
		return new OutsideWorkTimeSheet(overTime, holidayWorkTime, this.temporaryTimeSheet);
	}
	
	/**
	 * 逆丸めにして取得する
	 * @return 就業時間外時間帯
	 */
	public OutsideWorkTimeSheet getReverseRounding() {
		return new OutsideWorkTimeSheet(
				this.overTimeWorkSheet.map(o -> o.getReverseRounding()),
				this.holidayWorkTimeSheet.map(h -> h.getReverseRounding()), Optional.empty());
	}
}
