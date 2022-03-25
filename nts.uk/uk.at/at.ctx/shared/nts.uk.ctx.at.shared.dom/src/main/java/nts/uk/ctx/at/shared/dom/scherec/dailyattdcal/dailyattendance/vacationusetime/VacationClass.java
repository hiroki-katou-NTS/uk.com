package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime;

import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.*;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.VacationAddTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 休暇クラス
 * @author keisuke_hoshina
 */
@Value
public class VacationClass {
	
	/** 日別実績の休暇 */
	private HolidayOfDaily holidayOfDaily;
	
	/**
	 * 全て0で作成する
	 * @return 休暇クラス
	 */
	public static VacationClass createAllZero() {
		return new VacationClass(
				new HolidayOfDaily(
						new AbsenceOfDaily(new AttendanceTime(0)),
						new TimeDigestOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
						new YearlyReservedOfDaily(new AttendanceTime(0)),
						new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
						new OverSalaryOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
						new SpecialHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
						new AnnualOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
						new TransferHolidayOfDaily(new AttendanceTime(0))));
	}

	public static interface Require extends RefDesForAdditionalTakeLeave.Require {}
	
	/**
	 * 休暇使用時間の計算
	 * @param recordReGet 実績
	 * @param workType 勤務種類
	 * @param conditionItem 労働条件項目
	 * @param goOutTimeOfDaily 日別実績の外出時間(List)
	 * @param lateTimeOfDaily 日別実績の遅刻時間(List)
	 * @param leaveEarlyTimeOfDaily 日別実績の早退時間(list)
	 * @return 日別実績の休暇
	 */
	public static HolidayOfDaily calcUseRestTime(
			Require require,
			String employeeId,
			GeneralDate baseDate,
			WorkType workType,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
			Optional<HolidayAddtionSet> holidayAddtionSet,
			IntegrationOfDaily integrationOfDaily) {

		//欠勤使用時間
		AttendanceTime absenceUseTime = vacationTimeOfCalcDaily(require, employeeId, baseDate, integrationOfDaily.getWorkInformation(), workType,
				VacationCategory.Absence, predetermineTimeSet, holidayAddtionSet);
		AbsenceOfDaily absenceOfDaily = new AbsenceOfDaily(absenceUseTime);

		//時間消化休暇使用時間
		AttendanceTime timeDigest = new AttendanceTime(0);
		TimeDigestOfDaily timeDigestOfDaily = new TimeDigestOfDaily(timeDigest, new AttendanceTime(0));

		//積立年休使用時間
		AttendanceTime yearlyReservedTime = vacationTimeOfCalcDaily(require, employeeId, baseDate, integrationOfDaily.getWorkInformation(), workType,
				VacationCategory.YearlyReserved, predetermineTimeSet, holidayAddtionSet);
		YearlyReservedOfDaily yearlyReservedOfDaily = new YearlyReservedOfDaily(yearlyReservedTime);

		//代休使用時間の計算
		AttendanceTime substUseTime = AttendanceTime.ZERO;
		AttendanceTime substDigestTime = AttendanceTime.ZERO;
		{
			// 日数単位の休暇時間計算
			AttendanceTime substDay = vacationTimeOfCalcDaily(require, employeeId, baseDate, integrationOfDaily.getWorkInformation(), workType,
					VacationCategory.SubstituteHoliday, predetermineTimeSet, holidayAddtionSet);
			// 合計時間代休使用時間を取得
			AttendanceTime substTime = AttendanceTime.ZERO;
			if (integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()){
				substTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getTotalTimeCompLeaveUseTime();
			}
			substUseTime = new AttendanceTime(substDay.valueAsMinutes() + substTime.valueAsMinutes());
			// 時間消化休暇使用時間を取得する
		}
		SubstituteHolidayOfDaily substituteOfDaily = new SubstituteHolidayOfDaily(substUseTime, substDigestTime);

		//超過有休使用時間
		AttendanceTime overUseTime = AttendanceTime.ZERO;
		AttendanceTime overDigestTime = AttendanceTime.ZERO;
		{
			// 日数単位の休暇時間計算
			AttendanceTime overDay = vacationTimeOfCalcDaily(require, employeeId, baseDate, integrationOfDaily.getWorkInformation(), workType,
					VacationCategory.TimeDigestVacation, predetermineTimeSet, holidayAddtionSet);
			// 合計超過有給使用時間を取得
			AttendanceTime overTime = AttendanceTime.ZERO;
			if (integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()){
				overTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getTotalExcessHolidayUseTime();
			}
			overUseTime = new AttendanceTime(overDay.valueAsMinutes() + overTime.valueAsMinutes());
			// 時間消化休暇使用時間を取得する
		}
		OverSalaryOfDaily overSalaryOfDaily = new OverSalaryOfDaily(overUseTime, overDigestTime);

		//特別休暇使用時間の計算
		AttendanceTime specialUseTime = AttendanceTime.ZERO;
		{
			// 日数単位の休暇時間計算
			AttendanceTime spacialDay = vacationTimeOfCalcDaily(require, employeeId, baseDate, integrationOfDaily.getWorkInformation(), workType,
					VacationCategory.SpecialHoliday, predetermineTimeSet, holidayAddtionSet);
			// 合計特別休暇使用時間を取得
			AttendanceTime specialTime = AttendanceTime.ZERO;
			if (integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()){
				specialTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getTotalSpecialHolidayUseTime();
			}
			specialUseTime = new AttendanceTime(spacialDay.valueAsMinutes() + specialTime.valueAsMinutes());
		}
		SpecialHolidayOfDaily specHolidayOfDaily = new SpecialHolidayOfDaily(specialUseTime, new AttendanceTime(0));

		//年休使用時間の計算
		AttendanceTime annualUseTime = AttendanceTime.ZERO;
		AttendanceTime annualDigestTime = AttendanceTime.ZERO;
		{
			// 日数単位の休暇時間計算
			AttendanceTime annualDay = vacationTimeOfCalcDaily(require, employeeId, baseDate, integrationOfDaily.getWorkInformation(), workType,
					VacationCategory.AnnualHoliday, predetermineTimeSet, holidayAddtionSet);
			// 合計時間年休使用時間を取得
			AttendanceTime annualTime = AttendanceTime.ZERO;
			if (integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()){
				annualTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getTotalTimeAnnualUseTime();
			}
			annualUseTime = new AttendanceTime(annualDay.valueAsMinutes() + annualTime.valueAsMinutes());
			// 時間消化休暇使用時間を取得する
		}
		AnnualOfDaily annualOfDaily = new AnnualOfDaily(annualUseTime, annualDigestTime);
		
		//振休使用時間の計算
		AttendanceTime transferHolidayUseTime = vacationTimeOfCalcDaily(require, employeeId, baseDate, integrationOfDaily.getWorkInformation(), workType,
				VacationCategory.Pause, predetermineTimeSet, holidayAddtionSet);
		TransferHolidayOfDaily transferHolidayOfDaily =new TransferHolidayOfDaily(transferHolidayUseTime);		

		return new HolidayOfDaily(absenceOfDaily, timeDigestOfDaily, yearlyReservedOfDaily, substituteOfDaily,
				overSalaryOfDaily, specHolidayOfDaily, annualOfDaily, transferHolidayOfDaily);
	}
	
