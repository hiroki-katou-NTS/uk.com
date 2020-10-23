package nts.uk.ctx.sys.portal.app.command.flowmenu;

import lombok.Value;

@Value
public class FileExportCommand {

	/**
	 * ファイルID
	 */
	private String fileId;
	
	/**
	 * フローメニューコード
	 */
	private String flowMenuCode;
}
