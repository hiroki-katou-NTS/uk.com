package nts.uk.ctx.bs.person.dom.person.info.setting.user.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.setting.user.UserSetting;
import nts.uk.ctx.bs.person.dom.person.info.setting.user.UserSettingRepository;

@Stateless
public class UserSettingBuzImpl implements UserSettingBuz{
	
	@Inject
	private UserSettingRepository userSettingRepository;

	@Override
	public void updateUserSetting(UserSetting userSetting) {
		boolean existUS = userSettingRepository.checkUserSettingExist(userSetting.getEmployeeId());
		if(existUS)
			userSettingRepository.updateUserSetting(userSetting);
		else userSettingRepository.createUserSetting(userSetting);
	}

}
