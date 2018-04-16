package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Size;

public class StandardWidget extends TopPagePart {

	public StandardWidget(String companyID, String toppagePartID, TopPagePartCode code, TopPagePartName name, TopPagePartType type, Size size) {
		super(companyID, toppagePartID, code, name, type, size);
		// TODO Auto-generated constructor stub
	}

	public static StandardWidget createFromJavaType(String companyID, String toppagePartID, String code, String name, int type, int width, int height) {
       return new StandardWidget (
    		   companyID,
    		   toppagePartID,
    		   new TopPagePartCode(code),
    		   new TopPagePartName(name),
    		   EnumAdaptor.valueOf(type, TopPagePartType.class), 
    		   Size.createFromJavaType(width, height));
	}

}
