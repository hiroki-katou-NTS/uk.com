package nts.uk.ctx.sys.portal.dom.webmenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
public class WebMenu extends AggregateRoot {
	
	/**
	 * 
	 */
	@Getter
	private String companyCode;
	
	/**
	 * 
	 */
	@Getter
	private WebMenuCode webMenuCode;
	
	/**
	 * 
	 */
	@Getter
	private WebMenuName webMenuName;
	
	/**
	 * 
	 */
	@Getter
	private DefaultMenu defaultMenu;
	
	/**
	 * 
	 */
	@Getter
	private MenuBar menuBar;
	
	/**
	 * 
	 */
	@Getter
	private TitleMenu titleMenu;
	
	/**
	 * 
	 */
	@Getter
	private TreeMenu treeMenu;
	
	
/*	public static WebMenu createFromJavaType() {
		return new WebMenu(
				companyCode, webMenuCode, webMenuName, defaultMenu)
	}*/
	
	
}
