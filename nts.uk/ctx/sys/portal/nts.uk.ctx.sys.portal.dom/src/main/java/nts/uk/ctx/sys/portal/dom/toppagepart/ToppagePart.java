package nts.uk.ctx.sys.portal.dom.toppagepart;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.portal.dom.toppagepart.primitive.ToppagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.primitive.ToppagePartName;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;

/**
 * @author LamDT
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ToppagePart extends DomainObject {

	/** Company ID */
	String companyID;

	/** Toppage Part GUID */
	String toppagePartID;

	/** ToppagePart Code */
	ToppagePartCode toppagePartCode;

	/** ToppagePart Name */
	ToppagePartName toppagePartName;

	/** Enum ToppagePart Type */
	TopPagePartType toppagePartType;

	/** Size */
	Size size;

	public static ToppagePart createFromJavaType(String companyID, String toppagePartID, String toppagePartCode, String toppagePartName, int toppagePartType, int width, int height){
		return new ToppagePart(
			companyID, toppagePartID,
			new ToppagePartCode(toppagePartCode),
			new ToppagePartName(toppagePartName),
			EnumAdaptor.valueOf(toppagePartType, TopPagePartType.class),
			Size.createFromJavaType(width, height)
		);
	}
}