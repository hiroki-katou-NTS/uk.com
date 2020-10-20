package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.banworktogether;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AddBanWorkTogetherCommandHandler extends CommandHandler<AddBanWorkTogetherCommand> {

    @Inject
    private BanWorkTogetherRepository banWorkTogetherRepo;

    @Override
    protected void handle(CommandHandlerContext<AddBanWorkTogetherCommand> context) {
        AddBanWorkTogetherCommand command = context.getCommand();

        
    }
}
