package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonthDom;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;

/**
 * 月別実績の振出日数
 * @author shuichu_ishida
 */
@Getter
public class RecruitmentDaysOfMonthly {

	/** 日数 */
	private AttendanceDaysMonthDom days;
	
	/**
	 * コンストラクタ
	 */
	public RecruitmentDaysOfMonthly(){
		
		this.days = new AttendanceDaysMonthDom(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @return 月別実績の振出日数
	 */
	public static RecruitmentDaysOfMonthly of(AttendanceDaysMonthDom days){
		
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
