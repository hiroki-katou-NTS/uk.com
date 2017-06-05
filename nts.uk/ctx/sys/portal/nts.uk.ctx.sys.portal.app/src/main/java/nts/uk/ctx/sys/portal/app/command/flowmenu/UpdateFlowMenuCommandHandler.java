/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.flowmenu;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateFlowMenuCommandHandler extends CommandHandler<UpdateFlowMenuCommand> {
	
	@Inject
	public FlowMenuRepository repository;

	@Override
	protected void handle(CommandHandlerContext<UpdateFlowMenuCommand> context) {
		String companyId = AppContexts.user().companyId();
		UpdateFlowMenuCommand command = context.getCommand();
		
		// Check FlowMenu is Existence	
		Optional<FlowMenu> checkFlowMenu = repository.getFlowMenu(companyId, context.getCommand().getToppagePartID());
		if(!checkFlowMenu.isPresent()){
			throw new BusinessException("ER026");
		}
		
		// Update FLowMenu
		FlowMenu flowMenu = checkFlowMenu.get();
		flowMenu.setName(command.getTopPageName());
		flowMenu.setSize(command.getWidthSize(), command.getHeightSize());
		flowMenu.setFileID(command.getFileID());
		flowMenu.setDefClassAtr(command.getDefClassAtr());
		repository.update(flowMenu);
	}
}