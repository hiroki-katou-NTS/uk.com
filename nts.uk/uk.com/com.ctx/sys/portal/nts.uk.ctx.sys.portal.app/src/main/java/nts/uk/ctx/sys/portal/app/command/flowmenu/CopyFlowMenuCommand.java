package nts.uk.ctx.sys.portal.app.command.flowmenu;

import lombok.Data;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.CreateFlowMenuDto;

@Data
public class CopyFlowMenuCommand {
	/**
	 * フローメニューコード
	 */
	private String flowMenuCode;
	
	/**
	 * フローメニュー作成
	 */
	private CreateFlowMenuDto createFlowMenu;
}
