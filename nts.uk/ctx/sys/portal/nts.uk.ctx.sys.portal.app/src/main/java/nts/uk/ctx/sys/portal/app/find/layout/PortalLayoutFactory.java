package nts.uk.ctx.sys.portal.app.find.layout;

import java.util.List;

import nts.uk.ctx.sys.portal.app.find.placement.PlacementDto;
import nts.uk.ctx.sys.portal.app.find.placement.PlacementPartDto;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.externalurl.ExternalUrl;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;

/**
 * @author LamDT 
 */
public interface PortalLayoutFactory {

	/** 
	 * Create LayoutDto from Layout Domain
	 * 
	 * @param
	 * @return LayoutDto
	 */
	LayoutDto buildLayoutDto(Layout layout, List<Placement> placements);
}
