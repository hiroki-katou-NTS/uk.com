/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;

/**
 * ロールセット - Class RoleSet.
 * @author HieuNV
 */
@Getter
public class RoleSet extends AggregateRoot {

	/** ロールセットコード. */
	private RoleSetCode roleSetCd;

	/** 会社ID */
	private String companyId;

	/** ロールセット名称*/
	private RoleSetName roleSetName;

	/** 承認権限*/
	private ApprovalAuthority approvalAuthority;

	/** ロールID: オフィスヘルパーロール */
	private String officeHelperRole;

	/** ロールID: マイナンバーロール */
	private String myNumberRole;

	/** ロールID: 人事ロール */
	private String hRRole;

	/** ロールID: 個人情報ロール */
	private String personInfRole;

	/** ロールID: 就業ロール */
	private String employmentRole;

	/** ロールID: 給与ロール */
	private String salaryRole;

	/**
	 * Instantiates a new role set.
	 *
	 * @param roleSetCd
	 * @param companyId
	 * @param roleSetName
	 * @param approvalAuthority
	 * @param officeHelperRole
	 * @param myNumberRole
	 * @param hRRole
	 * @param personInfRole
	 * @param employmentRole
	 * @param salaryRole
	 */
	public RoleSet(RoleSetCode roleSetCd
			, String companyId
			, RoleSetName roleSetName
			, ApprovalAuthority approvalAuthority
			, String officeHelperRole
			, String myNumberRole
			, String hRRole
			, String personInfRole
			, String employmentRole
			, String salaryRole
			) {
		super();
		this.roleSetCd 			= roleSetCd;
		this.companyId 			= companyId;
		this.roleSetName 		= roleSetName;
		this.approvalAuthority 	= approvalAuthority;
		this.officeHelperRole 	= officeHelperRole;
		this.myNumberRole 		= myNumberRole;
		this.hRRole 			= hRRole;
		this.personInfRole 		= personInfRole;
		this.employmentRole 	= employmentRole;
		this.salaryRole 		= salaryRole;
	}
	
	/**
	 * If has approval Authority right.
	 *
	 * @return true
	 */
	public boolean HasApprovalAuthority() {
		return this.approvalAuthority == ApprovalAuthority.HasRight;
	}

	/**
	 * If hasn't approval Authority right.
	 *
	 * @return true
	 */
	public boolean HasntApprovalAuthority() {
		return this.approvalAuthority == ApprovalAuthority.HasntRight;
	}

	/**
	 * Remove approval authority
	 */
	public void RemoveApprovalAuthority() {
		this.approvalAuthority = ApprovalAuthority.HasntRight;
	}

	/**
	 * Build a role set domain
	 *
	 * @param roleSetCd
	 * @param companyId
	 * @param roleSetName
	 * @param approvalAuthority
	 * @param officeHelperRole
	 * @param myNumberRole
	 * @param hRRole
	 * @param personInfRole
	 * @param employmentRole
	 * @param salaryRole
	 */
	public void create(String roleSetCd
			, String companyId
			, String roleSetName
			, int approvalAuthority
			, String officeHelperRole
			, String myNumberRole
			, String hRRole
			, String personInfRole
			, String employmentRole
			, String salaryRole) {
		this.roleSetCd 			= new RoleSetCode(roleSetCd);
		this.companyId 			= companyId;
		this.roleSetName 		= new RoleSetName(roleSetName);
		this.approvalAuthority 	= EnumAdaptor.valueOf(approvalAuthority, ApprovalAuthority.class);
		this.officeHelperRole 	= officeHelperRole;
		this.myNumberRole 		= myNumberRole;
		this.hRRole 			= hRRole;
		this.personInfRole 		= personInfRole;
		this.employmentRole 	= employmentRole;
		this.salaryRole 		= salaryRole;
	}
}
