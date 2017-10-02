/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.mailserver;

import javax.ejb.Stateless;

import nts.gul.mail.send.setting.SendMailSetting;
import nts.uk.shr.com.mail.SendMailSettingAdaptor;

/**
 * The Class JpaSystemConfigRepository.
 */
@Stateless
public class SendMailSettingAdaptorImpl implements SendMailSettingAdaptor {

	@Override
	public SendMailSetting getSetting(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
