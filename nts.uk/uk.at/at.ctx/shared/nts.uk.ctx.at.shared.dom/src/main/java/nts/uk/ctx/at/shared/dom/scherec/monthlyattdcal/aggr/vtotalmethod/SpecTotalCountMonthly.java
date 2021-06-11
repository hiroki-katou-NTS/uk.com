package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績集計の特定日カウント
 * @author HoangNDH
 *
 */
@Data
@NoArgsConstructor
public class SpecTotalCountMonthly {
	
	// 連続勤務の日でもカウントする
	private boolean continuousCount;
	// 勤務日ではない日でもカウントする
	private boolean notWorkCount;
	// 計算対象外のカウント条件
	private SpecCountNotCalcSubject specCount;
	
	public static SpecTotalCountMonthly of(boolean continuousCount, 
			boolean notWorkCount, SpecCountNotCalcSubject specCount) {
		
		SpecTotalCountMonthly result = new SpecTotalCountMonthly();
		
		result.continuousCount = continuousCount;
		result.notWorkCount = notWorkCount;
		result.specCount = specCount;
		
		return result;
	}
	
	/** 条件を満たしているか判断 */
	public boolean isFitCondition(WorkType workType, WorkingSystem workingSystem, boolean isAttendanceDay,
			WorkTypeDaysCountTable workTypeDaysCountTable) {
		
		/** パラメータ「労働制」を判断 */
		if (workingSystem == WorkingSystem.EXCLUDED_WORKING_CALCULATE) { /** 就業計算対象外 */
			/** 属性「計算対象外のカウント条件」を取得 */
			switch (this.specCount) {
			case Uncondition: return true; /** 無条件にカウントする */
			case NoUncondition: return false; /** 無条件にカウントしない */
			default: /** 勤務日のみカウントする →　①　*/
				break;
			} 
			
		} else { /** 以外*/
			/** パラメータ「勤務種類」を取得 */
			if (workType.isContinuousWork()) 
				/** 属性「連続勤務の日でもカウントする」を取得 */
				return this.continuousCount;
			
			/** 属性「勤務日ではない日でもカウントする」を取得 */
			if (this.notWorkCount) 
				return true;
				
			/** 「勤務日数」のカウント方法を判断 →　① */
		}
		
		/** 「勤務日数」のカウント方法を判断 ① */
		if (workTypeDaysCountTable.getWorkDays().v() > 0)
			/** ○出勤状態を判断する */
			return isAttendanceDay;
		
		return false;
	}
}
