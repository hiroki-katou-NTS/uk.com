package nts.uk.ctx.at.function.app.command.alarm.alarmlist;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormal;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormalRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SendMailErrorAlarmCommandHandler extends CommandHandler<SendMailErrorAlarmCommand>{
	
	@Inject
	private MailSettingNormalRepository mailSettingNormalRepository;
	
	@Override
	protected void handle(CommandHandlerContext<SendMailErrorAlarmCommand> context) {
		Optional<MailSettingNormal> optMailSetting = mailSettingNormalRepository.findByCompanyId(AppContexts.user().companyId());
		
	}

}
