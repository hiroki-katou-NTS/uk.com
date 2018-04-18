package nts.uk.ctx.at.function.app.command.alarm.mailsettings;

import lombok.Value;

@Value
public class MailAutoAndNormalCommand {

	private MailSettingAutomaticCommand mailSettingAutomatic;
	
	private MailSettingNormalCommand mailSettingNormal;
	
}
