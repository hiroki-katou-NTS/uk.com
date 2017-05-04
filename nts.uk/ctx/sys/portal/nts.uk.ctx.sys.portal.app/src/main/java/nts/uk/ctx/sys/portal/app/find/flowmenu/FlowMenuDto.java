/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.find.flowmenu;

import lombok.Value;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuTopPagePart;

@Value
public class FlowMenuDto {
			
	private String toppagePartID;
		
	private String fileName;
	
	private int defClassAtr;
	
	private String code;
	
	private String name;
	
	private int type;
	
	private int widthSize;
	
	private int heightSize;
	
	public static FlowMenuDto fromDomain (FlowMenuTopPagePart domain) {
		 return new FlowMenuDto (
				 domain.getToppagePartID(),
				 domain.getFileName().v(),
				 domain.getDefClassAtr().value,
				 domain.getCode().v(),
				 domain.getName().v(),
				 domain.getType().value,
				 domain.getSize().getWidth().v(),
				 domain.getSize().getHeight().v());
	}
}
