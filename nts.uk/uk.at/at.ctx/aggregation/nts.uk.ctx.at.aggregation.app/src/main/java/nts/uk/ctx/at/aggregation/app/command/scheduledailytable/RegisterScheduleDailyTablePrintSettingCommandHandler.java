package nts.uk.ctx.at.aggregation.app.command.scheduledailytable;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableCode;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RegisterScheduleDailyTablePrintSettingCommandHandler extends CommandHandler<ScheduleDailyTablePrintSettingCommand> {
    @Inject
    private ScheduleDailyTableRepository repository;

    @Override
    protected void handle(CommandHandlerContext<ScheduleDailyTablePrintSettingCommand> commandHandlerContext) {
        ScheduleDailyTablePrintSettingCommand command = commandHandlerContext.getCommand();
        if (command.isNewMode()) {
            String companyId = AppContexts.user().companyId();
            repository.get(companyId, new ScheduleDailyTableCode(command.getCode())).ifPresent(domain -> {
                throw new BusinessException("Msg_3");
            });
            repository.insert(command.toDomain());
        } else {
            repository.update(command.toDomain());
        }
    }
}
