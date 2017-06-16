package nts.uk.ctx.sys.portal.dom.webmenu;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.standardmenu.MenuCode;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;

@AllArgsConstructor
public class TreeMenu {

	@Getter
	private UUID titleMenuId;
	
	@Getter
	private MenuCode code;
	
	@Getter
	private Integer displayOrder;
	
	@Getter
	private MenuClassification classification;
	
	@Getter
	private System system;

	public static TreeMenu createFromJavaType(String titleMenuId, String code, int displayOrder, int classification, int system) {
		return new TreeMenu(UUID.fromString(titleMenuId), new MenuCode(code), displayOrder,EnumAdaptor.valueOf(classification, MenuClassification.class), EnumAdaptor.valueOf(system, System.class));
	}	
}
