/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.flowmenu;

import lombok.Value;

@Value
public class UpdateFlowMenuCommand {
	private String toppagePartID;	
	//top page code
	private String topPageCode;
	//top page name
	private String topPageName;
	//width size
	private int widthSize;
	//height size
	private int heightSize;
	
	/** File ID */
	private String fileID;
	
	// Def Class Atr
	private int defClassAtr;
}
