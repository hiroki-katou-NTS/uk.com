package nts.uk.ctx.at.aggregation.app.command.scheduledailytable;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableCode;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DeleteScheduleDailyTablePrintSettingCommandHandler extends CommandHandler<String> {
    @Inject
    private ScheduleDailyTableRepository repository;

    @Override
    protected void handle(CommandHandlerContext<String> commandHandlerContext) {
        String code = commandHandlerContext.getCommand();
        repository.delete(AppContexts.user().companyId(), new ScheduleDailyTableCode(code));
    }
}
