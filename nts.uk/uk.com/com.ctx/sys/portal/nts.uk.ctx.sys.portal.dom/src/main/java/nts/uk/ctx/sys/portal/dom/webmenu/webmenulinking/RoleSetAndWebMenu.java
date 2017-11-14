/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuCode;

/**
 * ロールセット別�?付�?� - Class RoleSetAndWenMenu
 * @author HieuNV
 */
@Getter
public class RoleSetAndWebMenu extends AggregateRoot {

	/** �?社ID */
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
	public RoleSetAndWebMenu(String companyId, String webMenuCd, String roleSetCd) {
		super();
		this.companyId = companyId;
		this.webMenuCd = new WebMenuCode(webMenuCd);
		this.roleSetCd = new RoleSetCode(roleSetCd);
	}
	
	@Override
	public void validate() {
		super.validate();
	}
}
