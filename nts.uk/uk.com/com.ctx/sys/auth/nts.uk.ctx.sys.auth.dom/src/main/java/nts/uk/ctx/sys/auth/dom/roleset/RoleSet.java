/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedJobTitleRepository;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedPersonRepository;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenu;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenuAdapter;

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
	
	@Inject
	private RoleSetGrantedPersonRepository roleSetGrantedPersonRepository;
	
	@Inject
	private RoleSetGrantedJobTitleRepository roleSetGrantedJobTitleRepository;
	
	@Inject
	private RoleSetAndWebMenuAdapter roleSetAndWebMenuAdapter;
	
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

	/** list of Web menu link */
	List<RoleSetAndWebMenu> roleSetAndWebMenus;
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
		this.buildRoleSetAndWebMenu();
	}
	
	
	/**
	 * Initial a new RoleSet with list of WebMenu code.
	 * @param roleSetCd
	 * @param companyId
	 * @param roleSetName
	 * @param approvalAuthority
	 * @param officeHelperRoleCd
	 * @param myNumberRoleCd
	 * @param hRRoleCd
	 * @param personInfRoleCd
	 * @param employmentRoleCd
	 * @param salaryRoleCd
	 * @param webMenuCds
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
			, String salaryRoleCd
			, List<String> webMenuCds) {
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
		//build list of RoleSetAndWebMenu from list of WebMenu code
		if (CollectionUtil.isEmpty(webMenuCds)) {
			this.roleSetAndWebMenus = webMenuCds.stream()
				.map(webMenuCd -> new RoleSetAndWebMenu(companyId, webMenuCd, roleSetCd)
				).collect(Collectors.toList());
		} else {
			this.roleSetAndWebMenus = new ArrayList<>();
		}
	}
	
	/**
	 * Get list of Web menu
	 */
	private void buildRoleSetAndWebMenu() {
		this.roleSetAndWebMenus = roleSetAndWebMenuAdapter.findAllWebMenuByRoleSetCd(this.roleSetCd.v());
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
		return StringUtils.isNoneEmpty(roleId) ? null : roleRepository.findByRoleId(roleId);
	}

	/**
	 * Get role id from Optional<Role>
	 * @param opRole
	 * @return role id if it is present, else null
	 */
	public static String getRoleId(Optional<Role> opRole) {
		return opRole. isPresent() ? opRole.get().getRoleId() : null;
	}
			
	/**
	 * Check are there any role ?
	 * @return
	 */
	public boolean hasAnyRole() {
		return this.employmentRole.isPresent()
				|| this.officeHelperRole.isPresent()
				|| this.myNumberRole.isPresent()
				|| this.hRRole.isPresent()
				|| this.personInfRole.isPresent()
				|| this.employmentRole.isPresent()
				|| this.salaryRole.isPresent();

	}
	
	
	/**
	 * Check setting default of Role set
	 * @return
	 */
	public boolean isDefault() {
		return defaultRoleSetRepository.find(companyId, roleSetCd.v()).isPresent();
	}
	
	/**
	 * Check if this Role Set is granted for member
	 * @return
	 */
	public boolean isGrantedForPerson() {
		/**check from CAS014 */
		return roleSetGrantedPersonRepository.checkRoleSetCdExist(this.roleSetCd.v(), this.companyId);
	}
	
	/**
	 * Check if this Role Set is granted for Position (manager)
	 * @return
	 */
	public boolean isGrantedForPosition() {
		/** check from CAS014 */
		return roleSetGrantedJobTitleRepository.checkRoleSetCdExist(this.roleSetCd.v(), this.companyId);
	}
	
	public void removePersonInfRole() {
		this.personInfRole = null;
	}
}
