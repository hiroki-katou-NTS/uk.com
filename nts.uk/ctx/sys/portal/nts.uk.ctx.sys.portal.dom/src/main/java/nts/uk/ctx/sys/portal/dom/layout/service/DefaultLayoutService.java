package nts.uk.ctx.sys.portal.dom.layout.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
import nts.uk.ctx.sys.portal.dom.placement.service.PlacementService;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author LamDT
 */
@Stateless
public class DefaultLayoutService implements LayoutService {

	@Inject
	private LayoutRepository layoutRepository;
	
	@Inject
	private PlacementService placementService;

	@Override
	public boolean isExist(String layoutID) {
		Optional<Layout> layout = layoutRepository.find(layoutID);
		return layout.isPresent();
	}

	@Override
	public String copyTopPageLayout(String layoutID) {
		if (layoutID.equals(null) || !isExist(layoutID))
			return null;
		
		String companyID = AppContexts.user().companyID();
		String newLayoutID = IdentifierUtil.randomUniqueId();
		
		Layout newLayout = Layout.createFromJavaType(companyID, layoutID, 0);
		layoutRepository.add(newLayout);
		
		placementService.copyPlacementByLayout(newLayoutID);
		
		return newLayoutID;
	}

}
