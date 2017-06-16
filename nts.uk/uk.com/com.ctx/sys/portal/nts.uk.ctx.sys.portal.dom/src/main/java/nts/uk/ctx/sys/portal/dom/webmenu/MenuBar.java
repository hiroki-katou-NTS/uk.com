package nts.uk.ctx.sys.portal.dom.webmenu;

import java.util.List;
import java.util.UUID;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
/**
 * 
 * @author sonnh
 *
 */

@Value
public class MenuBar {
	
	private UUID menuBarId;
	
	private MenuCode code;
	
	private MenuBarName menuBarName;
		
	private SelectedAtr selectedAtr;
	
	private System system;

	private MenuClassification menuCls;
		
	private ColorCode backgroundColor;
	
	private ColorCode textColor;
	
	private Integer displayOrder;

	private List<TitleMenu> titleMenu;
	
	public static MenuBar createFromJavaType(String menuBarId, String menuBarName, int selectedAtr,
			int system, int menuCls, String code, String backgroundColor, String textColor,
			Integer displayOrder, List<TitleMenu> titleMenu) {
		return new MenuBar(
				UUID.fromString(menuBarId),
				new MenuCode(code),
				new MenuBarName(menuBarName),
				EnumAdaptor.valueOf(selectedAtr, SelectedAtr.class),
				EnumAdaptor.valueOf(system, System.class),
				EnumAdaptor.valueOf(menuCls, MenuClassification.class),
				new ColorCode(backgroundColor),
				new ColorCode(textColor),
				displayOrder,
				titleMenu);
	}
	
	public static UUID createMenuBarId() {
		return UUID.randomUUID();
	}
	
	
}
