package nts.uk.ctx.sys.portal.app.toppage.find;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;

/**
 * The Class PlacementDto.
 */
@Data
public class PlacementDto {

	/** The row. */
	Integer row;

	/** The column. */
	Integer column;

	/** The top page part dto. */
	TopPagePartDto topPagePartDto;

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
