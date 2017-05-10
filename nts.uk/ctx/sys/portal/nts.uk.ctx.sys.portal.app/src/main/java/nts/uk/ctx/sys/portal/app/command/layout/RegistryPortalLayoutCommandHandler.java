package nts.uk.ctx.sys.portal.app.command.layout;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.app.command.placement.PortalPlacementCommand;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
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
public class RegistryPortalLayoutCommandHandler extends CommandHandler<RegistryPortalLayoutCommand> {

	@Inject
	private LayoutRepository layoutRepository;

	@Inject
	private PlacementRepository placementRepository;

	@Inject
	private LayoutService layoutService;

	@Inject
	private PlacementService placementService;

	@Override
	protected void handle(CommandHandlerContext<RegistryPortalLayoutCommand> context) {
		// Data
		String companyID = AppContexts.user().companyId();
		RegistryPortalLayoutCommand command = context.getCommand();
		PortalLayoutCommand layoutCommand = command.getPortalLayoutCommand();
		List<PortalPlacementCommand> placementCommands = command.getListPortalPlacementCommand();
		String layoutID = layoutCommand.getLayoutID();

		// Layout registry if not exist
		if (!layoutService.isExist(layoutID)) {
			layoutID = IdentifierUtil.randomUniqueId();
			layoutRepository.add(layoutCommand.toDomain(layoutID));
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
	}

}