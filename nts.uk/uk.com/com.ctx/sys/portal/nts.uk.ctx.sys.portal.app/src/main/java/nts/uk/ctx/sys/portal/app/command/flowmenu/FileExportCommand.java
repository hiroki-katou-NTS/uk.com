package nts.uk.ctx.sys.portal.app.command.flowmenu;

import lombok.Value;

@Value
public class FileExportCommand {
	
	/**
	 * フローメニューコード
	 */
	private String flowMenuCode;
	
	/**
	 * HTML
	 */
	private String htmlContent;
}
