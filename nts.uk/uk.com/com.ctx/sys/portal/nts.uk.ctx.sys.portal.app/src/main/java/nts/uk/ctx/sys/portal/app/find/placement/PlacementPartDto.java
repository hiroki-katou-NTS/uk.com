package nts.uk.ctx.sys.portal.app.find.placement;

import lombok.Value;

/**
 * @author LamDT
 */
@Value
public class PlacementPartDto {

	/** Width */
	private int width;

	/** Height */
	private int height;
	
	/** TopPage Part GUID */
	private String topPagePartID;
	
	/** Code */
	private String code;

	/** Name */
	private String name;

	/** TopPage Part Type */
	private Integer type;
	
	/** External Url */
	private String externalUrl;
	
}