/**
 * 
 */
package command.maintenancelayout;

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
public class UpdateMaintenanceLayoutCommandHandler extends CommandHandler<UpdateMaintenanceLayoutCommand> {

	@Inject
	private MaintenanceLayoutRepository maintenanceLayoutRepository;

	@Inject
	private AddMaintenanceLayoutCommandHandler addLayout;
	
	@Inject
	private RemoveMaintenanceLayoutCommandHandler removeLayout;

	@Override
	protected void handle(CommandHandlerContext<UpdateMaintenanceLayoutCommand> context) {

		UpdateMaintenanceLayoutCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String maintenanceLayoutID = IdentifierUtil.randomUniqueId();

		// check Exit Layout by companyId and LayoutCode
		if (maintenanceLayoutRepository.checkExit(companyId, command.layoutCode_des)) {
			// truong hop có tồn tại
			// 1. không check ô ghi đè dữ liệu
			if (!command.checked) {
				throw new BusinessException("Msg_3");
			} else if (command.checked) {
				// 2. có check ô ghi đè dữ liệu
				

			}
		} else {
			// truong hop không tồn tại
			// create from java type
			MaintenanceLayout domain = MaintenanceLayout.createFromJavaType(companyId, maintenanceLayoutID,
					command.getLayoutCode_des(), command.getLayoutName());
			maintenanceLayoutRepository.add(domain);
			
			

		}

	}
}
