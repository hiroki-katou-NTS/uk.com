package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.sys.portal.app.find.flowmenu.FlowMenuDto;
import nts.uk.ctx.sys.portal.app.find.placement.PlacementDto;
@Value
public class LayoutForMyPageDto {
	/** Company ID */
	private String companyID;

	/** Layout GUID */
	private String layoutID;

	/** Enum PG Type */
	private int pgType;
	/**flowMenu*/
	private List<FlowMenuDto> flowMenu;
	/**placement*/
	private List<PlacementDto> placements;
}
