package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface ExternalBudgetActualResultRepository {

	/**
	 * [1] 日次の外部予算実績を取得する
	 *
	 * @param targetOrg
	 * @param itemCode
	 * @param ymd
	 * @return ExternalBudgetActualResult
	 */
	public List<ExternalBudgetActualResult> get(TargetOrgIdenInfor targetOrg, ExternalBudgetCd itemCode, GeneralDate ymd);

	/**
	 * [2] 期間の日次の外部予算実績を取得する
	 *
	 * @param targetOrg
	 * @param datePeriod
	 * @param itemCode
	 * @return List<ExternalBudgetActualResult>
	 */

	public List<ExternalBudgetActualResult> getByPeriod(TargetOrgIdenInfor targetOrg, DatePeriod datePeriod, ExternalBudgetCd itemCode);

	/**
	 * [3] insert(日次の外部予算実績)
	 *
	 * @param actualResult
	 */
	public void insert(ExternalBudgetActualResult actualResult);

	/**
	 * [4] update(日次の外部予算実績)
	 *
	 * @param actualResult
	 */
	public void update(ExternalBudgetActualResult actualResult);

	/**
	 * [5] delete(対象組織識別情報, 外部予算実績項目コード, 年月日)
	 *
	 * @param targetOrg
	 * @param itemCode
	 * @param ymd
	 */
	public void delete(TargetOrgIdenInfor targetOrg, ExternalBudgetCd itemCode, GeneralDate ymd);

	/**
	 * [6] 期間の日次の外部予算実績をすべて取得する
	 */
	public List<ExternalBudgetActualResult> getAllByPeriod(TargetOrgIdenInfor targetOrg, DatePeriod datePeriod);

	public List<ExternalBudgetActualResult> getAllByPeriod(List<TargetOrgIdenInfor> lstTargetOrg, DatePeriod datePeriod);

}
