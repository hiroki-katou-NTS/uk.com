package nts.uk.ctx.sys.portal.dom.webmenu;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class WebMenu extends AggregateRoot {

	/**
	 * 
	 */
	private String companyId;

	/**
	 * 
	 */

	private WebMenuCode webMenuCode;

	/**
	 * 
	 */

	private WebMenuName webMenuName;

	/**
	 * 
	 */

	private DefaultMenu defaultMenu;

	/**
	 * 
	 */

	private List<MenuBar> menuBars;
	
	
	public static WebMenu createFromJavaType(String companyId, String webMenuCode, String webMenuName, int defaultMenu, List<MenuBar> menuBars) {
		return new WebMenu(
				companyId,
				new WebMenuCode(webMenuCode),
				new WebMenuName(webMenuName),
				EnumAdaptor.valueOf(defaultMenu, DefaultMenu.class),
				menuBars);
	}

	public WebMenu(String companyId, WebMenuCode webMenuCode, WebMenuName webMenuName, DefaultMenu defaultMenu,
			List<MenuBar> menuBars) {
		super();
		this.companyId = companyId;
		this.webMenuCode = webMenuCode;
		this.webMenuName = webMenuName;
		this.defaultMenu = defaultMenu;
		this.menuBars = menuBars;
	}
	
	/**
	 * Check web menu is default
	 * @return true is default else false
	 */
	public boolean isDefault() {
		return this.defaultMenu.equals(DefaultMenu.DefaultMenu);
	}
}
