package nts.uk.ctx.at.function.app.find.alarm.mailsettings;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailAddress;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettings;

@Data
public class MailSettingsDto {

	private String subject;

	private String text;

	private List<String> mailAddressBCC;

	private List<String> mailAddressCC;

	private String mailRely;

	public MailSettingsDto(Optional<MailSettings> MailSettings) {
		this.subject = MailSettings.get().getSubject().get().v();
		this.text = MailSettings.get().getText().get().v();
		this.mailAddressBCC = MailSettings.get().getMailAddressBCC().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
		this.mailAddressCC = MailSettings.get().getMailAddressCC().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
		this.mailRely = MailSettings.get().getMailRely().get().v();
	}

	public MailSettingsDto(MailSettings mailSetting) {
		this.subject = mailSetting.getSubject().isPresent() ? mailSetting.getSubject().get().v() : null;
		this.text = mailSetting.getText().isPresent() ? mailSetting.getText().get().v() : null;
		this.mailAddressBCC = mailSetting.getMailAddressBCC().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
		this.mailAddressCC = mailSetting.getMailAddressCC().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
		this.mailRely =  mailSetting.getMailRely().isPresent() ? mailSetting.getMailRely().get().v() : null;
	}
	
}
