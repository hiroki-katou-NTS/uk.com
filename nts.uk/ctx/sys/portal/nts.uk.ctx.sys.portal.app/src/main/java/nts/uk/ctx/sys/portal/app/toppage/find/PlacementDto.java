package nts.uk.ctx.sys.portal.app.toppage.find;

import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;

public class PlacementDto {

	/** The row. */
	Integer row;

	/** The column. */
	Integer column;

	TopPagePartDto topPagePartDto;

	public static PlacementDto fromDomain(Placement placement, TopPagePart topPagePart) {
		PlacementDto placementDto = new PlacementDto();
		placementDto.row = placement.getRow().v();
		placementDto.column = placement.getColumn().v();
		placementDto.topPagePartDto = TopPagePartDto.fromDomain(topPagePart);
		return placementDto;
	}
}
