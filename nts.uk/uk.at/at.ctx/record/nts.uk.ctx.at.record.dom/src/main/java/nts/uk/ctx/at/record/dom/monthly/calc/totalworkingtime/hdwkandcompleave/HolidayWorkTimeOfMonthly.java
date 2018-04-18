package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalHolidayWorkTransferOrderOfAggrMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.ExcessOutsideTimeSet;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.TreatHolidayWorkTimeOfLessThanCriteriaPerWeek;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.HolidayWorkAndTransferAtr;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の休出時間
 * @author shuichi_ishida
 */
@Getter
public class HolidayWorkTimeOfMonthly {

	/** 休出合計時間 */
	private TimeMonthWithCalculation totalHolidayWorkTime;
	/** 事前休出時間 */
	private AttendanceTimeMonth beforeHolidayWorkTime;
	/** 振替合計時間 */
	private TimeMonthWithCalculation totalTransferTime;
	/** 集計休出時間 */
	private Map<HolidayWorkFrameNo, AggregateHolidayWorkTime> aggregateHolidayWorkTimeMap;

	/**
	 * コンストラクタ
	 */
	public HolidayWorkTimeOfMonthly(){
		
		this.totalHolidayWorkTime = TimeMonthWithCalculation.ofSameTime(0);
		this.beforeHolidayWorkTime = new AttendanceTimeMonth(0);
		this.totalTransferTime = TimeMonthWithCalculation.ofSameTime(0);
		
		this.aggregateHolidayWorkTimeMap = new HashMap<>();
	}

	/**
	 * ファクトリー
	 * @param totalHolidayWorkTime 休出合計時間
	 * @param beforeHolidayWorkTime 事前休出時間
	 * @param totalTransferTime 振替合計時間
	 * @param aggregateHolidayWorkTimeList 集計休出時間
	 * @return 月別実績の休出時間
	 */
	public static HolidayWorkTimeOfMonthly of(
			TimeMonthWithCalculation totalHolidayWorkTime,
			AttendanceTimeMonth beforeHolidayWorkTime,
			TimeMonthWithCalculation totalTransferTime,
			List<AggregateHolidayWorkTime> aggregateHolidayWorkTimeList){

		val domain = new HolidayWorkTimeOfMonthly();
		domain.totalHolidayWorkTime = totalHolidayWorkTime;
		domain.beforeHolidayWorkTime = beforeHolidayWorkTime;
		domain.totalTransferTime = totalTransferTime;
		for (AggregateHolidayWorkTime aggregateHolidayWorkTime : aggregateHolidayWorkTimeList){
			val holidayWorkFrameNo = aggregateHolidayWorkTime.getHolidayWorkFrameNo();
			domain.aggregateHolidayWorkTimeMap.putIfAbsent(holidayWorkFrameNo, aggregateHolidayWorkTime);
		}
		return domain;
	}
	
	/**
	 * 対象の集計休出時間を取得する
	 * @param holidayWorkFrameNo 休出枠NO
	 * @return 対象の集計休出時間
	 */
	private AggregateHolidayWorkTime getTargetAggregateHolidayWorkTime(HolidayWorkFrameNo holidayWorkFrameNo){

		this.aggregateHolidayWorkTimeMap.putIfAbsent(holidayWorkFrameNo, new AggregateHolidayWorkTime(holidayWorkFrameNo));
		return this.aggregateHolidayWorkTimeMap.get(holidayWorkFrameNo);
	}
	
