/**
 * 
 */
package command.maintenancelayout;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayout;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
@Transactional
public class AddMaintenanceLayoutCommandHandler extends CommandHandler<AddMaintenanceLayoutCommand> {

	@Inject
	private MaintenanceLayoutRepository maintenanceLayoutRepository;

	@Override
	protected void handle(CommandHandlerContext<AddMaintenanceLayoutCommand> context) {

		AddMaintenanceLayoutCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();

		// check exists Layout
		Optional<MaintenanceLayout> maintenanceLayout = maintenanceLayoutRepository
				.checkExit(command.getMaintenanceLayoutID());
		if (maintenanceLayout.isPresent()) {
			throw new BusinessException("ER005");
		}

	}
}
