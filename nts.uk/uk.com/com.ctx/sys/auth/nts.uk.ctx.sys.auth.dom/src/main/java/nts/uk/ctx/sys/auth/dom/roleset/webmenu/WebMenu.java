package nts.uk.ctx.sys.auth.dom.roleset.webmenu;

import lombok.Data;
@Data
public class WebMenu {

	/** Webメニューコード */
	private String webMenuCd;

	/** Webメニュー名称 */
	private String webMenuName;

	/** 会社ID */
	private String companyId;

	/** 既定メニュー */
	private boolean defaultMenu;


	/**
	 * Transfer data from Domain into Dto to response to client
	 * @param roleSet
	 * @return
	 */
	/*	public static WebMenu build(WebMenuPubDto webMenuPubDto) {
		WebMenu result = new WebMenu();
		result.webMenuCd(webMenuPubDto.webMenuCd);
		result.webMenuName(webMenuPubDto.webMenuName);
		result.companyId(webMenuPubDto.companyId);
		result.defaultMenu(webMenuPubDto.defaultMenu);
		return result;
	}
	*/
}
