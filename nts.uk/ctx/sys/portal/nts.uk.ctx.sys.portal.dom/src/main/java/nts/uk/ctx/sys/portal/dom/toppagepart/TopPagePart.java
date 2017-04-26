package nts.uk.ctx.sys.portal.dom.toppagepart;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.portal.dom.toppagepart.primitive.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.primitive.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;

/**
 * @author LamDT
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class TopPagePart extends DomainObject {

	/** Company ID */
	String companyID;

	/** Toppage Part GUID */
	String toppagePartID;

	/** ToppagePart Code */
	TopPagePartCode topPagePartCode;

	/** ToppagePart Name */
	TopPagePartName topPagePartName;

	/** Enum ToppagePart Type */
	TopPagePartType topPagePartType;

	/** Size */
	Size size;

	public static TopPagePart createFromJavaType(String companyID, String topPagePartID, String topPagePartCode, String topPagePartName, int topPagePartType, int width, int height){
		return new TopPagePart(
			companyID, topPagePartID,
			new TopPagePartCode(topPagePartCode),
			new TopPagePartName(topPagePartName),
			EnumAdaptor.valueOf(topPagePartType, TopPagePartType.class),
			Size.createFromJavaType(width, height)
		);
	}
}