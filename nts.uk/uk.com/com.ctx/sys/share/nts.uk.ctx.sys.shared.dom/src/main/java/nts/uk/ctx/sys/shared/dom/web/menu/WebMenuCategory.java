package nts.uk.ctx.sys.shared.dom.web.menu;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Menu category.
 */
@Getter @Setter
public class WebMenuCategory {
	
	/**
	 * Name.
	 */
	private String name;
	
	/**
	 * Background color.
	 */
	private String backgroundColor;
	
	/**
	 * Menu items.
	 */
	private List<MenuItem> items = new ArrayList<>();
	
	/**
	 * Titles.
	 */
	private List<TitleMenu> titles = new ArrayList<>();
}
