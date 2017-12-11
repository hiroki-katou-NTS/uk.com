package nts.uk.ctx.pereg.app.find.usersetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.usesetting.UserSetting;
import nts.uk.ctx.pereg.dom.usesetting.UserSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UserSettingFinder {

	@Inject
	private UserSettingRepository userSetRepo;

	public UserSettingDto getUserSetting() {

		Optional<UserSetting> opt = this.userSetRepo.getUserSetting(AppContexts.user().employeeId());

		if (!opt.isPresent()) {
			return null;
		} else {

			return UserSettingDto.fromDomain(opt.get());
		}

	}

}
