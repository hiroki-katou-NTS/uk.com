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
	
	private String fileID;
	
	private String fileName;
	
	private int defClassAtr;
	
	public FlowMenu toDomain (){
		
		return FlowMenu.createFromJavaType(AppContexts.user().companyID(),
				this.toppagePartID,
				this.fileID,
				this.fileName,
				this.defClassAtr);
	}
}
