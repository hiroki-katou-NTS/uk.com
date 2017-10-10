package nts.uk.shr.com.mail;

import javax.ejb.Stateless;

import nts.gul.mail.send.setting.SendMailSetting;

@Stateless
public class TestSendMailSettingAdaptor implements SendMailSettingAdaptor {

	@Override
	public SendMailSetting getSetting(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