	/**
	 * 集計する　（通常・変形労働時間勤務用）
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param employmentCd 雇用コード
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param workInfo 勤務情報
	 * @param legalHolidayWorkTransferOrder 法定内休出振替順
	 * @param excessOutsideTimeSet 時間外超過設定
	 * @param treatHolidayWorkTimeOfLessThanCriteriaPerWeek 1週間の基準時間未満の休日出勤時間の扱い
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregateForRegAndIrreg(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily,
			String companyId, String workplaceId, String employmentCd, WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr, WorkInformation workInfo,
			LegalHolidayWorkTransferOrderOfAggrMonthly legalHolidayWorkTransferOrder,
			ExcessOutsideTimeSet excessOutsideTimeSet,
			TreatHolidayWorkTimeOfLessThanCriteriaPerWeek treatHolidayWorkTimeOfLessThanCriteriaPerWeek,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 集計区分を確認する
		boolean isAggregateHolidayWork = false;
		if (aggregateAtr == MonthlyAggregateAtr.MONTHLY) isAggregateHolidayWork = true;
		if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
			
			// 休出を集計するか確認する
			this.confirmAggregateHolidayWork(companyId, workInfo, excessOutsideTimeSet, repositories);
		}
		if (isAggregateHolidayWork){
			
			// 自動的に除く休出枠を確認する
			val autoExcludeHolidayWorkFrames = treatHolidayWorkTimeOfLessThanCriteriaPerWeek.getAutoExcludeHolidayWorkFrames();
			if (autoExcludeHolidayWorkFrames.isEmpty()) {
				// 0件なら、自動計算せず休出時間を集計する
				this.aggregateWithoutAutoCalc(attendanceTimeOfDaily);
			}
			else {
				// 1件以上なら、自動計算して休出時間を集計する
				this.aggregateByAutoCalc(attendanceTimeOfDaily, companyId, workplaceId, employmentCd, workingSystem,
						workInfo, legalHolidayWorkTransferOrder, autoExcludeHolidayWorkFrames, repositories);
			}
		}
	}

	/**
	 * 休出を集計するか確認する
	 * @param companyId 会社ID
	 * @param workInfo 勤務情報
	 * @param excessOutsideTimeSet 時間外超過設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return true：休出を集計する、false：休出を集計しない
	 */
	private boolean confirmAggregateHolidayWork(
			String companyId,
			WorkInformation workInfo,
			ExcessOutsideTimeSet excessOutsideTimeSet,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 「勤務種類が法内休出の休出時間は時間外超過対象から自動的に除く」でない時、休出を集計する
		if (!excessOutsideTimeSet.isAutoExcludeHolidayWorkTimeFromExcessOutsideWorkTime()) return true;
		
		// 勤務種類から法定内休日か判断する
		val workType = repositories.getWorkType().findByPK(companyId, workInfo.getWorkTypeCode().v());
		if (workType.isPresent()){
			val workTypeSet = workType.get().getWorkTypeSet();
			if (workTypeSet != null){
				val holidayAtr = workTypeSet.getHolidayAtr();
				// 法定内休日なら、休出を集計しない
				if (holidayAtr == HolidayAtr.STATUTORY_HOLIDAYS) return false;
			}
		}
		
		// 法定内休日以外なら、休出を集計する
		return true;
	}
	
