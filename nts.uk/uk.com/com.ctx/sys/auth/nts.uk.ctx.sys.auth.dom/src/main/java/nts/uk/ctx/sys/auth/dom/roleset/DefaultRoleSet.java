/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import javax.inject.Inject;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 既定のロールセット - Class DefaultRoleSet
 * @author HieuNV
 */
@Getter
public class DefaultRoleSet extends AggregateRoot {

	@Inject
	private DefaultRoleSetRepository defaultRoleSetRepository;
	/** 会社ID */
	private String companyId;

	/** ロールセットコード. */
	private RoleSetCode roleSetCd;

	/**
	 * Instantiates a new default role set.
	 * 
	 * @param companyId
	 * @param roleSetCd
	 */
	private DefaultRoleSet(String companyId, RoleSetCode roleSetCd) {
		super();
		this.companyId = companyId;
		this.roleSetCd = roleSetCd;
	}
	
	/**
	 * Build a default role set domain
	 *
	 * @param companyId
	 * @param roleSetCd
	 */
	public static DefaultRoleSet create(String companyId, String roleSetCd) {
		return new DefaultRoleSet(companyId, new RoleSetCode(roleSetCd));
	}
	
	/** 
	 * check if there are set default role set for the company id
	 * @return
	 */
	public boolean isCompanyHasDefaultRoleSet() {
		return defaultRoleSetRepository.findByCompanyId(this.companyId).isPresent();
	}
}
