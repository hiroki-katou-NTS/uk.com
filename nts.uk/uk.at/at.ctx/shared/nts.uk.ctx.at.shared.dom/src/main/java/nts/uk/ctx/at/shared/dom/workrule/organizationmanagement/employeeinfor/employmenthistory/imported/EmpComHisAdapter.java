package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * 社員の所属会社履歴Adapter
 * @author HieuLt
 *
 */
public interface EmpComHisAdapter {

	/**[1] 期間を指定して在籍期間を取得する **/
	List<EmpEnrollPeriodImport> getEnrollmentPeriod (List<String> lstEmpId , DatePeriod datePeriod);
}
