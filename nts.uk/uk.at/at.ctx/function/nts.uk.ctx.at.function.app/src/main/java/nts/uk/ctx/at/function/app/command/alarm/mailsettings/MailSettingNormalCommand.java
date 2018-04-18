package nts.uk.ctx.at.function.app.command.alarm.mailsettings;

import lombok.Value;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormal;
import nts.uk.shr.com.context.AppContexts;

@Value
public class MailSettingNormalCommand {

	private MailSettingsCommand mailSettings;

	private MailSettingsCommand mailSettingAdmins;
	
	public MailSettingNormal toDomain(){
		String companyID = AppContexts.user().companyId();
		return new MailSettingNormal(companyID, mailSettings.toDomain(), mailSettingAdmins.toDomain());
	}
}
