package nts.uk.ctx.at.shared.app.command.supportoperationsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.MaximumNumberOfSupport;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RegisterSupportOperationSettingCommandHandler extends CommandHandler<RegisterSupportOperationSettingCommand> {

    @Inject
    private SupportOperationSettingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<RegisterSupportOperationSettingCommand> commandHandlerContext) {
        RegisterSupportOperationSettingCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        SupportOperationSetting supportOperationSetting = new SupportOperationSetting(
                command.getIsUsed()==1,
                false, // default value
                new MaximumNumberOfSupport(command.getMaxNumberOfSupportOfDay())
        );

        repository.update(cid, supportOperationSetting);
    }
}
