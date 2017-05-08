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
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
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

	@Override
	protected void handle(CommandHandlerContext<RegistryPortalLayoutCommand> context) {
		// Data
		String companyID = AppContexts.user().companyID();
		RegistryPortalLayoutCommand command = context.getCommand();
		PortalLayoutCommand layoutCommand = command.getPortalLayoutCommand();
		List<PortalPlacementCommand> placementCommands = command.getListPortalPlacementCommand();

		// Remove old data
		layoutRepository.remove(companyID, layoutCommand.toDomain().getLayoutID());
		List<String> placementIDs = new ArrayList<String>();
		for (PortalPlacementCommand placementCommand : placementCommands) {
			placementIDs.add(placementCommand.getPlacementID());
		}
		placementRepository.removeAll(companyID, placementIDs);

		// Layout registry
		layoutRepository.add(layoutCommand.toDomain(IdentifierUtil.randomUniqueId()));

		// Placements registry
		List<Placement> placements = new ArrayList<Placement>();
		for (PortalPlacementCommand placementCommand : placementCommands) {
			placements.add(placementCommand.toDomain(IdentifierUtil.randomUniqueId()));
		}
		placementRepository.addAll(placements);

	}

}