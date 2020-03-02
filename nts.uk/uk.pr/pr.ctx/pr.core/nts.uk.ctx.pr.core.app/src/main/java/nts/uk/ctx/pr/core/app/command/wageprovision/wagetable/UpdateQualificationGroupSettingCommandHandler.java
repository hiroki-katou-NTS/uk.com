package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateQualificationGroupSettingCommandHandler extends CommandHandler<QualificationGroupSettingCommand> {

	@Inject
	private QualificationGroupSettingRepository qualificationGroupSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<QualificationGroupSettingCommand> context) {
		QualificationGroupSettingCommand command = context.getCommand();
		command.setCompanyID(AppContexts.user().companyId());
		qualificationGroupSettingRepository.update(command.fromCommandToDomain());
	}
}
