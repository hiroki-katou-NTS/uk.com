package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeVacationWork;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Getter
/** 集計用の加算設定 */
public class WorkTimeAddTimeSetForMonthAggr {

	/** 加算する */
	private boolean addSetUse;
	
	/** 休暇加算する: 労働時間計算時の休暇分を含める設定 */
	private IncludeVacationSetForCalcWorkTime vacationSet;
	
	/** 短時間勤務加算する */
	private boolean addShortTime;
	
	/** インターバル加算する */
	private boolean addInterval;
	
	/** 遅刻早退加算する: 遅刻早退時間の扱い設定単位 */
	private TreatLateEarlyTimeSetUnit lateEarlySet;
	
	/** 欠勤加算する */
	private boolean addAbsence;
	
	/** 割増加算フラグ */
	private boolean premiumFlag;
	
	private AddSettingOfWorkingTime rawSet;
	
	/** 加算時間を取得する */
	public AttendanceTimeMonth calcAddTime(Require require, List<IntegrationOfDaily> dailies, Optional<HolidayAddtionSet> addSet) {
		
		String cid = AppContexts.user().companyId();
		
		int addTime = dailies.stream().filter(d -> d.getAttendanceTimeOfDailyPerformance().isPresent()).mapToInt(r -> {
			
			val d = r.getAttendanceTimeOfDailyPerformance().get();
			val record = Collections.singletonList(r);
			
			/** 休暇加算時間を取得する */
			val vacationTime = calcVacationAddTime(require, cid, record, addSet).valueAsMinutes();
			
			/** 欠勤加算時間を取得する */
			val absenceTime = calcAbsenceAddTime(record).valueAsMinutes();
			
			/** 短時間勤務加算時間を取得する */
			val shortTime = calcShortTimeAddTime(record).valueAsMinutes();
			
			/** 遅刻・早退加算時間を取得する */
			val lateEarlyTime = calcLateEarlyAddTime(require, cid, record).valueAsMinutes();
			
			/**　取得できた時間をすべて合計するする */
			val sum = vacationTime + absenceTime + shortTime + lateEarlyTime;
			
			/** 実働就業時間 */
			val actualWorkTime = d.getActualWorkingTimeOfDaily().getTotalWorkingTime().getWithinStatutoryTimeOfDaily().getActualWorkTime();
			/** 所定労働時間 */
			val prescribedLaborTime = d.getWorkScheduleTimeOfDaily().getRecordPrescribedLaborTime();
			
			/** 所定労働時間超えたかを確認する */
			if (sum + actualWorkTime.valueAsMinutes() < prescribedLaborTime.valueAsMinutes())
				
				/** 加算時間　✙=　一日加算合計時間 */
				return sum;
			
			/** 加算時間　✙=　日別勤怠の勤怠時間．予定時間．実績所定労働時間　－　日別勤怠の勤怠時間．勤務時間．総労働時間．所定内時間実働就業時間 */
			return prescribedLaborTime.valueAsMinutes() - actualWorkTime.valueAsMinutes();
		}).sum();
		
		/** 取得できた時間をすべて加算する */
		return new AttendanceTimeMonth(addTime);
	}
	
	/** 欠勤加算時間を取得する */
	public AttendanceTimeMonth calcAbsenceAddTime(List<IntegrationOfDaily> dailies) {
		
		/** 加算するかを確認する */
		if (!this.addAbsence) return new AttendanceTimeMonth(0);
		
		/** 欠勤時間を取得する */
		val absenveAddTime = dailies.stream()
				.mapToInt(c -> c.getAttendanceTimeOfDailyPerformance()
								.map(at -> at.getActualWorkingTimeOfDaily().getTotalWorkingTime()
												.getHolidayOfDaily().getAbsence().getUseTime().valueAsMinutes())
								.orElse(0))
				.sum();
		
		/** 加算時間を返す */
		return new AttendanceTimeMonth(absenveAddTime);
	} 
	
	/** 遅刻・早退加算時間を取得する */
	public AttendanceTimeMonth calcLateEarlyAddTime(RequireM4 require, String cid, List<IntegrationOfDaily> dailies) {
		
		/** 加算するかを確認する */
		if (!this.lateEarlySet.getTreatSet().isInclude()) return new AttendanceTimeMonth(0);
		
		/** 遅刻・早退時間を求める */
		val absenveAddTime = dailies.stream().mapToInt(c -> getLateEarlyTime(require, cid, c)).sum();
		
		/** 加算時間を返す */
		return new AttendanceTimeMonth(absenveAddTime);
	} 
	
