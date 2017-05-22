package nts.uk.ctx.sys.portal.app.find.layout;

import java.util.List;

import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;

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
