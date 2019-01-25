package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AddQualificationGroupSettingCommandHandler extends CommandHandler<QualificationGroupSettingCommand> {

	@Inject
	private QualificationGroupSettingRepository qualificationGroupSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<QualificationGroupSettingCommand> context) {
		QualificationGroupSettingCommand command = context.getCommand();
		if (qualificationGroupSettingRepository.getQualificationGroupSettingById(command.getQualificationGroupCode())
				.isPresent())
			throw new BusinessException("Msg_3");
		command.setCompanyID(AppContexts.user().companyId());
		qualificationGroupSettingRepository.add(command.fromCommandToDomain());
	}
}
