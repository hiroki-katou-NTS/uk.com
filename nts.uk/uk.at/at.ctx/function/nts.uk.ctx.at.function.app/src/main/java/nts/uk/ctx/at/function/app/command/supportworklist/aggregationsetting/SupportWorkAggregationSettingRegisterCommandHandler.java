package nts.uk.ctx.at.function.app.command.supportworklist.aggregationsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SupportWorkAggregationSettingRegisterCommandHandler extends CommandHandler<SupportWorkAggregationSettingCommand> {
    @Inject
    private SupportWorkAggregationSettingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<SupportWorkAggregationSettingCommand> commandHandlerContext) {
        if (repository.get(AppContexts.user().companyId()).isPresent()) {
            repository.update(commandHandlerContext.getCommand().toDomain());
        } else {
            repository.insert(commandHandlerContext.getCommand().toDomain());
        }
    }
}
