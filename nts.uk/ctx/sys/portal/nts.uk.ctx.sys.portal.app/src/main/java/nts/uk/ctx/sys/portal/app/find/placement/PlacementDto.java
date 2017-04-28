package nts.uk.ctx.sys.portal.app.find.placement;

import lombok.Value;
import nts.uk.ctx.sys.portal.dom.placement.Placement;

/**
 * @author LamDT
 */
@Value
public class PlacementDto {
	
	/** Company ID */
	private String companyID;

	/** Placement GUID */
	private String placementID;

	/** Layout GUID */
	private String layoutID;

	/** Column */
	private int column;
	
	/** Row */
	private int row;
	
	/** Width */
	private Integer width;
	
	/** Height */
	private Integer height;
	
	/** External Url */
	private String externalUrl;
	
	/** TopPage Part GUID */
	private String topPagePartID;
	
	/** Create Dto from Domain */
	public static PlacementDto fromDomain(Placement placement) {
		// External Url information
		Integer width = placement.getExternalUrl().isPresent() ? placement.getExternalUrl().get().getWidth().v() : null;
		Integer height = placement.getExternalUrl().isPresent() ? placement.getExternalUrl().get().getHeight().v() : null;
		String externalUrl = placement.getExternalUrl().isPresent() ? placement.getExternalUrl().get().getUrl().v() : null;
		
		return new PlacementDto(
			placement.getCompanyID(), placement.getPlacementID(), placement.getLayoutID(),
			placement.getColumn().v(), placement.getRow().v(),
			width, height, externalUrl, placement.getToppagePartID()
		);
	}
}