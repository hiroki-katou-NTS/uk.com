package nts.uk.ctx.at.function.app.find.alarm.mailsettings;

import lombok.Data;

@Data
public class MailAutoAndNormalDto {

	private MailSettingAutomaticDto mailSettingAutomaticDto;
	
	private MailSettingNormalDto mailSettingNormalDto;

	public MailAutoAndNormalDto(MailSettingAutomaticDto mailSettingAutomaticDto,
			MailSettingNormalDto mailSettingNormalDto) {
		super();
		this.mailSettingAutomaticDto = mailSettingAutomaticDto;
		this.mailSettingNormalDto = mailSettingNormalDto;
	}
	
}
