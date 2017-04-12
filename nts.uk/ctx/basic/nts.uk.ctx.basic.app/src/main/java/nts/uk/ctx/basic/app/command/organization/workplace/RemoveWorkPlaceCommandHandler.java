package nts.uk.ctx.basic.app.command.organization.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceCode;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemoveWorkPlaceCommandHandler extends CommandHandler<RemoveWorkPlaceCommand> {

	@Inject
	private WorkPlaceRepository workPlaceRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveWorkPlaceCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		workPlaceRepository.remove(companyCode, new WorkPlaceCode(context.getCommand().getWorkPlaceCode()),
				context.getCommand().getHistoryId());
	}

}

