package nts.uk.ctx.pereg.app.command.usesetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.usesetting.UserSetting;
import nts.uk.ctx.pereg.dom.usesetting.UserSettingRepository;

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