	/**
	 * 日数単位の休暇時間計算
	 * @param require Require
	 * @param employeeId 社員ID
	 * @param baseDate 基準日
	 * @param workInfo 日別勤怠の勤務情報
	 * @param workType 勤務種類
	 * @param vacationCategory 休暇種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @return 休暇時間
	 */
	public static AttendanceTime vacationTimeOfCalcDaily(
			Require require,
			String employeeId,
			GeneralDate baseDate,
			WorkInfoOfDailyAttendance workInfo,
			WorkType workType,
			VacationCategory vacationCategory,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
			Optional<HolidayAddtionSet> holidayAddtionSet) {

		// 所定時間の内訳を取得
		BreakDownTimeDay breakDown = getBreakDownOfPredTime(
				require, employeeId, baseDate, workInfo, vacationCategory, predetermineTimeSet, holidayAddtionSet);
		// 指定の分類の1日午前午後区分を取得
		Optional<WorkAtr> workAtr = workType.getWorkAtr(vacationCategory.convertWorkTypeClassification());
		// 取得
		if (!workAtr.isPresent()) return AttendanceTime.ZERO;
		AttendanceTime result = breakDown.get(workAtr.get());
		
		return new AttendanceTime(result.v());
	}

	/**
	 * 所定時間の内訳を取得
	 * @param require Require
	 * @param employeeId 社員ID
	 * @param baseDate 基準日
	 * @param workInfo 日別勤怠の勤務情報
	 * @param vacationCategory 休暇種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @return 所定時間の内訳
	 */
	public static BreakDownTimeDay getBreakDownOfPredTime(
			Require require,
			String employeeId,
			GeneralDate baseDate,
			WorkInfoOfDailyAttendance workInfo,
			VacationCategory vacationCategory,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
			Optional<HolidayAddtionSet> holidayAddtionSet){
		
		BreakDownTimeDay result = BreakDownTimeDay.newInstance();
		switch (vacationCategory){
		case AnnualHoliday:
		case YearlyReserved:
		case SpecialHoliday:
			// 休暇加算時間設定を確認
			if (holidayAddtionSet.isPresent()){
				// 休暇加算時間の設定の取得
				result = getVacationAddSet(require, employeeId, workInfo, baseDate,
						holidayAddtionSet.get(), vacationCategory);
			}
			break;
		default:
			// 所定時間を取得
			if (predetermineTimeSet.isPresent()){
				result = predetermineTimeSet.get().getAdditionSet().getPredTime();
			}
			break;
		}
		// 所定時間の内訳を返す
		return result;
	}
	
