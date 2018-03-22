package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Size;
/**
 * 選択ウィジェット
 * @author phongtq
 *
 */
@Getter
public class OptionalWidget extends TopPagePart {

	public OptionalWidget(String companyID, String toppagePartID, TopPagePartCode topPagePartCode,
			TopPagePartName topPagePartName, TopPagePartType topPagePartType, Size size) {
		super(companyID, toppagePartID, topPagePartCode, topPagePartName, topPagePartType, size);

	}

	public static OptionalWidget createFromJavaType(String companyID,String toppagePartID, String topPagePartCode, String topPagePartName, int topPagePartType, int width, int height){
		
		return new OptionalWidget(companyID, toppagePartID, 
				new TopPagePartCode(topPagePartCode), 
				new TopPagePartName(topPagePartName), 
				EnumAdaptor.valueOf(topPagePartType, TopPagePartType.class), 
				Size.createFromJavaType(width, height));
	}
}
