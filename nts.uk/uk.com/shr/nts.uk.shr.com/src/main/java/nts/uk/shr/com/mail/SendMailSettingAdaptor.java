package nts.uk.shr.com.mail;

import nts.gul.mail.send.setting.SendMailSetting;

public interface SendMailSettingAdaptor {

	SendMailSetting getSetting(String companyId);
}
