package nts.uk.ctx.sys.portal.app.command.layout;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.portal.app.command.placement.PortalPlacementCommand;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.service.LayoutService;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.placement.service.PlacementService;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author LamDT
 */
@Stateless
@Transactional
public class RegistryPortalLayoutCommandHandler extends CommandHandlerWithResult<RegistryPortalLayoutCommand, String> {

	@Inject
	private PlacementRepository placementRepository;

	@Inject
	private LayoutService layoutService;

	@Inject
	private PlacementService placementService;

	@Override
	protected String handle(CommandHandlerContext<RegistryPortalLayoutCommand> context) {
		// Data
		String companyID = AppContexts.user().companyId();
		RegistryPortalLayoutCommand command = context.getCommand();
		PortalLayoutCommand layoutCommand = command.getPortalLayoutCommand();
		List<PortalPlacementCommand> placementCommands = command.getListPortalPlacementCommand();
		String layoutID = layoutCommand.getLayoutID();

		// Create Layout if not exist
		if (!layoutService.isExist(layoutID)) {
			if (StringUtil.isNullOrEmpty(layoutID, true)) {
				layoutID = IdentifierUtil.randomUniqueId();
			}
			Layout layout = layoutCommand.toDomain(layoutID);
			layoutService.createLayout(companyID, layoutCommand.getParentCode(), layoutCommand.getPgType(), layout);
		}

		// Placements delete
		placementService.deletePlacementByLayout(companyID, layoutID);

		// Placements registry
		List<Placement> placements = new ArrayList<Placement>();
		for (PortalPlacementCommand placementCommand : placementCommands) {
			placementCommand.setLayoutID(layoutID);
			placements.add(placementCommand.toDomain(IdentifierUtil.randomUniqueId()));
		}
		placementRepository.addAll(placements);
		
		return layoutID;
	}

}