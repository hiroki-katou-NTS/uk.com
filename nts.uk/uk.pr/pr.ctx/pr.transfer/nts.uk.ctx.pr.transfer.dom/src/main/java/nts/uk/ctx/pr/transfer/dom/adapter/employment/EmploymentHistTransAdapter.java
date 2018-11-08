package nts.uk.ctx.pr.transfer.dom.adapter.employment;

import java.util.List;

/**
 * アダプタ：所属雇用履歴
 * @author HungTT
 */
public interface EmploymentHistTransAdapter {

	/**
	 * 所属雇用履歴を取得する
	 * @param employeeId 社員ID
	 * @return 所属雇用履歴リスト
	 */
	// RequestList326
	List<EmploymentHistImport> findByEmployeeIdOrderByStartDate(String employeeId);
}
