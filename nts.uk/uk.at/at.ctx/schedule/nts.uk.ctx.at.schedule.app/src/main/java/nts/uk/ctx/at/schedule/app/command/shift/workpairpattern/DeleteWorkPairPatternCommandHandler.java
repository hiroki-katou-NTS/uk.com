package nts.uk.ctx.at.schedule.app.command.shift.workpairpattern;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkPairPatternRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class DeleteWorkPairPatternCommandHandler extends CommandHandler<DeletePatternCommand> {

	@Inject
	private WorkPairPatternRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeletePatternCommand> context) {
		DeletePatternCommand command = context.getCommand();
		if (StringUtil.isNullOrEmpty(command.getWorkplaceId(), true)) {
			String companyId = AppContexts.user().companyId();
			repo.removeComWorkPairPattern(companyId, command.getGroupNo());
		} else {
			String workplaceId = command.getWorkplaceId();
			repo.removeWorkplaceWorkPairPattern(workplaceId, command.getGroupNo());
		}
	}

}
