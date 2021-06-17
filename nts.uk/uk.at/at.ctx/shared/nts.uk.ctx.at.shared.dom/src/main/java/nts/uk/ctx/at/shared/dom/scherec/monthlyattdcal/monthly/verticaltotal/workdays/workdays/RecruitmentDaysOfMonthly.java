package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.workdays;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountCondOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.context.AppContexts;

/**
 * 月別実績の振出日数
 * @author shuichi_ishida
 */
@Getter
public class RecruitmentDaysOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 */
	public RecruitmentDaysOfMonthly(){
		
		this.days = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @return 月別実績の振出日数
	 */
	public static RecruitmentDaysOfMonthly of(AttendanceDaysMonth days){
		
		RecruitmentDaysOfMonthly domain = new RecruitmentDaysOfMonthly();
		domain.days = days;
		return domain;
	}
	
	/**
	 * 集計
	 * @param workingSystem 労働制
	 * @param workTypeDaysCountTable 勤務種類の日数カウント表
	 * @param isAttendanceDay 出勤しているかどうか
	 */
	public void aggregate(RequireM1 require, WorkingSystem workingSystem, 
			WorkTypeDaysCountTable workTypeDaysCountTable, boolean isAttendanceDay){

		if (workTypeDaysCountTable == null) return;
		
		// 労働制を取得
		if (workingSystem != WorkingSystem.EXCLUDED_WORKING_CALCULATE) {/** 「就業計算対象外」以外 */

			/** 振出日数カウント条件を判断 */
			val isCountAttendanceDayOnly = require.aggregateMethodOfMonthly(AppContexts.user().companyId())
					.map(c -> c.getTransferAttendanceDays().getTADaysCountCondition() == TADaysCountCondOfMonthlyAggr.ONLY_DAY_OF_WORK)
					.orElse(false);
			
			/** ○出勤状態を判断する */
			if (isCountAttendanceDayOnly && !isAttendanceDay)
				return;
		}
		
		/** ○「振出日数」に加算 */
		this.days = this.days.addDays(workTypeDaysCountTable.getTransferAttendanceDays().v());
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(RecruitmentDaysOfMonthly target){
		
		this.days = this.days.addDays(target.days.v());
	}
	
	public static interface RequireM1 {

		Optional<AggregateMethodOfMonthly> aggregateMethodOfMonthly(String cid);
	}
}
