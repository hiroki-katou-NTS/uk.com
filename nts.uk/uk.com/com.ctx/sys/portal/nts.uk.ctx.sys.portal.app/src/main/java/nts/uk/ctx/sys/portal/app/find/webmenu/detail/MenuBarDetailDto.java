package nts.uk.ctx.sys.portal.app.find.webmenu.detail;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuBarDetailDto {
	private String menuBarId;
	
	private String code;

	private String menuBarName;

	// From tree or menu bar itself
	private int selectedAttr;

	private int system;

	private int menuCls;

	private String backgroundColor;

	private String textColor;

	private int displayOrder;

	private List<TitleBarDetailDto> titleMenu;
}
