package nts.uk.ctx.at.function.app.find.alarm.mailsettings;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormal;

@Data
public class MailSettingNormalDto {

	private MailSettingsDto mailSettings = null;

	private MailSettingsDto mailSettingAdmins = null;
	
	public MailSettingNormalDto(Optional<MailSettingNormal> mailSettingNormal) {
		super();
		if(mailSettingNormal.isPresent()){
			this.mailSettings = mailSettingNormal.get().getMailSettings().isPresent() ? new MailSettingsDto(mailSettingNormal.get().getMailSettings()): null;
			this.mailSettingAdmins = mailSettingNormal.get().getMailSettingAdmins().isPresent() ? new MailSettingsDto(mailSettingNormal.get().getMailSettingAdmins()): null;
		}
	}
}
