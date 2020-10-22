package nts.uk.ctx.sys.portal.app.command.flowmenu;

import lombok.Value;

@Value
public class UpdateFlowMenuCommand {

	/**
	 * フローメニューコード
	 */
	private String flowMenuCode;
	
	/**
	 * フローメニュー名称
	 */
	private String flowMenuName;
}
