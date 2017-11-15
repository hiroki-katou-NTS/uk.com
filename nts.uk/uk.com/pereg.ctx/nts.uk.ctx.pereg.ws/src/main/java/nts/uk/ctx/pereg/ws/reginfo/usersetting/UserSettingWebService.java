package nts.uk.ctx.pereg.ws.reginfo.usersetting;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import find.person.setting.user.UserSettingDto;
import find.person.setting.user.UserSettingFinder;

/**
 * @author sonnlb
 *
 */
@Path("reginfo/usersetting")
@Produces("application/json")
public class UserSettingWebService {

	private UserSettingFinder finder;

	@POST
	@Path("getUserSetting")
	public UserSettingDto getUserSetting() {
		return this.finder.getUserSetting();
	}
}
