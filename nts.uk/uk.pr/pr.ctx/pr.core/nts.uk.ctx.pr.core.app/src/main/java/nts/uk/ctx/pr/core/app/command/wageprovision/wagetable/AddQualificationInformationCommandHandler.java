package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformationRepository;

@Stateless
public class AddQualificationInformationCommandHandler extends CommandHandler<QualificationInformationCommand> {

	@Inject
	private QualificationInformationRepository qualificationInformationRepository;

	@Override
	protected void handle(CommandHandlerContext<QualificationInformationCommand> context) {
		QualificationInformationCommand command = context.getCommand();
		if (qualificationInformationRepository.getQualificationGroupSettingById(command.getQualificationCode())
				.isPresent())
			throw new BusinessException("Msg_3");
		qualificationInformationRepository.add(command.fromCommandToDomain());
	}
}