	/**
	 * 自動計算して集計する
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param placeId 職場ID
	 * @param employmentCd 雇用コード
	 * @param workingSystem 労働制
	 * @param workInfo 勤務情報
	 * @param legalHolidayWorkTransferOrder 法定内休出振替順
	 * @param autoExcludeHolidayWorkFrames 自動的に除く休出枠
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void aggregateByAutoCalc(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily,
			String companyId, String placeId, String employmentCd, WorkingSystem workingSystem,
			WorkInformation workInfo,
			LegalHolidayWorkTransferOrderOfAggrMonthly legalHolidayWorkTransferOrder,
			List<HolidayWorkFrameNo> autoExcludeHolidayWorkFrames,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 法定内休出にできる時間を計算する
		AttendanceTime canLegalHolidayWork = this.calcLegalHolidayWork(attendanceTimeOfDaily,
				companyId, placeId, employmentCd, workingSystem, repositories);
		
		// 「休出枠時間」を取得する
		val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
		val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
		val excessOfStatutoryTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		val holidayWorkTimeOfDaily = excessOfStatutoryTimeOfDaily.getWorkHolidayTime();
		// 休出時間がない時、集計しない
		if (!holidayWorkTimeOfDaily.isPresent()) return;
		val holidayWorkFrameTimeSrcs = holidayWorkTimeOfDaily.get().getHolidayWorkFrameTime();
			
		// 休出枠時間リストをマップに組み換え　（枠での検索用）
		Map<HolidayWorkFrameNo, HolidayWorkFrameTime> holidayWorkFrameTimes = new HashMap<>();
		for (val holidayWorkFrameTimeSrc : holidayWorkFrameTimeSrcs){
			holidayWorkFrameTimes.putIfAbsent(holidayWorkFrameTimeSrc.getHolidayFrameNo(), holidayWorkFrameTimeSrc);
		}
	
		// 休出・振替の処理順序を取得する
		if (workInfo.getWorkTimeCode() == null) return;
		val workTimeCode = workInfo.getWorkTimeCode().v();
		val holidayWorkAndTransferAtrs = repositories.getHolidayWorkAndTransferOrder().get(
				companyId, workTimeCode, false);
		
		// 休出・振替のループ
		for (val holidayWorkAndTransferAtr : holidayWorkAndTransferAtrs){
		
			// 休出枠時間のループ処理
			canLegalHolidayWork = this.holidayWorkFrameTimeProcess(holidayWorkAndTransferAtr,
					legalHolidayWorkTransferOrder, canLegalHolidayWork,
					autoExcludeHolidayWorkFrames, holidayWorkFrameTimes, attendanceTimeOfDaily.getYmd());
		}
	}
	
	/**
	 * 自動計算せず集計する
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	private void aggregateWithoutAutoCalc(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily){
		
		// 「休出枠時間」を取得する
		val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
		val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
		val excessOfStatutoryTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		val holidayWorkTimeOfDaily = excessOfStatutoryTimeOfDaily.getWorkHolidayTime();
		// 休出時間がない時、集計しない
		if (!holidayWorkTimeOfDaily.isPresent()) return;
		val holidayWorkFrameTimeSrcs = holidayWorkTimeOfDaily.get().getHolidayWorkFrameTime();
			
		// 取得した休出枠時間を「集計休出時間」に入れる
		for (val holidayWorkFrameTimeSrc : holidayWorkFrameTimeSrcs){
			val holidayWorkFrameNo = holidayWorkFrameTimeSrc.getHolidayFrameNo();
			val targetAggregateHolidayWorkTime = this.getTargetAggregateHolidayWorkTime(holidayWorkFrameNo);
			val ymd = attendanceTimeOfDaily.getYmd();
			targetAggregateHolidayWorkTime.addHolidayWorkTimeInTimeSeriesWork(ymd, holidayWorkFrameTimeSrc);
		}
	}
	
	/**
	 * 法定内休出に出来る時間を計算する
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param placeId 職場ID
	 * @param employmentCd 雇用コード
	 * @param workingSystem 労働制
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private AttendanceTime calcLegalHolidayWork(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily,
			String companyId, String placeId, String employmentCd, WorkingSystem workingSystem,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 日の法定労働時間を取得する
		//*****（未）　正式な処理の作成待ち。
		//DailyCalculationPersonalInformation dailyCalculationPersonalInformation =
		//		repositories.getGetOfStatutoryWorkTime().getDailyTimeFromStaturoyWorkTime(
		//			workingSystem,
		//			companyId,
		//			placeId,
		//			employmentCd,
		//			attendanceTimeOfDaily.getEmployeeId(),
		//			attendanceTimeOfDaily.getYmd());
		
		// 法定内休出にできる時間
		//*****（未）　正式な処理が出来てから、代入。
		AttendanceTime canLegalHolidayWork = new AttendanceTime(8 * 60);
		//		new AttendanceTime(dailyCalculationPersonalInformation.getStatutoryWorkTime().v());
		return canLegalHolidayWork;
	}
	
	/**
	 * 休出枠時間のループ処理
	 * @param holidayWorkAndTransferAtr 休出振替区分
	 * @param legalHolidayWorkTransferOrderOfAggrMonthly 法定内休出振替順
	 * @param canLegalHolidayWork 法定内休出に出来る時間
	 * @param autoExcludeHolidayWorkFrameList 自動的に除く休出枠
	 * @param holidayWorkFrameTimeMap 休出枠時間　（日別実績より取得）
	 * @param ymd 年月日
	 * @return 法定内休出に出来る時間　（計算後）
	 */
	private AttendanceTime holidayWorkFrameTimeProcess(
			HolidayWorkAndTransferAtr holidayWorkAndTransferAtr,
			LegalHolidayWorkTransferOrderOfAggrMonthly legalHolidayWorkTransferOrderOfAggrMonthly,
			AttendanceTime canLegalHolidayWork,
			List<HolidayWorkFrameNo> autoExcludeHolidayWorkFrameList,
			Map<HolidayWorkFrameNo, HolidayWorkFrameTime> holidayWorkFrameTimeMap,
			GeneralDate ymd){
		
		AttendanceTime timeAfterCalc = canLegalHolidayWork;
		
		// 休出枠時間分ループ
		for (val legalHolidayWorkTransferOrder : legalHolidayWorkTransferOrderOfAggrMonthly.getLegalHolidayWorkTransferOrders()){
			val holidayWorkFrameNo = legalHolidayWorkTransferOrder.getHolidayWorkFrameNo();
			
			// 該当する（日別実績の）休出枠時間がなければ、次の枠へ
			if (!holidayWorkFrameTimeMap.containsKey(holidayWorkFrameNo)) continue;
			val holidayWorkFrameTime = holidayWorkFrameTimeMap.get(holidayWorkFrameNo);
			
			// 対象の時系列ワークを確認する
			val targetHolidayWorkTime = this.getTargetAggregateHolidayWorkTime(holidayWorkFrameNo);
			val timeSeriesWork = targetHolidayWorkTime.getAndPutTimeSeriesWork(ymd);
			
			// 自動的に除く休出枠か確認する
			if (autoExcludeHolidayWorkFrameList.contains(holidayWorkFrameNo)){
				
				// 取得した休出枠時間を集計休出時間に入れる　（入れた時間分を法定内休出にできる時間から引く）
				switch (holidayWorkAndTransferAtr){
				case HOLIDAY_WORK:
					AttendanceTime legalHolidayWorkTime =
						new AttendanceTime(holidayWorkFrameTime.getHolidayWorkTime().get().getTime().v());
					AttendanceTime holidayWorkTime = new AttendanceTime(0);
					if (legalHolidayWorkTime.lessThanOrEqualTo(timeAfterCalc.v())){
						// 休出時間が法定内休出にできる時間以下の時
						timeAfterCalc = timeAfterCalc.minusMinutes(legalHolidayWorkTime.v());
					}
					else {
						// 休出時間が法定内休出にできる時間を超える時
						holidayWorkTime = new AttendanceTime(legalHolidayWorkTime.v());
						holidayWorkTime = holidayWorkTime.minusMinutes(timeAfterCalc.v());
						legalHolidayWorkTime = new AttendanceTime(timeAfterCalc.v());
						timeAfterCalc = new AttendanceTime(0);
					}
					timeSeriesWork.addHolidayWorkTimeInLegalHolidayWorkTime(TimeDivergenceWithCalculation.createTimeWithCalculation(
							legalHolidayWorkTime, new AttendanceTime(0)));
					timeSeriesWork.addHolidayWorkTimeInHolidayWorkTime(TimeDivergenceWithCalculation.createTimeWithCalculation(
							holidayWorkTime, new AttendanceTime(0)));
					break;
				case TRANSFER:
					AttendanceTime legalTransferTimeWork =
						new AttendanceTime(holidayWorkFrameTime.getTransferTime().get().getTime().v());
					AttendanceTime transferTimeWork = new AttendanceTime(0);
					if (legalTransferTimeWork.lessThanOrEqualTo(timeAfterCalc.v())){
						// 振替時間が法定内休出にできる時間以下の時
						timeAfterCalc = timeAfterCalc.minusMinutes(legalTransferTimeWork.v());
					}
					else {
						// 振替時間が法定内休出にできる時間を超える時
						transferTimeWork = new AttendanceTime(legalTransferTimeWork.v());
						transferTimeWork = transferTimeWork.minusMinutes(timeAfterCalc.v());
						legalTransferTimeWork = new AttendanceTime(timeAfterCalc.v());
						timeAfterCalc = new AttendanceTime(0);
					}
					timeSeriesWork.addTransferTimeInLegalHolidayWorkTime(TimeDivergenceWithCalculation.createTimeWithCalculation(
							legalTransferTimeWork, new AttendanceTime(0)));
					timeSeriesWork.addTransferTimeInHolidayWorkTime(TimeDivergenceWithCalculation.createTimeWithCalculation(
							transferTimeWork, new AttendanceTime(0)));
					break;
				}
			}
			else {
				
				// 取得した休出枠時間を集計休出時間に入れる
				switch (holidayWorkAndTransferAtr){
				case HOLIDAY_WORK:
					timeSeriesWork.addHolidayWorkTimeInHolidayWorkTime(holidayWorkFrameTime.getHolidayWorkTime().get());
					break;
				case TRANSFER:
					timeSeriesWork.addTransferTimeInHolidayWorkTime(holidayWorkFrameTime.getTransferTime().get());
					break;
				}
			}
		}
		
		return timeAfterCalc;
	}
	
