package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * 社員の所属組織Imported						
 * @author HieuLt
 */
@Getter
@RequiredArgsConstructor
public class EmpOrganizationImport {

	/**社員ID**/
	private final EmployeeId empId;
	/**社員コード**/
	private final Optional<String> empCd;
	/**ビジネスネーム**/
	private final Optional<String> businessName;
	/**職場ID**/
	private final String workplaceId;
	/**職場グループID**/
	private final Optional<String> workplaceGroupId;
}
