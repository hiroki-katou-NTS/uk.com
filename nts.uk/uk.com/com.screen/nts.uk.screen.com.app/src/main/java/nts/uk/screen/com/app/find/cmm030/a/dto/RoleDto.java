package nts.uk.screen.com.app.find.cmm030.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.auth.dom.role.Role;

@Data
@AllArgsConstructor
public class RoleDto {

	// Id
	private String roleId;

	// 契約コード
	private String contractCode;

	// 会社ID
	private String companyId;

	// コード
	private String roleCode;

	// ロール名称
	private String name;

	// ロール種類
	private int roleType;

	// 担当区分
	private int assignAtr;

	// 参照範囲
	private int employeeReferenceRange;

	// 承認権限
	private Boolean approvalAuthority;

	public static RoleDto fromDomain(Role domain) {
		return new RoleDto(domain.getRoleId(), domain.getContractCode().v(), domain.getCompanyId(),
				domain.getRoleCode().v(), domain.getName().v(), domain.getRoleType().value, domain.getAssignAtr().value,
				domain.getEmployeeReferenceRange().value, domain.getApprovalAuthority().orElse(null));
	}
}
