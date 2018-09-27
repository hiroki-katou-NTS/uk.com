package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;

/**
 * 月別実績の振出日数
 * @author shuichu_ishida
 */
@Getter
public class RecruitmentDaysOfMonthly {

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
	 * @param workTypeDaysCountTable 勤務種類の日数カウント表
	 */
	public void aggregate(WorkTypeDaysCountTable workTypeDaysCountTable){

		if (workTypeDaysCountTable == null) return;
		
		// 振出日数に加算する
		this.days = this.days.addDays(workTypeDaysCountTable.getTransferAttendanceDays().v());
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(RecruitmentDaysOfMonthly target){
		
		this.days = this.days.addDays(target.days.v());
	}
}
