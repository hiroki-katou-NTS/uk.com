package nts.uk.ctx.sys.portal.app.command.flowmenu;

import lombok.Value;

@Value
public class UpdateFlowMenuLayoutCommand {

	/**
	 * フローメニューコード
	 */
	private String flowMenuCode;
	
	/**
	 * フローメニューレイアウト
	 */
	private FlowMenuLayoutCommand flowMenuLayout;
}
