/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.department;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * @author danpv
 *
 */
public interface AffDepartmentRepository {

	public Optional<AffiliationDepartment> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate);
	/**
	 * ドメインモデル「所属部門」を新規登録する
	 * @param domain
	 */
	void addAffDepartment(AffiliationDepartment domain);
	/**
	 * 取得した「所属部門」を更新する
	 * @param domain
	 */
	void updateAffDepartment(AffiliationDepartment domain);
	/**
	 * ドメインモデル「所属部門（兼務）」を削除する
	 * @param domain
	 */
	void deleteAffDepartment(AffiliationDepartment domain);
}
