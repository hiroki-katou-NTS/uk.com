package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.banworktogether;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogetherRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UpdateBanWorkTogetherCommandHandler extends CommandHandler<UpdateBanWorkTogetherCommand> {

    @Inject
    private WorkTogetherRepository workTogetherRepo;

    @Override
    protected void handle(CommandHandlerContext<UpdateBanWorkTogetherCommand> context) {
        UpdateBanWorkTogetherCommand command = context.getCommand();
    }
}
