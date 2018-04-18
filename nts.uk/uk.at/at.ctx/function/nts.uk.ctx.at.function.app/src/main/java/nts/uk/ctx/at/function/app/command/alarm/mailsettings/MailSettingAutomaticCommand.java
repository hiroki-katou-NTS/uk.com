package nts.uk.ctx.at.function.app.command.alarm.mailsettings;

import lombok.Value;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingAutomatic;
import nts.uk.shr.com.context.AppContexts;

@Value
public class MailSettingAutomaticCommand {

	private MailSettingsCommand mailSettings;

	private String senderAddress;

	private MailSettingsCommand mailSettingAdmins;
	
	public MailSettingAutomatic toDomain(){
		String companyID = AppContexts.user().companyId();
		return new MailSettingAutomatic(companyID, mailSettings.toDomain(), senderAddress, mailSettingAdmins.toDomain());
	}
}
