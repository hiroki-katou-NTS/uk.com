package nts.uk.ctx.sys.auth.dom.roleset.webmenu;
/**
 *  担当ロール別紐付け
 */
import lombok.Value;
@Value
public class WebMenu {

	/** Webメニューコード */
	private String webMenuCd;

	/** Webメニュー名称 */
	private String webMenuName;

	/** 会社ID */
	private String companyId;

	/** 既定メニュー */
	private boolean defaultMenu;

	public WebMenu(String webMenuCd, String webMenuName, String companyId, boolean defaultMenu) {
		this.webMenuCd = webMenuCd;
		this.webMenuName = webMenuName;
		this.companyId = companyId;
		this.defaultMenu = defaultMenu;
	}
}