	/**
	 * 休暇加算時間の設定の取得
	 * @param require Require
	 * @param employeeId 社員ID
	 * @param workInfo 日別勤怠の勤務情報
	 * @param baseDate 基準日
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param vacationCategory 休暇種類
	 * @return 1日の時間内訳
	 */
	public static BreakDownTimeDay getVacationAddSet(
			Require require,
			String employeeId,
			WorkInfoOfDailyAttendance workInfo,
			GeneralDate baseDate,
			HolidayAddtionSet holidayAddtionSet,
			VacationCategory vacationCategory) {

		BreakDownTimeDay result = BreakDownTimeDay.newInstance();

		// 休暇加算時間の取得
		result = holidayAddtionSet.getReference().getVacationAddTime(require,
				AppContexts.user().companyId(), employeeId, workInfo.getRecordInfo(), baseDate);
		// 休暇種類を確認
		switch (vacationCategory){
		case AnnualHoliday:
			// 加算する休暇設定を取得　→　加算する設定を判断
			if (holidayAddtionSet.getAdditionVacationSet().getAnnualHoliday().isNotUse()){
				// 時間内訳をクリア
				result = BreakDownTimeDay.newInstance();
			}
			break;
		case SpecialHoliday:
			// 加算する休暇設定を取得　→　加算する設定を判断
			if (holidayAddtionSet.getAdditionVacationSet().getSpecialHoliday().isNotUse()){
				// 時間内訳をクリア
				result = BreakDownTimeDay.newInstance();
			}
			break;
		case YearlyReserved:
			// 加算する休暇設定を取得　→　加算する設定を判断
			if (holidayAddtionSet.getAdditionVacationSet().getYearlyReserved().isNotUse()){
				// 時間内訳をクリア
				result = BreakDownTimeDay.newInstance();
			}
			break;
		default:
			break;
		}
		// 1日の時間内訳を返す
		return result;
	}

	/**
	 * 日単位の休暇加算時間の計算
	 * @param require Require
	 * @param premiumAtr 割増区分（"通常"、"割増")
	 * @param employeeId 社員ID
	 * @param baseDate 基準日
	 * @param workType 勤務種類
	 * @param workInfo 日別勤怠の勤務情報
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @return 休暇加算時間
	 */
	public static VacationAddTime calcVacationAddTime(
			Require require,
			PremiumAtr premiumAtr,
			String employeeId,
			GeneralDate baseDate,
			WorkType workType,
			WorkInfoOfDailyAttendance workInfo,
			AddSetting addSetting,
			Optional<HolidayAddtionSet> holidayAddtionSet) {
		
		VacationAddTime vacationAddTime =
				new VacationAddTime(AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO);
		// 休暇加算するかどうか判断
		if (addSetting.isAddVacation(premiumAtr) == NotUseAtr.NOT_USE){
			// 加算しない時、休暇加算時間を全て0で返す
			return vacationAddTime;
		}
		// 加算時間の設定を取得
		BreakDownTimeDay breakdownTimeDay = holidayAddtionSet.get().getReference().getVacationAddTime(
				require, AppContexts.user().companyId(), employeeId, workInfo.getRecordInfo(), baseDate);
		// 労働時間の加算設定を確認する
		AddSettingOfWorkingTime holidayCalcMethodSet = addSetting.getAddSetOfWorkingTime();
		// 休暇加算時間を加算するかどうか判断
		vacationAddTime = judgeVacationAddTime(breakdownTimeDay, premiumAtr,
				holidayAddtionSet.get(), workType, holidayCalcMethodSet);
		// 休暇加算時間を返す
		return vacationAddTime;
	}

