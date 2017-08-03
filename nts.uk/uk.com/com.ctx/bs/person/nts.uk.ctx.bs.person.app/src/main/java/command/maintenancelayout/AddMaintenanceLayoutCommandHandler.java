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
import nts.gul.text.IdentifierUtil;
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
		String companyId = AppContexts.user().companyId();
		String maintenanceLayoutID = IdentifierUtil.randomUniqueId();

		// check exists Layout by companyId and LayoutCOde
		if (maintenanceLayoutRepository.checkExit(companyId, command.layoutCode)) {
			throw new BusinessException("Msg_3");
		}

		// create from java type
		MaintenanceLayout domain = MaintenanceLayout.createFromJavaType(companyId, maintenanceLayoutID,
				command.getLayoutCode(), command.getLayoutName());

		maintenanceLayoutRepository.add(domain);
	}

}
