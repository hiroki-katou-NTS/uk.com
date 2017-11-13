/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * ロールセット別紐付け - Class RoleSetAndWenMenu
 * @author HieuNV
 */
@Getter
public class RoleSetAndWebMenu extends AggregateRoot {

	/** 会社ID */
	private String companyId;

	/** メニューコードリスト */
	private WebMenuCode webMenuCd;

	/** ロールセットコード. */
	private RoleSetCode roleSetCd;

	/**
	 * Instantiates a new default role set.
	 * 
	 * @param companyId
	 * @param webMenuCd
	 * @param roleSetCd
	 */
	public RoleSetAndWebMenu(String companyId, WebMenuCode webMenuCd, RoleSetCode roleSetCd) {
		super();
		this.companyId = companyId;
		this.webMenuCd = webMenuCd;
		this.roleSetCd = roleSetCd;
	}
	
	/**
	 * Build a default role set domain
	 *
	 * @param companyId
	 * @param webMenuCd
	 * @param roleSetCd
	 */
	public void create(String companyId, String webMenuCd, String roleSetCd) {
		this.companyId = companyId;
		this.webMenuCd = new WebMenuCode(webMenuCd);
		this.roleSetCd = new RoleSetCode(roleSetCd);
	}
	

}
