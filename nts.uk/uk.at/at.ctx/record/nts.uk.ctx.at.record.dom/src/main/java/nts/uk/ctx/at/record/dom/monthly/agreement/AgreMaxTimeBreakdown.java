package nts.uk.ctx.at.record.dom.monthly.agreement;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.AttendanceItemOfMonthly;

/**
 * 36協定上限時間内訳
 * @author shuichi_ishida
 */
@Getter
public class AgreMaxTimeBreakdown extends AgreementTimeBreakdown {

	/** 法定内休出時間 */
	private AttendanceTimeMonth legalHolidayWorkTime;
	/** 法定内振替時間 */
	private AttendanceTimeMonth legalTransferTime;

	/**
	 * コンストラクタ
	 */
	public AgreMaxTimeBreakdown(){
		super();
		this.legalHolidayWorkTime = new AttendanceTimeMonth(0);
		this.legalTransferTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param overTime 残業時間
	 * @param transferOverTime 振替残業時間
	 * @param holidayWorkTime 休出時間
	 * @param transferTime 振替時間
	 * @param flexLegalTime フレックス法定内時間
	 * @param flexIllegalTime フレックス法定外時間
	 * @param withinPrescribedPremiumTime 所定内割増時間
	 * @param weeklyPremiumTime 週割増時間
	 * @param monthlyPremiumTime 月割増時間
	 * @param legalHolidayWorkTime 法定内休出時間
	 * @param legalTransferTime 法定内振替時間
	 * @return 36協定上限時間内訳
	 */
	public static AgreMaxTimeBreakdown of(
			AttendanceTimeMonth overTime,
			AttendanceTimeMonth transferOverTime,
			AttendanceTimeMonth holidayWorkTime,
			AttendanceTimeMonth transferTime,
			AttendanceTimeMonth flexLegalTime,
			AttendanceTimeMonth flexIllegalTime,
			AttendanceTimeMonth withinPrescribedPremiumTime,
			AttendanceTimeMonth weeklyPremiumTime,
			AttendanceTimeMonth monthlyPremiumTime,
			AttendanceTimeMonth legalHolidayWorkTime,
			AttendanceTimeMonth legalTransferTime){

		AgreMaxTimeBreakdown domain = new AgreMaxTimeBreakdown();
		domain.overTime = overTime;
		domain.transferOverTime = transferOverTime;
		domain.holidayWorkTime = holidayWorkTime;
		domain.transferTime = transferTime;
		domain.flexLegalTime = flexLegalTime;
		domain.flexIllegalTime = flexIllegalTime;
		domain.withinPrescribedPremiumTime = withinPrescribedPremiumTime;
		domain.weeklyPremiumTime = weeklyPremiumTime;
		domain.monthlyPremiumTime = monthlyPremiumTime;
		domain.legalHolidayWorkTime = legalHolidayWorkTime;
		domain.legalTransferTime = legalTransferTime;
		return domain;
	}
	
	/**
	 * 36協定上限時間の対象項目を取得
	 * @param aggregateAtr 集計区分
	 * @param monthlyCalculation 月別実績の月の計算
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void getTargetItemOfAgreMax(
			MonthlyAggregateAtr aggregateAtr,
			MonthlyCalculation monthlyCalculation,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 集計結果　初期化　（自クラス分）
		this.legalHolidayWorkTime = new AttendanceTimeMonth(0);
		this.legalTransferTime = new AttendanceTimeMonth(0);

		// 36協定時間の対象時間を取得
		this.getTargetItemOfAgreement(aggregateAtr, monthlyCalculation, repositories);
		
		// 丸め設定取得
		RoundingSetOfMonthly roundingSet = monthlyCalculation.getCompanySets().getRoundingSet();
		
		// 法定内休出の勤怠項目IDを全て取得
		val companySets = monthlyCalculation.getCompanySets();
		List<Integer> attendanceItemIds = repositories.getOutsideOTSetService().getAllAttendanceItemIdsForLegalBreak(
				monthlyCalculation.getCompanyId(),
				Optional.of(companySets.getOutsideOverTimeSet()),
				Optional.of(companySets.getWorkDayoffFrameList()));
		for (val attendanceItemId : attendanceItemIds){
			
			// 対象項目の時間を取得　と　丸め処理
			val targetItemTime = monthlyCalculation.getTimeOfAttendanceItemId(attendanceItemId, roundingSet, true);
			
			// 勤怠項目IDに対応する時間を加算する　（法定内時間用）
			this.addTimeByAttendanceItemIdForLegal(attendanceItemId, targetItemTime);
		}
	}
	
	/**
	 * 勤怠項目IDに対応する時間を加算する　（法定内時間用）
	 * @param attendanceItemId 勤怠項目ID
	 * @param targetItemTime 対象項目の時間
	 */
	private void addTimeByAttendanceItemIdForLegal(int attendanceItemId, AttendanceTimeMonth targetItemTime){
		
		// 休出時間
		if (attendanceItemId >= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_10.value){
			this.legalHolidayWorkTime = this.legalHolidayWorkTime.addMinutes(targetItemTime.v());
		}
		
		// 計算休出時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_10.value){
			this.legalHolidayWorkTime = this.legalHolidayWorkTime.addMinutes(targetItemTime.v());
		}
		
		// 振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_TIME_10.value){
			this.legalTransferTime = this.legalTransferTime.addMinutes(targetItemTime.v());
		}
		
