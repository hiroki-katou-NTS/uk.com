package nts.uk.ctx.sys.shared.dom.adapter;

import java.util.Optional;

import nts.uk.ctx.sys.shared.dom.dto.EmployeeImport;

public interface SysSharedEmployeeAdapter {
	/**
	 * 社員コードからユーザを取得する
	 *
	 * @param 会社ID
	 * @param 社員コード
	 * @return 社員（インポート）
	 */
	Optional<EmployeeImport> getByEmployeeCode(String companyId,String employeeCode);
}
