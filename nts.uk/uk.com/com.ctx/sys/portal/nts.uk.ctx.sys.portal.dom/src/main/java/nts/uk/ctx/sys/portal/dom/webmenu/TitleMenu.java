package nts.uk.ctx.sys.portal.dom.webmenu;

import java.util.List;
import java.util.UUID;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;

@Value
public class TitleMenu {

	private UUID menuBarId;

	private UUID titleMenuId;

	private TitleMenuName titleMenuName;

	private ColorCode backgroundColor;

	private String imageFile;

	private ColorCode textColor;

	private TitleMenuAtr titleMenuAtr;

	private TitleMenuCode titleMenuCode;

	private Integer displayOrder;

	private List<TreeMenu> treeMenu;

	public static TitleMenu createFromJavaType(String menuBarId, String titleMenuId, String titleMenuName,
			String backgroundColor, String imageFile, String textColor, int titleMenuAtr, String titleMenuCode,
			Integer displayOrder, List<TreeMenu> treeMenu) {
		return new TitleMenu(UUID.fromString(menuBarId), UUID.fromString(titleMenuId), new TitleMenuName(titleMenuName),
				new ColorCode(backgroundColor), imageFile, new ColorCode(textColor),
				EnumAdaptor.valueOf(titleMenuAtr, TitleMenuAtr.class), new TitleMenuCode(titleMenuCode), displayOrder,
				treeMenu);
	}

	public static TitleMenu newTitleMenu(String menuBarId, String titleMenuName, String backgroundColor,
			String imageFile, String textColor, int titleMenuAtr, String titleMenuCode, Integer displayOrder,
			List<TreeMenu> treeMenu) {	
		UUID titleMenuId = UUID.randomUUID();
		return createFromJavaType(menuBarId,titleMenuId.toString(),titleMenuName, backgroundColor, imageFile, textColor, titleMenuAtr, titleMenuCode, displayOrder, treeMenu);
	}
	
	public static UUID createTitleMenuId() {
		return UUID.randomUUID();
	}
}
