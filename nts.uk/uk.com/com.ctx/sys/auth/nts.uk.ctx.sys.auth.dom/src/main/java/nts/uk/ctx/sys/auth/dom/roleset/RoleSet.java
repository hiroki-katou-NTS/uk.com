/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import java.util.Optional;

import javax.inject.Inject;

import lombok.Getter;
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
	public RoleSet(String roleSetCd
			, String companyId
			, String roleSetName
			, ApprovalAuthority approvalAuthority
			, String officeHelperRoleCd
			, String myNumberRoleCd
			, String hRRoleCd
			, String personInfRoleCd
			, String employmentRoleCd
			, String salaryRoleCd) {
		super();
		this.roleSetCd 			= new RoleSetCode(roleSetCd);
		this.companyId 			= companyId;
		this.roleSetName 		= new RoleSetName(roleSetName);
		this.approvalAuthority 	= approvalAuthority;
		this.officeHelperRole 	= getRoleById(officeHelperRoleCd);
		this.myNumberRole 		= getRoleById(myNumberRoleCd);
		this.hRRole 			= getRoleById(hRRoleCd);
		this.personInfRole 		= getRoleById(personInfRoleCd);
		this.employmentRole 	= getRoleById(employmentRoleCd);
		this.salaryRole 		= getRoleById(salaryRoleCd);
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
	 * Extract Role by Id
	 * @param roleId
	 * @return
	 */
	private Optional<Role> getRoleById(String roleId) {
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
	 * Validate for update
	 */
	public void validateForUpdate() {
		super.validate();
	
		//throw error if there are not any role 
		if (!hasRole()) {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage("Msg_???");
			bbe.throwExceptions();
		}

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
		// ロールセット個人別付与で使用されている場合は削除できない
		if (isGrantedForMember()) {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage("Msg_???");
			bbe.throwExceptions();
		}
		// ロールセット職位別付与で使用されている場合は削除できない
		if (isGrantedForPosition()) {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage("Msg_???");
			bbe.throwExceptions();
		}

		// ドメインモデル「既定のロールセット」を取得する
		if (isDefault()) {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage("Msg_585");
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
		/**TODO -> check from CAS014 */
		return false;
	}
	
	/**
	 * Check if this Role Set is granted for Position (manager)
	 * @return
	 */
	private boolean isGrantedForPosition() {
		/**TODO -> check from CAS014 */
		return false;
	}
}