	/**
	 * 集計する　（フレックス時間勤務用）
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param aggregateAtr 集計区分
	 * @param aggrSetOfFlex フレックス時間勤務の月の集計設定
	 */
	public void aggregateForFlex(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily,
			String companyId, MonthlyAggregateAtr aggregateAtr, AggrSettingMonthlyOfFlx aggrSetOfFlex){
		
		// 「休出枠時間」を取得する
		val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
		val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
		val excessOfStatutoryTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		val holidayWorkTimeOfDaily = excessOfStatutoryTimeOfDaily.getWorkHolidayTime();
		// 休出時間がない時、集計しない
		if (!holidayWorkTimeOfDaily.isPresent()) return;
		val holidayWorkFrameTimeSrcs = holidayWorkTimeOfDaily.get().getHolidayWorkFrameTime();
		
		val ymd = attendanceTimeOfDaily.getYmd();
		
		// 取得した休出枠時間を「集計休出時間」に入れる
		for (val holidayWorkFrameTimeSrc : holidayWorkFrameTimeSrcs){
			val holidayWorkFrameNo = holidayWorkFrameTimeSrc.getHolidayFrameNo();
			val targetHolidayWorkTime = this.getTargetAggregateHolidayWorkTime(holidayWorkFrameNo);
			targetHolidayWorkTime.addHolidayWorkTimeInTimeSeriesWork(ymd, holidayWorkFrameTimeSrc);
		}
	}
	
