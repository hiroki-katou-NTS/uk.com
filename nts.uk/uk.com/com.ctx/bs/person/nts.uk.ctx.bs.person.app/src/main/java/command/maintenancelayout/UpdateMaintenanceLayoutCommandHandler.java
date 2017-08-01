/**
 * 
 */
package command.maintenancelayout;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayoutRepository;

/**
 * @author laitv
 *
 */
@Stateless
@Transactional
public class UpdateMaintenanceLayoutCommandHandler extends CommandHandler<UpdateMaintenanceLayoutCommand>{
	
	@Inject
	private MaintenanceLayoutRepository maintenanceLayoutRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateMaintenanceLayoutCommand> context) {
		// TODO Auto-generated method stub
		
	}
}
