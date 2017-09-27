package command.person.info.setting.user;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.setting.user.UserSetting;
import nts.uk.ctx.bs.person.dom.person.info.setting.user.service.UserSettingBuz;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class UpdateUserSettingCommandHandler extends CommandHandler<UpdateUserSettingCommand>{

	@Inject
	private UserSettingBuz userSettingBuz;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateUserSettingCommand> context) {
		UpdateUserSettingCommand command = context.getCommand();
		String employeeId = AppContexts.user().employeeId();
		userSettingBuz.updateUserSetting(UserSetting.generateFullObject(employeeId, command.getEmpCodeValType()
				, command.getCardNoValType(), command.getRecentRegType(), command.getEmpCodeLetter(), command.getCardNoLetter()));
	}

}