	/**
	 * 休暇加算時間の計算
	 * @param breakdownTimeDay 休暇加算時間
	 * @param premiumAtr 割増区分
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param workType 勤務種類
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @return 休暇加算時間
	 */
	public static VacationAddTime judgeVacationAddTime(
			BreakDownTimeDay breakdownTimeDay,
			PremiumAtr premiumAtr,
			HolidayAddtionSet holidayAddtionSet,
			WorkType workType,
			AddSettingOfWorkingTime holidayCalcMethodSet) {
		
		VacationAddTime vacationAddTime = new VacationAddTime(new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0));
		// 加算する休暇設定を取得
		LeaveSetAdded leaveSetAdded = getAddVacationSet(premiumAtr, holidayAddtionSet, holidayCalcMethodSet);
		// 勤務区分をチェックする
		if (workType.isOneDay()) {
			val addTimes = checkVacationToAdd(leaveSetAdded, workType.getDailyWork().getOneDay(), breakdownTimeDay.getOneDay());
			vacationAddTime = vacationAddTime.addVacationAddTime(addTimes);
		} else {
			vacationAddTime = vacationAddTime.addVacationAddTime(
					checkVacationToAdd(leaveSetAdded,workType.getDailyWork().getMorning(), breakdownTimeDay.getMorning()));
			vacationAddTime = vacationAddTime.addVacationAddTime(
					checkVacationToAdd(leaveSetAdded,workType.getDailyWork().getAfternoon(), breakdownTimeDay.getAfternoon()));
		}
		return vacationAddTime;
	}

	/**
	 * 加算する休暇の種類を取得
	 * @param premiumAtr 割増区分
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @return 加算する休暇設定
	 */
	private static LeaveSetAdded getAddVacationSet(PremiumAtr premiumAtr,
			HolidayAddtionSet holidayAddtionSet, AddSettingOfWorkingTime holidayCalcMethodSet) {
		LeaveSetAdded leaveSetAdded = new LeaveSetAdded(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);// 下のif文に入らない場合は全てしないを返す
		// 休暇加算設定の取得
		if (holidayCalcMethodSet.isAddVacation(premiumAtr) == NotUseAtr.USE) {// 加算する場合
			leaveSetAdded = holidayAddtionSet.getAdditionVacationSet();
		}
		return leaveSetAdded;
	}

	/**
	 * 休暇加算時間を加算するかチェックする
	 * @param leaveSetAdded 加算する休暇設定
	 * @param workTypeClassification 勤務種類の分類
	 * @param attendanceTime 日単位の休暇時間
	 * @return 休暇加算時間
	 */
	private static VacationAddTime checkVacationToAdd(LeaveSetAdded leaveSetAdded,
			WorkTypeClassification workTypeClassification, AttendanceTime attendanceTime) {
		VacationAddTime vacationAddTime = new VacationAddTime(new AttendanceTime(0), new AttendanceTime(0),
				new AttendanceTime(0));
		if (workTypeClassification.isAnnualLeave()) {// 年休の場合
			if (leaveSetAdded.getAnnualHoliday() == NotUseAtr.USE) {
				vacationAddTime = new VacationAddTime(attendanceTime, new AttendanceTime(0), new AttendanceTime(0));
			}
		} else if (workTypeClassification.isYearlyReserved()) {// 積立年休の場合
			if (leaveSetAdded.getYearlyReserved() == NotUseAtr.USE) {
				vacationAddTime = new VacationAddTime(new AttendanceTime(0), attendanceTime, new AttendanceTime(0));
			}
		} else if (workTypeClassification.isSpecialHoliday()) {// 特別休暇の場合
			if (leaveSetAdded.getSpecialHoliday() == NotUseAtr.USE) {
				vacationAddTime = new VacationAddTime(new AttendanceTime(0), new AttendanceTime(0), attendanceTime);
			}
		}
		return vacationAddTime;
	}
}