	/** 遅刻・早退時間を求める */
	private int getLateEarlyTime(RequireM4 require, String cid, IntegrationOfDaily daily) {

		/** 遅刻早退を就業時間に含めるか判断する */
		val isInclude = daily.getWorkInformation().getRecordInfo().getWorkTimeCodeNotNull().map(wtc -> {
			
			/** 就業時間帯の共通設定を取得する */
			WorkTimezoneLateEarlySet lateEarlySet = IntegrationOfWorkTime.getWorkTime(require, cid, Optional.of(wtc))
					.getCommonSetting().getLateEarlySet();
			
			return this.lateEarlySet.isIncludeLateEarlyInWorkTime(Optional.of(lateEarlySet));
		}).orElse(false); 
		
		if (!isInclude)
			return 0;
		
		return daily.getAttendanceTimeOfDailyPerformance().map(at -> {
			
			/** 遅刻早退合計時間に加算する */
			val lateAddTime = at.getLateTimeOfDaily().stream().mapToInt(l -> l.getAddTime().valueAsMinutes()).sum();
			
			/** 遅刻早退合計時間に加算する */
			val earlyAddTime = at.getLeaveEarlyTimeOfDaily().stream().mapToInt(l -> l.getAddTime().valueAsMinutes()).sum();
			
			/** 遅刻早退合計時間を返す */
			return lateAddTime + earlyAddTime;
		}).orElse(0);
	}
	
	/** 短時間勤務加算時間を取得する */
	public AttendanceTimeMonth calcShortTimeAddTime(List<IntegrationOfDaily> dailies) {
		
		/** 加算するかを確認する */
		if (!this.addShortTime) return new AttendanceTimeMonth(0);
		
		/** 短時間勤務の時間を取得する */
		val shortTimeAddTime = dailies.stream()
				.mapToInt(c -> c.getAttendanceTimeOfDailyPerformance()
									.map(at -> at.getActualWorkingTimeOfDaily().getTotalWorkingTime()
													.getShotrTimeOfDaily().getAddTime().valueAsMinutes())
									.orElse(0))
				.sum();
		
		/** 加算時間を返す */
		return new AttendanceTimeMonth(shortTimeAddTime);
	} 
	
	/** 休暇加算時間を取得する */
	public AttendanceTimeMonth calcVacationAddTime(RequireM1 require, String cid, 
			List<IntegrationOfDaily> dailies, Optional<HolidayAddtionSet> addSet) {
		
		if (!addSet.isPresent()) return new AttendanceTimeMonth(0);
		
		/** 加算時間＝0 */
		int vacationTime = 0;
		
		for (val daily : dailies) {

			/** 休暇加算時間の取得 */
			vacationTime += calcVacationAddTimeOneDay(require, cid, daily, addSet.get());
		}
		
		/** 加算時間を返す */
		return new AttendanceTimeMonth(vacationTime);
	}
	
	/** 休暇加算時間の取得 */
	private int calcVacationAddTimeOneDay(RequireM1 require, String cid, IntegrationOfDaily daily, HolidayAddtionSet addSet) {
		
		/** 加算するか確認する */
		if (vacationSet.getAddition() == NotUseAtr.NOT_USE) return 0;
		
		/** 月別集計用の時間休暇加算時間を取得する */
		val timeVacationAddTime = getTimeVacationAddTime(require, cid, daily, addSet);
		
		/** 月別集計用の休暇加算時間を取得する */
		val vacationAddTime = getVacationAddTime(require, cid, daily, addSet);
		
		/** 休暇加算時間に加算する */
		return timeVacationAddTime + vacationAddTime;
	}
	
	/** 月別集計用の休暇加算時間を取得する */
	public int getVacationAddTime(RequireM3 require, String cid, IntegrationOfDaily daily, HolidayAddtionSet addSet) {
			
		/** 勤務種類を取得する */
		val workType = require.workType(cid, daily.getWorkInformation().getRecordInfo().getWorkTypeCode());
		
		/** 加算すべきか確認する */
		if (!workType.map(c -> c.getWorkAtr(WorkTypeClassification.AnnualHoliday).isPresent()
				|| c.getWorkAtr(WorkTypeClassification.YearlyReserved).isPresent()
				|| c.getWorkAtr(WorkTypeClassification.SpecialHoliday).isPresent()).orElse(false))
			return 0;
		
		/** １日の時間内訳の取得 */
		val breakdown = addSet.getReference().getVacationAddTime(require, cid, daily.getEmployeeId(), 
							daily.getWorkInformation().getRecordInfo().getWorkTimeCodeNotNull(), daily.getYmd());
		
		/** 各休暇の時間消化休暇使用時間を取得 */
		/** TODO: 時間消化休暇使用まだ対応しないため一旦保留*/
		
		/** 休暇加算時間の計算 */
		val vacationAddTime = VacationClass.judgeVacationAddTime(breakdown, 
				premiumFlag ? PremiumAtr.Premium : PremiumAtr.RegularWork, 
						addSet, workType.get(), this.rawSet);
		
		/** 休暇加算合計時間を計算する */
		return vacationAddTime.calcTotaladdVacationAddTime();
	}
	
