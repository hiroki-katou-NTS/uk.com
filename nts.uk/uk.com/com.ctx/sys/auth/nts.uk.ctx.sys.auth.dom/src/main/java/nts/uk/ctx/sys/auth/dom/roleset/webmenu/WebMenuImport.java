package nts.uk.ctx.sys.auth.dom.roleset.webmenu;
/**
 *  担当ロール別紐付け
 */
import lombok.Value;
@Value
public class WebMenuImport {

	/** Webメニューコード */
	private String webMenuCd;

	/** Webメニュー名称 */
	private String webMenuName;

	/** 会社ID */
	private String companyId;

	/** 既定メニュー */
	private boolean defaultMenu;

	public WebMenuImport(String companyId, String webMenuCd, String webMenuName, boolean defaultMenu) {
		this.webMenuCd = webMenuCd;
		this.webMenuName = webMenuName;
		this.companyId = companyId;
		this.defaultMenu = defaultMenu;
	}
}
