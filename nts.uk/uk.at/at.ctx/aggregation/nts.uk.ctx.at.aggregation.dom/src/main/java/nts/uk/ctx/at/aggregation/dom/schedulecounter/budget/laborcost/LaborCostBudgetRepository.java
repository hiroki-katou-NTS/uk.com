package nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 人件費予算Repository
 * @author kumiko_otake
 */
public interface LaborCostBudgetRepository {

	/**
	 * insert
	 * @param companyId 会社ID
	 * @param domain 人件費予算
	 */
	void insert( String companyId, LaborCostBudget domain );

	/**
	 * delete
	 * @param companyId 会社ID
	 * @param targetOrg 対象組織
	 * @param ymd 年月日
	 */
	void delete( String companyId, TargetOrgIdenInfor targetOrg, GeneralDate ymd );


	/**
	 * get*
	 * @param companyId 会社ID
	 * @param targetOrg 対象組織
	 * @param period 期間
	 * @return
	 */
	List<LaborCostBudget> get( String companyId, TargetOrgIdenInfor targetOrg, DatePeriod period );

}
