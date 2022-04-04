package nts.uk.ctx.at.function.app.command.supportworklist.outputsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkListOutputSettingRepository;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SupportWorkOutputSettingDeleteCommandHandler extends CommandHandler<String> {
    @Inject
    private SupportWorkListOutputSettingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<String> commandHandlerContext) {
        repository.delete(AppContexts.user().companyId(), new SupportWorkOutputCode(commandHandlerContext.getCommand()));
    }
}
