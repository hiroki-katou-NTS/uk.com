package nts.uk.ctx.at.function.app.command.supportworklist.outputsetting;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkListOutputSetting;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkListOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class SupportWorkOutputSettingRegisterCommandHandler extends CommandHandler<SupportWorkOutputSettingCommand> {
    @Inject
    private SupportWorkListOutputSettingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<SupportWorkOutputSettingCommand> commandHandlerContext) {
        SupportWorkListOutputSetting domain = commandHandlerContext.getCommand().toDomain();
        Optional<SupportWorkListOutputSetting> check = repository.get(AppContexts.user().companyId(), domain.getCode());
        if (check.isPresent()) {
            if (commandHandlerContext.getCommand().getMode() == 0)
                throw new BusinessException("Msg_3243");
            repository.update(domain);
        } else {
            repository.insert(domain);
        }
    }
}
