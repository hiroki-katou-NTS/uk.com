package nts.uk.ctx.sys.portal.app.find.toppagepart;

import lombok.Value;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;

/**
 * @author LamDT
 */
@Value
public class TopPagePartDto {

	/** Company ID */
	private String companyID;

	/** TopPage Part GUID */
	private String topPagePartID;

	/** Code */
	private String code;

	/** Name */
	private String name;

	/** TopPage Part Type */
	private int type;
	
	/** Width */
	private int width;

	/** Height */
	private int height;

	/** Convert Domain to Dto */
	public static TopPagePartDto fromDomain(TopPagePart topPagePart) {
		return new TopPagePartDto(
			topPagePart.getCompanyID(), topPagePart.getToppagePartID(),
			topPagePart.getCode().v(), topPagePart.getName().v(),
			topPagePart.getType().value, topPagePart.getWidth().v(), topPagePart.getHeight().v()
		);
	}
	
}