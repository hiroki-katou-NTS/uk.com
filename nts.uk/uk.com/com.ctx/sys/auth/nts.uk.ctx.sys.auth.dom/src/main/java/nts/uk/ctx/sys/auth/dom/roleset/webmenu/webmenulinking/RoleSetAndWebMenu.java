/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking;

/**
 * ロールセット別�?付�?� - Class RoleSetAndWenMenu
 * @author HieuNV
 */
@lombok.Value
public class RoleSetAndWebMenu {

	/** �?社ID */
	private String companyId;

	/** メニューコードリスト */
	private String webMenuCd;

	/** ロールセットコード. */
	private String roleSetCd;

	/**
	 * Instantiates a new default role set.
	 * 
	 * @param companyId
	 * @param webMenuCd
	 * @param roleSetCd
	 */
	public RoleSetAndWebMenu(String companyId, String webMenuCd, String roleSetCd) {
		this.companyId = companyId;
		this.webMenuCd = webMenuCd;
		this.roleSetCd = roleSetCd;
	}
	
}
