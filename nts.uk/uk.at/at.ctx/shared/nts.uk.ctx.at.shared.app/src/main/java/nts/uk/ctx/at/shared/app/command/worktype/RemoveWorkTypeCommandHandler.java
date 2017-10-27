package nts.uk.ctx.at.shared.app.command.worktype;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveWorkTypeCommandHandler extends CommandHandler<RemoveWorkTypeCommand> {

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveWorkTypeCommand> context) {
		String companyId = AppContexts.user().companyId();
		RemoveWorkTypeCommand command = context.getCommand();
		if (workTypeRepository.findByPK(companyId, command.getWorkTypeCd()).isPresent()) {
			workTypeRepository.remove(companyId, command.getWorkTypeCd());
			workTypeRepository.removeWorkTypeSet(companyId, command.getWorkTypeCd());
		}
	}
}