	/**
	 * 法定内休出時間を取得する
	 * @param datePeriod 期間
	 * @return 法定内休出時間
	 */
	public AttendanceTimeMonth getLegalHolidayWorkTime(DatePeriod datePeriod){

		AttendanceTimeMonth returnTime = new AttendanceTimeMonth(0);

		// 法定内休出時間．休出時間　＋　法定内休出時間．振替時間　の合計
		for (val aggregateHolidayWorkTime : this.aggregateHolidayWorkTimeMap.values()){
			for (val timeSeriesWork : aggregateHolidayWorkTime.getTimeSeriesWorks().values()){
				if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
				val legalHolidayWorkTime = timeSeriesWork.getLegalHolidayWorkTime();
				returnTime = returnTime.addMinutes(legalHolidayWorkTime.getHolidayWorkTime().get().getTime().v());
				returnTime = returnTime.addMinutes(legalHolidayWorkTime.getTransferTime().get().getTime().v());
			}
		}
		
		return returnTime;
	}
	
	/**
	 * 休出合計時間を集計する
	 * @param datePeriod 期間
	 */
	public void aggregateTotal(DatePeriod datePeriod){

		this.totalHolidayWorkTime = TimeMonthWithCalculation.ofSameTime(0);
		this.beforeHolidayWorkTime = new AttendanceTimeMonth(0);
		this.totalTransferTime = TimeMonthWithCalculation.ofSameTime(0);

		for (val aggregateHolidayWorkTime : this.aggregateHolidayWorkTimeMap.values()){
			aggregateHolidayWorkTime.aggregate(datePeriod);
			this.totalHolidayWorkTime = this.totalHolidayWorkTime.addMinutes(
					aggregateHolidayWorkTime.getHolidayWorkTime().getTime().v(),
					aggregateHolidayWorkTime.getHolidayWorkTime().getCalcTime().v());
			this.beforeHolidayWorkTime = this.beforeHolidayWorkTime.addMinutes(
					aggregateHolidayWorkTime.getBeforeHolidayWorkTime().v());
			this.totalTransferTime = this.totalTransferTime.addMinutes(
					aggregateHolidayWorkTime.getTransferTime().getTime().v(),
					aggregateHolidayWorkTime.getTransferTime().getCalcTime().v());
		}
	}
	
	/**
	 * 総労働対象時間の取得
	 * @return 総労働対象時間
	 */
	public AttendanceTimeMonth getTotalWorkingTargetTime(){
		
		return new AttendanceTimeMonth(this.totalHolidayWorkTime.getTime().v() +
				this.totalTransferTime.getTime().v());
	}
}
