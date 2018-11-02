package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UpdateQualificationGroupSettingCommandHandler extends CommandHandler<QualificationGroupSettingCommand> {

    @Inject
    private QualificationGroupSettingRepository qualificationGroupSettingRepository;

    @Override
    protected void handle(CommandHandlerContext<QualificationGroupSettingCommand> context) {
        QualificationGroupSettingCommand command = context.getCommand();
        qualificationGroupSettingRepository.update(command.fromCommandToDomain());
    }
}
