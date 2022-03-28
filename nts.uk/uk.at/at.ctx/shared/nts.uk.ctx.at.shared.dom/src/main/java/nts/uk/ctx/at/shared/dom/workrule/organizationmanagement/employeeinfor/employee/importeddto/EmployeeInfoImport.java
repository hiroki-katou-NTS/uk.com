package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto;

import lombok.*;

/**
 * dto by RequestList 228
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeInfoImport {
	
	/** 社員ID */
	private String sid;

	/** 社員コード.Employee code */
	private String scd;

	/** ビジネスネーム.Business name */
	private String bussinessName;

}