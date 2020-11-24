package nts.uk.ctx.sys.shared.dom.employee;

import java.util.Optional;

/**
 * 社員データ管理情報のAdapter
 */
public interface EmployeeDataManageInfoAdapter {

	Optional<EmployeeDataMngInfoImport> findByEmployeeCode(String companyId, String employeeCode);

	Optional<EmployeeDataMngInfoImport> findByEmployeeId(String employeeId);
}
