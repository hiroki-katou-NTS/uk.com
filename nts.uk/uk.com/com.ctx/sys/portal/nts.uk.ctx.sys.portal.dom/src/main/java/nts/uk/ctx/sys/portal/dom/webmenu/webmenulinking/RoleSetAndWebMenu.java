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
 * ロールセット別紐付け
 * @author HieuNV
 */
@Getter
public class RoleSetAndWebMenu extends AggregateRoot {

	/** ロールセットコード. */
	private RoleSetCode roleSetCd;

	/** メニューコードリスト */
	private WebMenuCode webMenuCd;

	/** 会社ID */
	private String companyId;

	/**
	 * Instantiates a new default role set.
	 * 
	 * @param companyId
	 * @param webMenuCd
	 * @param roleSetCd
	 */
	public RoleSetAndWebMenu(String roleSetCd, String webMenuCd, String companyId) {
		super();
		this.roleSetCd = new RoleSetCode(roleSetCd);
		this.webMenuCd = new WebMenuCode(webMenuCd);
		this.companyId = companyId;
	}
}
