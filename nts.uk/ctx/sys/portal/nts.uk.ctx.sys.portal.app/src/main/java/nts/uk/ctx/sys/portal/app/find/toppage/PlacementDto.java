/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.find.toppage;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;

/**
 * The Class PlacementDto.
 */
@Data
public class PlacementDto {

	/** The row. */
	private Integer row;

	/** The column. */
	private Integer column;

	/** The top page part dto. */
	private TopPagePartDto topPagePartDto;

	/**
	 * From domain.
	 *
	 * @param placement the placement
	 * @param topPagePart the top page part
	 * @return the placement dto
	 */
	public static PlacementDto fromDomain(Placement placement, TopPagePart topPagePart) {
		PlacementDto placementDto = new PlacementDto();
		placementDto.row = placement.getRow().v();
		placementDto.column = placement.getColumn().v();
		placementDto.topPagePartDto = TopPagePartDto.fromDomain(topPagePart);
		return placementDto;
	}
}
