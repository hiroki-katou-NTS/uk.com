/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.flowmenu;

import lombok.Value;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.shr.com.context.AppContexts;

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
	// file name
	private String fileName;
	//
	private int defClassAtr;
}