		// 計算振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_10.value){
			this.legalTransferTime = this.legalTransferTime.addMinutes(targetItemTime.v());
		}
	}
	
	/**
	 * 合計時間を取得
	 * @return 合計時間
	 */
	public AttendanceTimeMonth getTotalTime(){
		
		AttendanceTimeMonth totalTime = new AttendanceTimeMonth(0);
		totalTime = totalTime.addMinutes(this.overTime.v());
		totalTime = totalTime.addMinutes(this.transferOverTime.v());
		totalTime = totalTime.addMinutes(this.holidayWorkTime.v());
		totalTime = totalTime.addMinutes(this.transferTime.v());
		totalTime = totalTime.addMinutes(this.flexLegalTime.v());
		totalTime = totalTime.addMinutes(this.flexIllegalTime.v());
		totalTime = totalTime.addMinutes(this.withinPrescribedPremiumTime.v());
		totalTime = totalTime.addMinutes(this.weeklyPremiumTime.v());
		totalTime = totalTime.addMinutes(this.monthlyPremiumTime.v());
		totalTime = totalTime.addMinutes(this.legalHolidayWorkTime.v());
		totalTime = totalTime.addMinutes(this.legalTransferTime.v());
		return totalTime;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AgreMaxTimeBreakdown target){
		this.overTime = this.overTime.addMinutes(target.overTime.v());
		this.transferOverTime = this.transferOverTime.addMinutes(target.transferOverTime.v());
		this.holidayWorkTime = this.holidayWorkTime.addMinutes(target.holidayWorkTime.v());
		this.transferTime = this.transferTime.addMinutes(target.transferTime.v());
		this.flexLegalTime = this.flexLegalTime.addMinutes(target.flexLegalTime.v());
		this.flexIllegalTime = this.flexIllegalTime.addMinutes(target.flexIllegalTime.v());
		this.withinPrescribedPremiumTime = this.withinPrescribedPremiumTime.addMinutes(target.withinPrescribedPremiumTime.v());
		this.weeklyPremiumTime = this.weeklyPremiumTime.addMinutes(target.weeklyPremiumTime.v());
		this.monthlyPremiumTime = this.monthlyPremiumTime.addMinutes(target.monthlyPremiumTime.v());
		this.legalHolidayWorkTime = this.legalHolidayWorkTime.addMinutes(target.legalHolidayWorkTime.v());
		this.legalTransferTime = this.legalTransferTime.addMinutes(target.legalTransferTime.v());
	}
}
