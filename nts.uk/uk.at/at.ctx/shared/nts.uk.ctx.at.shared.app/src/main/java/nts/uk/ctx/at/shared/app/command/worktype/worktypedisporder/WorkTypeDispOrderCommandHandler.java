package nts.uk.ctx.at.shared.app.command.worktype.worktypedisporder;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktype.worktypedisporder.WorkTypeDispOrderRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Transactional
@Stateless
public class WorkTypeDispOrderCommandHandler extends CommandHandler<List<WorkTypeDispOrderCommand>> {
	@Inject
	private WorkTypeDispOrderRepository workTypeDisporderRepository;
	
	@Override
	protected void handle(CommandHandlerContext<List<WorkTypeDispOrderCommand>> context) {
		List<WorkTypeDispOrderCommand> command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		workTypeDisporderRepository.remove(companyId);
		
		for (WorkTypeDispOrderCommand workTypeDispOrderCommand : command) {
			workTypeDisporderRepository.add(workTypeDispOrderCommand.toDomain());
		}
	}
}
