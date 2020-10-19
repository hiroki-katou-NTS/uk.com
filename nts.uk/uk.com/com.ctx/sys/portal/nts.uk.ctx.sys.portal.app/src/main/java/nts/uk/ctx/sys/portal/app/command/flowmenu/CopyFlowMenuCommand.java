package nts.uk.ctx.sys.portal.app.command.flowmenu;

import lombok.Value;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.CreateFlowMenuDto;

@Value
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
