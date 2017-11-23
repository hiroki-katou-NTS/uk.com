package nts.uk.ctx.sys.portal.pub.webmenu.webmenulinking;

import lombok.Data;

@Data
public class RoleSetWebMenuExport {

	/** ロールセットコード. */
	private String roleSetCd;

	/** メニューコードリスト */
	private String webMenuCd;

	/** 会社ID */
	private String companyId;

	public RoleSetWebMenuExport(String roleSetCd, String webMenuCd, String companyId) {
		this.roleSetCd = roleSetCd;
		this.webMenuCd = webMenuCd;
		this.companyId = companyId;
	}
}