package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformationRepository;

@Stateless
public class UpdateQualificationInformationCommandHandler extends CommandHandler<QualificationInformationCommand> {

	@Inject
	private QualificationInformationRepository qualificationInformationRepository;

	@Override
	protected void handle(CommandHandlerContext<QualificationInformationCommand> context) {
		QualificationInformationCommand command = context.getCommand();
		qualificationInformationRepository.update(command.fromCommandToDomain());
	}
}
