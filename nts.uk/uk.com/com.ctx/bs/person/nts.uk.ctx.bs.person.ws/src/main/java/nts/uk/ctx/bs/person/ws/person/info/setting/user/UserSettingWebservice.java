package nts.uk.ctx.bs.person.ws.person.info.setting.user;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import command.person.info.setting.user.UpdateUserSettingCommand;
import command.person.info.setting.user.UpdateUserSettingCommandHandler;
import find.person.setting.user.UserSettingDto;
import find.person.setting.user.UserSettingFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/info/setting/user")
@Produces("application/json")
public class UserSettingWebservice extends WebService {

	@Inject
	private UpdateUserSettingCommandHandler updateUserSettingCommandHandler;

	@Inject
	private UserSettingFinder find;

	@POST
	@Path("update/updateUserSetting")
	public void updatePerInfoItemDefCopy(UpdateUserSettingCommand command) {
		this.updateUserSettingCommandHandler.handle(command);
	}

	@POST
	@Path("getUserSetting")
	public UserSettingDto getUserSetting() {
		return this.find.getUserSetting();
	}
}
