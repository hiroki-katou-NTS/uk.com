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
	
	/** Create Dto from Domain */
	public static TopPagePartDto fromDomain(TopPagePart domain) {
		return new TopPagePartDto(
			domain.getCompanyID(), domain.getToppagePartID(),
			domain.getCode().v(), domain.getName().v(), domain.getType().value,
			domain.getWidth().v(), domain.getHeight().v()
		);
	}
}