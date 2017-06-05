package nts.uk.ctx.sys.portal.dom.toppagepart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Height;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Size;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Width;

/**
 * @author LamDT
 */
@Getter
@AllArgsConstructor
public class TopPagePart extends AggregateRoot {

	/** Company ID */
	private String companyID;

	/** Toppage Part GUID */
	private String toppagePartID;

	/** ToppagePart Code */
	private TopPagePartCode code;

	/** ToppagePart Name */
	private TopPagePartName name;

	/** Enum ToppagePart Type */
	private TopPagePartType type;

	/** Size */
	private Size size;
	
    /**
     * Create TopPagePart from Java type 
     * 
     * @param
     * @return TopPagePart
     **/
    public static TopPagePart createFromJavaType(String companyID, String topPagePartID,
    		String topPagePartCode, String topPagePartName, int topPagePartType,
    		int width, int height){
        return new TopPagePart(
            companyID, topPagePartID,
            new TopPagePartCode(topPagePartCode),
            new TopPagePartName(topPagePartName),
            EnumAdaptor.valueOf(topPagePartType, TopPagePartType.class),
            Size.createFromJavaType(width, height)
        );
    }
	
	/** Quickly get Size.width */
	public Width getWidth() {
		return this.size.getWidth();
	}

	/** Quickly get Size.height */
	public Height getHeight() {
		return this.size.getHeight();
	}
	
	/** Set TopPagePart Name */
	public void setName(String name) {
		this.name = new TopPagePartName(name);
	}
	
	/** Set TopPagePart Type */
	public void setType(int type) {
		this.type = EnumAdaptor.valueOf(type, TopPagePartType.class);
	}
	
	/** Set TopPagePart Size */
	public void setSize(int width, int height) {
		this.size = Size.createFromJavaType(width, height);
	}
}