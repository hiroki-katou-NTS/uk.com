package nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 集計すべき期間を計算
 * @author shuichu_ishida
 */
public interface CalcPeriodForAggregate {
	
	/**
	 * 集計すべき期間を計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrEnd 集計終了日
	 */
	List<ClosurePeriod> algorithm(String companyId, String employeeId, GeneralDate aggrEnd);
}
