package nts.uk.ctx.at.function.app.find.alarm.mailsettings;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingAutomatic;

@Data
public class MailSettingAutomaticDto {

	private MailSettingsDto mailSettings = null;

	private String senderAddress = "";

	private MailSettingsDto mailSettingAdmins = null;

	public MailSettingAutomaticDto(Optional<MailSettingAutomatic> mailSettingAutomatic) {
		super();
		if(mailSettingAutomatic.isPresent()){
			this.mailSettings = mailSettingAutomatic.get().getMailSettings().isPresent() ? new MailSettingsDto(mailSettingAutomatic.get().getMailSettings()): null;
			this.senderAddress = mailSettingAutomatic.get().getSenderAddress().isPresent() ? mailSettingAutomatic.get().getSenderAddress().get():"";
			this.mailSettingAdmins = mailSettingAutomatic.get().getMailSettingAdmins().isPresent() ? new MailSettingsDto(mailSettingAutomatic.get().getMailSettingAdmins()): null;
		}
	}
	
}
