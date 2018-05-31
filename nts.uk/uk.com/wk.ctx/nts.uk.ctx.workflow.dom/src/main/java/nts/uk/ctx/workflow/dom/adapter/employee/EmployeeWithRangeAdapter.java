package nts.uk.ctx.workflow.dom.adapter.employee;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * @author sang.nv
 *
 */
public interface EmployeeWithRangeAdapter {
	/**
	 * RequestList314
	 * ログイン者の社員参照範囲内で社員を取得する
	 * @param companyID 会社ID
	 * @param employeeCD 社員コード
	 * @return
	 */
	Optional<EmployeeWithRangeLoginImport> findEmployeeByAuthorizationAuthority(String companyID, String employeeCD);

	/**
	 * RequestList315
	 * 承認権限がある社員の中から社員を取得する
	 * @param companyID 会社ID
	 * @param employeeCD 社員コード
	 * @param baseDate 基準日
	 * @return
	 */
	Optional<EmployeeWithRangeLoginImport> findByEmployeeByLoginRange(String companyID, String employeeCD, GeneralDate baseDate);
}
