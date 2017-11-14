/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import java.util.Optional;

import javax.inject.Inject;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BundledBusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;

/**
 * ロールセット - Class RoleSet.
 * @author HieuNV
 */
@Getter
public class RoleSet extends AggregateRoot {

	@Inject
	private RoleRepository roleRepository;
	
	@Inject
	private DefaultRoleSetRepository defaultRoleSetRepository;
	
	@Inject
	private RoleSetRepository roleSetRepository;
	
	/** ロールセットコード. */
	private RoleSetCode roleSetCd;

	/** 会社ID */
	private String companyId = "000000000000-0000";

	/** ロールセット名称*/
	private RoleSetName roleSetName;

	/** 承認権限*/
	private ApprovalAuthority approvalAuthority;

	/** ロールID: オフィスヘルパーロール */
	private Optional<Role> officeHelperRole;

	/** ロールID: マイナンバーロール */
	private Optional<Role> myNumberRole;

	/** ロールID: 人事ロール */
	private Optional<Role> hRRole;

	/** ロールID: 個人情報ロール */
	private Optional<Role> personInfRole;

	/** ロールID: 就業ロール */
	private Optional<Role> employmentRole;

	/** ロールID: 給与ロール */
	private Optional<Role> salaryRole;

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
	private RoleSet(RoleSetCode roleSetCd
			, String companyId
			, RoleSetName roleSetName
			, ApprovalAuthority approvalAuthority
			, Optional<Role> officeHelperRole
			, Optional<Role> myNumberRole
			, Optional<Role> hRRole
			, Optional<Role> personInfRole
			, Optional<Role> employmentRole
			, Optional<Role> salaryRole
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
	public boolean hasApprovalAuthority() {
		return this.approvalAuthority == ApprovalAuthority.HasRight;
	}

	/**
	 * If hasn't approval Authority right.
	 *
	 * @return true
	 */
	public boolean hasntApprovalAuthority() {
		return this.approvalAuthority == ApprovalAuthority.HasntRight;
	}

	/**
	 * set approval authority
	 */
	public void setApprovalAuthority() {
		this.approvalAuthority = ApprovalAuthority.HasRight;
	}

	/**
	 * Remove approval authority
	 */
	public void removeApprovalAuthority() {
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
	public static RoleSet create(String roleSetCd
			, String companyId
			, String roleSetName
			, boolean approvalAuthority
			, String officeHelperRoleCd
			, String myNumberRoleCd
			, String hRRoleCd
			, String personInfRoleCd
			, String employmentRoleCd
			, String salaryRoleCd) {
		return new RoleSet(new RoleSetCode(roleSetCd)
				, companyId
				, new RoleSetName(roleSetName)
				, approvalAuthority ? ApprovalAuthority.HasRight : ApprovalAuthority.HasntRight
				, getRoleById(officeHelperRoleCd)
				, getRoleById(myNumberRoleCd)
				, getRoleById(hRRoleCd)
				, getRoleById(personInfRoleCd)
				, getRoleById(employmentRoleCd)
				,getRoleById(salaryRoleCd)
				);
	}

	/**
	 * Extract Role by Id
	 * @param roleId
	 * @return
	 */
	private static Optional<Role> getRoleById(String roleId) {
		//TODO - > why is it list?
		//return StringUtil.isNullOrEmpty(officeHelperRole, true) ? null : roleRepository.findById(roleId).get(0);
		return null;
	}

	public static String getRoleTypeCd(Optional<Role> opRole) {
		return opRole.isPresent() ? opRole.get().getRoleId() : null;
	}
	
	
	@Override
	public void validate() {
		super.validate();
		//check duplicate RoleSetCd - ロールセットコードが重複してはならない
		if (isDublicateRoleSetCd()) {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage("Msg_3");
			bbe.throwExceptions();
		}
		
		//throw error if there are not any role 
		if (!hasRole()) {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage("Msg_???");
			bbe.throwExceptions();
		}

	}
		
	/**
	 * check if there are existed Role Set Code
	 * @return
	 */
	private boolean isDublicateRoleSetCd() {
		return roleSetRepository.isDuplicateRoleSetCd(this.roleSetCd.v(), this.companyId);
	}
	
	/**
	 * Check are there any role ?
	 * @return
	 */
	private boolean hasRole() {
		return this.employmentRole.isPresent()
				|| this.officeHelperRole.isPresent()
				|| this.myNumberRole.isPresent()
				|| this.hRRole.isPresent()
				|| this.personInfRole.isPresent()
				|| this.employmentRole.isPresent()
				|| this.salaryRole.isPresent();

	}
	
	/**
	 * Validate constrains before perform deleting
	 */
	public void validateForDelete() {
		if (isDefault()) {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage("Msg_585");
			bbe.throwExceptions();
		}
		if (isGrantedForMember()) {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage("Msg_???");
			bbe.throwExceptions();
		}
		if (isGrantedForPosition()) {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage("Msg_???");
			bbe.throwExceptions();
		}
		
	}
	
	/**
	 * Check setting default of Role set
	 * @return
	 */
	private boolean isDefault() {
		return defaultRoleSetRepository.find(companyId, roleSetCd.v()).isPresent();
	}
	
	/**
	 * Check if this Role Set is granted for member
	 * @return
	 */
	private boolean isGrantedForMember() {
		return false;
	}
	
	/**
	 * Check if this Role Set is granted for Position (manager)
	 * @return
	 */
	private boolean isGrantedForPosition() {
		return false;
	}
}
