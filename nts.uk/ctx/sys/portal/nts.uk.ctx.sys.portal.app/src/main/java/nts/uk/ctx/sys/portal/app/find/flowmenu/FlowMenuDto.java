/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.find.flowmenu;

import lombok.Value;import nts.uk.ctx.sys.portal.dom.flowmenu.DefClassAtr;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;

@Value
public class FlowMenuDto {
		
	private String companyID;
	
	private String toppagePartID;
	
	private String fileID;
	
	private String fileName;
	
	private int defClassAtr;
	
	public static FlowMenuDto fromDomain (FlowMenu domain) {
		 return new FlowMenuDto (
				 domain.getCompanyID(),
				 domain.getToppagePartID(),
				 domain.getFileID(),
				 domain.getFileName().v(),
				 domain.getDefClassAtr().compareTo(DefClassAtr.Default ));
	}
}
