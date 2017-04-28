package nts.uk.ctx.sys.portal.app.find.placement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.app.find.placement.PlacementDto;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;

/**
 * @author LamDT
 */
@Stateless
public class PortalPlacementFinder {
	@Inject
	private PlacementRepository placementRepository;
	
	/**
	 * Find Placement by ID
	 * @param placementID
	 * @return Optional Placement
	 */
	public Optional<PlacementDto> findPlacement(String placementID) {
		return placementRepository.find(placementID).map(item -> PlacementDto.fromDomain(item));
	}

}