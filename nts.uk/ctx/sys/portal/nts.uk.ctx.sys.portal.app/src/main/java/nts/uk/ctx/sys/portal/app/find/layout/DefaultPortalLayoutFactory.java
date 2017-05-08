package nts.uk.ctx.sys.portal.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.app.find.placement.PlacementDto;
import nts.uk.ctx.sys.portal.app.find.placement.PlacementPartDto;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.externalurl.ExternalUrl;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author LamDT 
 */
@Stateless
public class DefaultPortalLayoutFactory {

	@Inject
	private TopPagePartRepository topPagePartRepository;

	/** Create LayoutDto from Layout Domain
	 * 
	 * @param
	 * @return LayoutDto
	 */
	public LayoutDto buildLayoutDto(Layout layout, List<Placement> placements) {
		List<PlacementDto> placementDtos = buildPlacementDto(placements);
		return new LayoutDto(layout.getCompanyID(), layout.getLayoutID(), layout.getPgType().value, placementDtos);
	}

	/** Create List PlacementDto from List Placement Domain
	 * 
	 * @param
	 * @return List PlacementDto
	 */
	public List<PlacementDto> buildPlacementDto(List<Placement> placements) {
		List<PlacementDto> placementDtos = new ArrayList<PlacementDto>();
		for (Placement placement : placements) {
			placementDtos.add(buildPlacementDto(placement));
		}
		return placementDtos;	
	}

	/** Create PlacementDto from Placement Domain
	 * 
	 * @param
	 * @return PlacementDto
	 */
	public PlacementDto buildPlacementDto(Placement placement) {
		if (placement.isExternalUrl()) {
			// ExternalUrl Part
			ExternalUrl externalUrl = placement.getExternalUrl().get();
			return new PlacementDto(
				placement.getCompanyID(), placement.getPlacementID(), placement.getLayoutID(),
				placement.getColumn().v(), placement.getRow().v(),
				fromExternalUrl(externalUrl));
		}
		else {
			// TopPage Part
			Optional<TopPagePart> topPagePart = topPagePartRepository.find(placement.getToppagePartID());
			if (topPagePart.isPresent())
				return new PlacementDto(
					placement.getCompanyID(), placement.getPlacementID(), placement.getLayoutID(),
					placement.getColumn().v(), placement.getRow().v(),
					fromTopPagePart(topPagePart.get()));
			return null;
		}
	}

	/** Create PlacementPartDto from TopPage Part Domain 
	 * 
	 * @param
	 * @return PlacementPartDto
	 */
	public PlacementPartDto fromTopPagePart(TopPagePart topPagePart) {
		return new PlacementPartDto(
			topPagePart.getCompanyID(),
			topPagePart.getWidth().v(), topPagePart.getHeight().v(), topPagePart.getToppagePartID(),
			topPagePart.getCode().v(), topPagePart.getName().v(), topPagePart.getType().value,
			null
		);
	}

	/** Create PlacementPartDto from ExternalUrl Domain
	 * 
	 * @param
	 * @return PlacementPartDto
	 */
	public PlacementPartDto fromExternalUrl(ExternalUrl externalUrl) {
		String companyID = AppContexts.user().companyID();
		return new PlacementPartDto(
			companyID, externalUrl.getWidth().v(), externalUrl.getHeight().v(),
			null, null, null, null,
			externalUrl.getUrl().v()
		);
	}

}