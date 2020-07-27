package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * 社員の雇用履歴Adapter
 * @author HieuLt
 */
public interface EmploymentHisScheduleAdapter {
	/**
	 * [1] 期間を指定して雇用期間を取得する
	 * @param listEmpId
	 * @param datePeriod
	 * @return
	 */
	List<EmploymentPeriodImported> getEmploymentPeriod (List<String> listEmpId, DatePeriod datePeriod);

}
