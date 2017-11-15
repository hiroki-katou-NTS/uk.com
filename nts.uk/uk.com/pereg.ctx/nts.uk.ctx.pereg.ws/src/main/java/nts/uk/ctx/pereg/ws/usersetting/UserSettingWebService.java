package nts.uk.ctx.pereg.ws.usersetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import find.person.setting.user.UserSettingDto;
import find.person.setting.user.UserSettingFinder;

/**
 * @author sonnlb
 *
 */
@Path("ctx/pereg/usersetting")
@Produces("application/json")
public class UserSettingWebService {

	@Inject
	private UserSettingFinder finder;

	@POST
	@Path("getUserSetting")
	public UserSettingDto getUserSetting() {
		return this.finder.getUserSetting();
	}
}
