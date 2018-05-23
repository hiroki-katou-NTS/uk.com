package nts.uk.ctx.at.request.dom.application.proxy;

import java.util.List;

public interface ProxyApplicationService {

	/**
	 * エラーチェック
	 * @param employeeId
	 * @param applicationType
	 * @return Name of employee has error
	 */
	List<String> errorCheckDepartment(List<String> employeeId, int applicationType);

	/**
	 * 申請種類選択
	 * @param employeeId
	 * @param applicationType
	 */
	void selectApplicationByType(List<String> employeeId, int applicationType);
}
