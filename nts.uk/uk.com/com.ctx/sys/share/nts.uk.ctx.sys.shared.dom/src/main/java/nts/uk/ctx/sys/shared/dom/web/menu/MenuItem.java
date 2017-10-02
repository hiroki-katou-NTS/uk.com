package nts.uk.ctx.sys.shared.dom.web.menu;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *	Menu item.
 */
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {

	/**
	 * Separator.
	 */
	public static final String SEPARATOR = "menu_item_separator";
	
	/**
	 * Name.
	 */
	private String name;
	
	/**
	 * Path.
	 */
	private String path;
	
	public static MenuItem separator() {
		return new MenuItem(SEPARATOR, null);
	}
}
