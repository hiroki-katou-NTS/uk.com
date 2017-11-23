package nts.uk.ctx.sys.portal.app.find.persettingmenu;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.standardmenu.MenuCode;
import nts.uk.ctx.sys.portal.dom.standardmenu.MenuDisplayName;

@Data
public class PermissionSettingMenuDto {
	/** The menu code. */
	private MenuCode code;

	private MenuDisplayName displayName;

	private String screenId;
	
	private String programId;
	
	private String queryString;
}
