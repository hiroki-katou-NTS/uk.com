package nts.uk.ctx.sys.auth.app.find.roleset;

import lombok.Data;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;

@Data
public class RoleSetDto {

	/** ロールセットコード. */
	private String roleSetCd;

	/** 会社ID */
	private String companyId;

	/** ロールセット名称*/
	private String roleSetName;

	/** 承認権限*/
	private boolean approvalAuthority;

	/** ロールID: オフィスヘルパーロール */
	private String officeHelperRoleCd;

	/** ロールID: マイナンバーロール */
	private String myNumberRoleCd;

	/** ロールID: 人事ロール */
	private String hRRoleCd;

	/** ロールID: 個人情報ロール */
	private String personInfRoleCd;

	/** ロールID: 就業ロール */
	private String employmentRoleCd;

	/** ロールID: 給与ロール */
	private String salaryRoleCd;
	
	/**
	 * Transfer data from Domain into Dto to response to client
	 * @param roleSet
	 * @return
	 */
	public static RoleSetDto build(RoleSet roleSet) {
		RoleSetDto result = new RoleSetDto();
		result.setApprovalAuthority(roleSet.hasApprovalAuthority());
		result.setCompanyId(roleSet.getCompanyId());
		result.setEmploymentRoleCd(RoleSet.getRoleTypeCd(roleSet.getEmploymentRole()));
		result.setHRRoleCd(RoleSet.getRoleTypeCd(roleSet.getHRRole()));
		result.setMyNumberRoleCd(RoleSet.getRoleTypeCd(roleSet.getMyNumberRole()));
		result.setOfficeHelperRoleCd(RoleSet.getRoleTypeCd(roleSet.getOfficeHelperRole()));
		result.setPersonInfRoleCd(RoleSet.getRoleTypeCd(roleSet.getPersonInfRole()));
		result.setRoleSetCd(roleSet.getRoleSetCd().v());
		result.setRoleSetName(roleSet.getRoleSetName().v());
		result.setSalaryRoleCd(RoleSet.getRoleTypeCd(roleSet.getSalaryRole()));
		return result;
	}
}
