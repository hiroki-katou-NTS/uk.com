package nts.uk.ctx.sys.portal.app.command.flowmenu;

import lombok.Value;

@Value
public class DeleteFlowMenuCommand {
	
	/**
	 * フローメニューコード
	 */
	private String flowMenuCode;
}
