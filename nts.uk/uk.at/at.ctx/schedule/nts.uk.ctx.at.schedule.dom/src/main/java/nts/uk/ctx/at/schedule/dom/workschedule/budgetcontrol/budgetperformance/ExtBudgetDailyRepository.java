package nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface ExtBudgetDailyRepository {

	/**
	 * [1] 日次の外部予算実績を取得する
	 * 
	 * @param targetOrg
	 * @param itemCode
	 * @param ymd
	 * @return ExtBudgetDaily
	 */
	public List<ExtBudgetDaily> getDailyExtBudgetResults(TargetOrgIdenInfor targetOrg, ExtBudgetActItemCode itemCode,
			GeneralDate ymd);

	/**
	 * [2] 期間の日次の外部予算実績を取得する
	 * 
	 * @param targetOrg
	 * @param datePeriod
	 * @param itemCode
	 * @return List<ExtBudgetDaily>
	 */

	public List<ExtBudgetDaily> getDailyExtBudgetResultsForPeriod(TargetOrgIdenInfor targetOrg, DatePeriod datePeriod,
			ExtBudgetActItemCode itemCode);

	/**
	 * [3] insert(日次の外部予算実績)
	 * 
	 * @param extBudgetDaily
	 */
	public void insert(ExtBudgetDaily extBudgetDaily);

	/**
	 * [4] update(日次の外部予算実績)
	 * 
	 * @param extBudgetDaily
	 */
	public void update(ExtBudgetDaily extBudgetDaily);
	/**
	 * [5] delete(対象組織識別情報, 外部予算実績項目コード, 年月日)
	 * @param extBudgetDaily
	 */
	public void delete(TargetOrgIdenInfor targetOrg,ExtBudgetActItemCode itemCode, GeneralDate ymd);

}
