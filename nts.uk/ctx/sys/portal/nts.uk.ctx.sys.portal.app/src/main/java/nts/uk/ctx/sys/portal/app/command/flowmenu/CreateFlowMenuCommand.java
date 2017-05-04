/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.flowmenu;

import lombok.Getter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.shr.com.context.AppContexts;

@Getter
public class CreateFlowMenuCommand {
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
	
	public FlowMenu toDomain (String topPagePartId){
		return FlowMenu.createFromJavaType(AppContexts.user().companyID(),
				topPagePartId,
				IdentifierUtil.randomUniqueId(),
				this.fileName,
				0);
	}
	
	public TopPagePart toTopPagePart(String topPagePartId){
		return TopPagePart.createFromJavaType(AppContexts.user().companyID(),
				topPagePartId, 
				this.topPageCode,
				this.topPageName,
				2,
				this.widthSize,
				this.heightSize);
		
	}

}
