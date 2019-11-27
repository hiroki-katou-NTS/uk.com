package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformationRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DeleteQualificationInformationCommandHandler extends CommandHandler<QualificationInformationCommand> {

    @Inject
    private QualificationInformationRepository qualificationInformationRepository;

    @Override
    protected void handle(CommandHandlerContext<QualificationInformationCommand> context) {
        QualificationInformationCommand command = context.getCommand();
        qualificationInformationRepository.remove(command.fromCommandToDomain());
    }
}
