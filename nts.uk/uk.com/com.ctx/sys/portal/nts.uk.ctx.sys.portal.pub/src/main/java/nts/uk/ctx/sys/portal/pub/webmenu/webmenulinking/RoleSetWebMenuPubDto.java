package nts.uk.ctx.sys.portal.pub.webmenu.webmenulinking;

import lombok.Data;

@Data
public class RoleSetWebMenuPubDto {

	/** ロールセットコード. */
	private String roleSetCd;

	/** メニューコードリスト */
	private String webMenuCd;

	/** 会社ID */
	private String companyId;

	public RoleSetWebMenuPubDto(String roleSetCd, String webMenuCd, String companyId) {
		this.roleSetCd = roleSetCd;
		this.webMenuCd = webMenuCd;
		this.companyId = companyId;
	}
}