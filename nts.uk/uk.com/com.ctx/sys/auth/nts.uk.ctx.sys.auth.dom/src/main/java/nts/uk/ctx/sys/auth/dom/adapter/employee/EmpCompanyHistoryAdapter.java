package nts.uk.ctx.sys.auth.dom.adapter.employee;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * 社員の所属会社履歴Adapter
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.権限管理.Imported.contexts.社員.社員の所属会社履歴Adapter
 * @author lan_lt
 *
 */
public interface EmpCompanyHistoryAdapter {
	/**
	 * 期間を指定して在籍期間を取得する 
	 * getEnrollmentPeriod
	 * @param lstEmpId 社員IDリスト
	 * @param datePeriod 期間
	 * @return List<EmpEnrollPeriodImport>
	 */
	List<EmpEnrollPeriodImport> getEnrollmentPeriod (List<String> lstEmpId , DatePeriod datePeriod);
}
