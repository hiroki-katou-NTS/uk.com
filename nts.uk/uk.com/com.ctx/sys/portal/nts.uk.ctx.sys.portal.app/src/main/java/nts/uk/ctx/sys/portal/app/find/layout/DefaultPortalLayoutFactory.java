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
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;

/**
 * @author LamDT 
 */
@Stateless
public class DefaultPortalLayoutFactory implements PortalLayoutFactory {
	
	@Inject TopPagePartService topPagePartService;

	@Override
	public LayoutDto buildLayoutDto(Layout layout, List<Placement> placements) {		
		List<PlacementDto> placementDtos = buildPlacementDto(layout, placements);
		return new LayoutDto(layout.getCompanyID(), layout.getLayoutID(), layout.getPgType().value, placementDtos);
	}

	private List<PlacementDto> buildPlacementDto(Layout layout, List<Placement> placements) {
		List<TopPagePart> activeTopPageParts = topPagePartService.getAllActiveTopPagePart(layout.getCompanyID(), layout.getPgType());
		
		List<PlacementDto> placementDtos = new ArrayList<PlacementDto>();
		for (Placement placement : placements) {
			if (placement.isExternalUrl()) {
				ExternalUrl externalUrl = placement.getExternalUrl().get();
				placementDtos.add(new PlacementDto(
					placement.getPlacementID(), placement.getLayoutID(),
					placement.getColumn().v(), placement.getRow().v(),
					PlacementPartDto.createExternalUrl(externalUrl)));
			}
			else {
				Optional<TopPagePart> topPagePart = activeTopPageParts.stream().filter(c -> c.getToppagePartID().equals(placement.getToppagePartID())).findFirst();
				if (topPagePart.isPresent())
					placementDtos.add(new PlacementDto(
						placement.getPlacementID(), placement.getLayoutID(),
						placement.getColumn().v(), placement.getRow().v(),
						PlacementPartDto.createFromTopPagePart(topPagePart.get())));
			}
		}
		return placementDtos;
	}

}