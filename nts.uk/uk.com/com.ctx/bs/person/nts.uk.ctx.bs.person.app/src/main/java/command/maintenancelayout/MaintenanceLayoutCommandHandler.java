/**
 * 
 */
package command.maintenancelayout;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
@Transactional
public class MaintenanceLayoutCommandHandler extends CommandHandler<MaintenanceLayoutCommand> {

	@Inject
	private MaintenanceLayoutRepository repo;

	@Override
	protected void handle(CommandHandlerContext<MaintenanceLayoutCommand> context) {
		String companyId = AppContexts.user().companyId();
		MaintenanceLayoutCommand command = context.getCommand();

		String layoutId = IdentifierUtil.randomUniqueId();

		switch (command.getAction()) {
		case 0: // insert
			// set new layoutID for insert object
			command.setId(layoutId);

			break;
		case 1: // update

			break;
		case 2: // copy
			
			break;
		case 3: // clone and override
			
			break;
		case 4: // remove
			
			break;
		}

	}
}
