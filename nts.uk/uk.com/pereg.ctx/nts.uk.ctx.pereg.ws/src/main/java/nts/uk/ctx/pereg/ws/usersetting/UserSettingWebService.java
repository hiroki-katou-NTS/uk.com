package nts.uk.ctx.pereg.ws.usersetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pereg.app.find.usersetting.UserSettingDto;
import nts.uk.ctx.pereg.app.find.usersetting.UserSettingFinder;

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
