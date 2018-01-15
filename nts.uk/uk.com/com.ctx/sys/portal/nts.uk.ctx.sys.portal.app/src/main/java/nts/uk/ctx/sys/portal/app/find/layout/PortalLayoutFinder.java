package nts.uk.ctx.sys.portal.app.find.layout;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;

/**
 * @author LamDT
 */
@Stateless
public class PortalLayoutFinder {

	@Inject
	private LayoutRepository layoutRepository;

	@Inject
	private PlacementRepository placementRepository;
	
	@Inject
	private PortalLayoutFactory portalLayoutFactory;

	/**
	 * Find Layout by ID
	 * 
	 * @param layoutID
	 * @return Optional Layout
	 */
	public LayoutDto findLayout(String layoutID) {
		Optional<Layout> layout = layoutRepository.find(layoutID);
		if (layout.isPresent()) {
			List<Placement> placements = placementRepository.findByLayout(layoutID);
			return portalLayoutFactory.buildLayoutDto(layout.get(), placements);
		}
		return null;
	}
}