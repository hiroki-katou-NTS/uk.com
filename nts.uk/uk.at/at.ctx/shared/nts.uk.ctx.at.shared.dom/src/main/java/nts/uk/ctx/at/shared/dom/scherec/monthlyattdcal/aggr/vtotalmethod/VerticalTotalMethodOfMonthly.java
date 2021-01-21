package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績の縦計方法
 * @author shuichu_ishida
 */
@Getter
@Setter
@AllArgsConstructor
public class VerticalTotalMethodOfMonthly extends AggregateRoot {

	/** 会社ID */
	private String companyId;
	/** 振出日数 */
	private TADaysCountOfMonthlyAggr transferAttendanceDays;
	/** 特定日 */
	private SpecTotalCountMonthly specTotalCountMonthly;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public VerticalTotalMethodOfMonthly(String companyId){
		
		super();
		this.companyId = companyId;
		this.transferAttendanceDays = new TADaysCountOfMonthlyAggr();
		this.specTotalCountMonthly = new SpecTotalCountMonthly();
	}
	
	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param transferAttendanceDays 振出日数
	 * @return 月別実績の縦計方法
	 */
	public static VerticalTotalMethodOfMonthly of(String companyId,
			TADaysCountOfMonthlyAggr transferAttendanceDays,
			SpecTotalCountMonthly specTotalCountMonthly){

		VerticalTotalMethodOfMonthly domain = new VerticalTotalMethodOfMonthly(companyId);
		domain.transferAttendanceDays = transferAttendanceDays;
		domain.specTotalCountMonthly = specTotalCountMonthly;
		return domain;
	}
	
	/** 特定日を計算する勤務種類かどうか判断 */
	public boolean isCalcThisWorkTypeAsSpecDays(WorkingSystem workingSystem, WorkType workType,
			WorkTypeDaysCountTable workTypeDaysCountTable) {
		
		/** パラメータ「労働制」を判断 */
		if (workingSystem == WorkingSystem.EXCLUDED_WORKING_CALCULATE) {
			if (this.specTotalCountMonthly.getSpecCount() == SpecCountNotCalcSubject.NoUncondition) {
				return false;
			}
			
			return true;
			
		} else {
			/** パラメータ「勤務種類」を取得 -> 連続勤務のチェック */
			if (workType.getDailyWork().isContinueWork()) {
				/** 属性「連続勤務の日でもカウントする」を取得 */
				return this.specTotalCountMonthly.isContinuousCount();
				
			} else {
				/** 属性「勤務日ではない日でもカウントする」を取得 */
				if (this.specTotalCountMonthly.isNotWorkCount()) {
					return true;
					
				} else {
					/** 「勤務日数」のカウント方法を判断 */
					if (workTypeDaysCountTable.getWorkDays().v() > 0) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
