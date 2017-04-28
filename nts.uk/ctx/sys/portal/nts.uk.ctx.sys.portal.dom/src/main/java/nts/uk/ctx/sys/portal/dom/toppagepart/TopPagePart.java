package nts.uk.ctx.sys.portal.dom.toppagepart;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Height;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Size;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Width;

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
	TopPagePartCode code;

	/** ToppagePart Name */
	TopPagePartName name;

	/** Enum ToppagePart Type */
	TopPagePartType type;

	/** Size */
	Size size;
	
	/** Quickly get Size.width */
	public Width getWidth() {
		return this.size.getWidth();
	}

	/** Quickly get Size.height */
	public Height getHeight() {
		return this.size.getHeight();
	}

	/**
	 * Create TopPagePart from Java type 
	 * 
	 * @param
	 * @return TopPagePart
	 **/
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