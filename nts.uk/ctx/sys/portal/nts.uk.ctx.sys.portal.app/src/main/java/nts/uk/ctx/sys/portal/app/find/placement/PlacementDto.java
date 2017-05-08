package nts.uk.ctx.sys.portal.app.find.placement;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.externalurl.ExternalUrl;
import nts.uk.shr.com.context.AppContexts;

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
	
	/** Part: TopPagePart & ExternalUrl */
	private PlacementPartDto placementPartDto;

}