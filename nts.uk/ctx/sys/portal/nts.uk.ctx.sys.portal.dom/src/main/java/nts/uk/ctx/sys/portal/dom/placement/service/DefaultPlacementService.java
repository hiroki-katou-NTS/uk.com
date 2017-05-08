package nts.uk.ctx.sys.portal.dom.placement.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.placement.externalurl.ExternalUrl;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author LamDT
 */
@Stateless
public class DefaultPlacementService implements PlacementService {
	
	@Inject
	private LayoutRepository layoutRepository;
	
	@Inject
	PlacementRepository placementRepository;

	@Override
	public boolean isExist(String placementID) {
		Optional<Placement> placement = placementRepository.find(placementID);
		return placement.isPresent();
	}

	@Override
	public List<String> copyPlacementByLayout(String layoutID) {
		Optional<Layout> layout = layoutRepository.find(layoutID);
		if (!layout.isPresent())
			return Collections.emptyList();
		
		List<Placement> placements = placementRepository.findByLayout(layoutID);
		List<String> placementIDs = new ArrayList<String>();
		for (Placement placement : placements) {
			String placementID = copyPlacement(placement.getPlacementID());
			placementIDs.add(placementID);
		}
		return placementIDs;
	}

	@Override
	public String copyPlacement(String placementID) {
		if (placementID.equals(null) || !isExist(placementID))
			return null;
		
		String companyID = AppContexts.user().companyID();
		String newPlacementID = IdentifierUtil.randomUniqueId();
		Placement placement = placementRepository.find(placementID).get();
		Optional<ExternalUrl> externalUrl = placement.getExternalUrl();
		String url = null;
		Integer width = null;
		Integer height = null;
		if (externalUrl.isPresent()) {
			url = externalUrl.get().getUrl().v();
			width = externalUrl.get().getWidth().v();
			height = externalUrl.get().getHeight().v();
		}
		Placement newPlacement = Placement.createFromJavaType(
				companyID, newPlacementID, placement.getLayoutID(), placement.getToppagePartID(),
				placement.getColumn().v(), placement.getRow().v(),
				url, width, height);
		placementRepository.add(newPlacement);
		
		return newPlacementID;

	}

}
