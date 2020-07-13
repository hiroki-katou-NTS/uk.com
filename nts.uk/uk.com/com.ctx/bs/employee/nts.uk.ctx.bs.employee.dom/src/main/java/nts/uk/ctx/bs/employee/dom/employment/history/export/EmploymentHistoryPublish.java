package nts.uk.ctx.bs.employee.dom.employment.history.export;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;

/**
 * 社員の雇用履歴Publish
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".基幹.社員.雇用.雇用履歴.Export
 * @author HieuLt
 *
 */
public interface EmploymentHistoryPublish {
	/**
	 * [1] 期間を指定して雇用期間を取得する
	 * @param lstEmpID
	 * @param datePeriod
	 * @return
	 */
	List<EmploymentPeriodExported> get(List<String> lstEmpID , DatePeriod datePeriod);

	
	public static interface Require {
		/**
		 * EmploymentHistoryRepository
		 * [R-1] 期間付き履歴項目を取得する
		 * @param employeeIds
		 * @param datePeriod
		 * @return
		 */
		List<EmploymentHistory> getByListSid(List<String> employeeIds  ,  DatePeriod datePeriod);
	}
}
