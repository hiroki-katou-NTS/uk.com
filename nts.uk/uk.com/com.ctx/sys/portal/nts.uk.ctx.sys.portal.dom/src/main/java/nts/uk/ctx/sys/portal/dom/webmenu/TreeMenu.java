package nts.uk.ctx.sys.portal.dom.webmenu;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.standardmenu.MenuCode;

@Setter
@Getter
@AllArgsConstructor
public class TreeMenu {

	private UUID titleMenuId;
	
	private MenuCode code;
	
	private Integer displayOrder;
	
	private MenuClassification classification;
	
	private System system;

	public static TreeMenu createFromJavaType(String titleMenuId, String code, int displayOrder, int classification, int system) {
		return new TreeMenu(UUID.fromString(titleMenuId), new MenuCode(code), displayOrder,EnumAdaptor.valueOf(classification, MenuClassification.class), EnumAdaptor.valueOf(system, System.class));
	}	
}
