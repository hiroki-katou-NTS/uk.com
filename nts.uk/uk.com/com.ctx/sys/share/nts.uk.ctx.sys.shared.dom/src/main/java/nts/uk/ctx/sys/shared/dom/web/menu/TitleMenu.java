package nts.uk.ctx.sys.shared.dom.web.menu;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Title menu.
 */
@Getter @Setter
public class TitleMenu {
	
	/**
	 * Name.
	 */
	private String name;
	
	/**
	 * Title color.
	 */
	private String titleColor;
	
	/**
	 * Image path.
	 */
	private String imagePath;
	
	/**
	 * Items.
	 */
	private List<MenuItem> items;
	
	
}