	/** 月別集計用の時間休暇加算時間を取得する */
	private int getTimeVacationAddTime(RequireM2 require, String cid, IntegrationOfDaily daily, HolidayAddtionSet addSet) {
		
		/** 相殺分時間休暇WORKを作る */
		val offsetTimeWork = TimeVacationWork.createForMonthlyAggregate(daily);
		
		/** 相殺分時間休暇WORKから就業時間に加算する時間のみ取得 */
		val offsetTime = offsetTimeWork.getValueForAddWorkTime(addSet);
		
		/** 時間休暇WORKを作る */
		val useTimeWork = TimeVacationWork.create(daily);
		
		/** 時間休暇WORKから就業時間に加算する時間のみ取得 */
		val useTime = useTimeWork.getValueForAddWorkTime(addSet);
		
		/** 加算する時間休暇Workを判断する */
		val timeVacation = addSet.decideTimeVacationWork(require, cid, useTime, offsetTime, daily.getWorkInformation().getRecordInfo());
		
		/** 加算時間を集計する */
		return timeVacation.total().valueAsMinutes();
	}
	
	/** 割増の集計用の加算設定に変換する */
	public static WorkTimeAddTimeSetForMonthAggr forPremium(AddSettingOfWorkingTime addSet) {
		
		val addSetMonth = new WorkTimeAddTimeSetForMonthAggr();
		
		/** 割増計算方法を設定する */
		if (addSet.getUseAtr() == NotUseAtr.USE) {
			
			val premium = addSet.getAddSetOfPremium();
			
			/** 集計用の加算設定を作成する */
			addSetMonth.addSetUse = premium.getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME;
			addSetMonth.addShortTime = premium.getTreatDeduct().map(c -> c.getCalculateIncludCareTime() == NotUseAtr.USE).orElse(false);
			addSetMonth.addInterval = premium.getTreatDeduct().map(c -> c.getCalculateIncludIntervalExemptionTime() == NotUseAtr.USE).orElse(false);
			addSetMonth.lateEarlySet = premium.getTreatDeduct().map(c -> c.getTreatLateEarlyTimeSet()).orElseGet(() -> TreatLateEarlyTimeSetUnit.createNotInclude());
			addSetMonth.vacationSet = premium.getTreatVacation().orElseGet(() -> new TreatVacationTimeForCalcPremium());
		} else {
			val worktime = addSet.getAddSetOfWorkTime();

			/** 集計用の加算設定を作成する */
			addSetMonth.addSetUse = worktime.getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME;
			addSetMonth.addShortTime = worktime.getTreatDeduct().map(c -> c.getCalculateIncludCareTime() == NotUseAtr.USE).orElse(false);
			addSetMonth.addInterval = worktime.getTreatDeduct().map(c -> c.getCalculateIncludIntervalExemptionTime() == NotUseAtr.USE).orElse(false);
			addSetMonth.lateEarlySet = worktime.getTreatDeduct().map(c -> c.getTreatLateEarlyTimeSet()).orElseGet(() -> TreatLateEarlyTimeSetUnit.createNotInclude());
			addSetMonth.addAbsence = false;
			addSetMonth.vacationSet = worktime.getTreatVacation().map(c -> new TreatVacationTimeForCalcPremium(
					c.getAddition(), 
					c.getDeformationExceedsPredeterminedValue(), 
					Optional.empty())).orElseGet(() -> new TreatVacationTimeForCalcPremium());
		}
		addSetMonth.addAbsence = false;
		addSetMonth.premiumFlag = true;
		addSetMonth.rawSet = addSet;
		
		/** 集計用の加算設定を返す */
		return addSetMonth;
	}
	
	/** 就業の集計用の加算設定に変換する */
	public static WorkTimeAddTimeSetForMonthAggr forWorkTime(AddSettingOfWorkingTime addSet) {
		
		val addSetMonth = new WorkTimeAddTimeSetForMonthAggr();
		val worktime = addSet.getAddSetOfWorkTime();

		/** 集計用の加算設定を作成する */
		addSetMonth.addSetUse = worktime.getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME;
		addSetMonth.addShortTime = worktime.getTreatDeduct().map(c -> c.getCalculateIncludCareTime() == NotUseAtr.USE).orElse(false);
		addSetMonth.addInterval = worktime.getTreatDeduct().map(c -> c.getCalculateIncludIntervalExemptionTime() == NotUseAtr.USE).orElse(false);
		addSetMonth.lateEarlySet = worktime.getTreatDeduct().map(c -> c.getTreatLateEarlyTimeSet()).orElseGet(() -> TreatLateEarlyTimeSetUnit.createNotInclude());
		addSetMonth.addAbsence = worktime.getTreatVacation().flatMap(c -> c.getMinusAbsenceTime()).map(c -> c == NotUseAtr.USE).orElse(false);
		addSetMonth.vacationSet = worktime.getTreatVacation().orElseGet(() -> new TreatVacationTimeForCalcWorkTime());
		addSetMonth.addAbsence = false;
		addSetMonth.premiumFlag = false;
		addSetMonth.rawSet = addSet;
		
		/** 集計用の加算設定を返す */
		return addSetMonth;
	}

	public static interface RequireM4 extends IntegrationOfWorkTime.RequireM2 {
		
	}
	
	public static interface RequireM3 extends RefDesForAdditionalTakeLeave.Require {
		
		Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode);
	}
	
	public static interface RequireM2 extends HolidayAddtionSet.Require {
		
	}
	
	public static interface RequireM1 extends RequireM2, RequireM3 {
		
	}
	
	public static interface Require extends RequireM1, RequireM4 {
		
	}
	
}
