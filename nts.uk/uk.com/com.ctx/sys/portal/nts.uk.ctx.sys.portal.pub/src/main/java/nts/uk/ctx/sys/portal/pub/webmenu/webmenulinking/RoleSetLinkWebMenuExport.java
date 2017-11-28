package nts.uk.ctx.sys.portal.pub.webmenu.webmenulinking;

import lombok.Data;

@Data
public class RoleSetLinkWebMenuExport {

	/** ロールセットコード. */
	private String roleSetCd;

	/** メニューコードリスト */
	private String webMenuCd;

	/** 会社ID */
	private String companyId;

	public RoleSetLinkWebMenuExport(String companyId, String roleSetCd, String webMenuCd) {
		this.roleSetCd = roleSetCd;
		this.webMenuCd = webMenuCd;
		this.companyId = companyId;
	}
}