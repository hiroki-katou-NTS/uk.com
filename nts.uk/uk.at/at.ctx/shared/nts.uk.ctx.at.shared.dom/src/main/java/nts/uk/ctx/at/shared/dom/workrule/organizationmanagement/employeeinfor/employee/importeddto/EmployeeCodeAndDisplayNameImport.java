package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto;

import lombok.Value;

/**
 * 社員コードと表示名
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.組織管理.社員情報.社員.社員コードと表示名
 * @author lan_lt
 *
 */
@Value
public class EmployeeCodeAndDisplayNameImport {
	/** 社員ID **/
	private String employeeId;

	/** 社員コード **/
	private String employeeCode;

	/** ビジネスネーム **/
	private String businessName;

}
