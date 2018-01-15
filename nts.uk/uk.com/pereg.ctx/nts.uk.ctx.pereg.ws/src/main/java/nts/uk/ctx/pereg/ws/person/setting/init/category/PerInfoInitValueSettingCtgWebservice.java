package nts.uk.ctx.pereg.ws.person.setting.init.category;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.person.setting.init.UpdateInitValueSettingCommand;
import nts.uk.ctx.pereg.app.command.person.setting.init.UpdateInitValueSettingHandler;
import nts.uk.ctx.pereg.app.find.person.setting.init.PerInfoInitValueSettingFinder;
import nts.uk.ctx.pereg.app.find.person.setting.init.PerInitValueSettingDto;

@Path("ctx/pereg/person/info/setting/init/ctg")
@Produces("application/json")
public class PerInfoInitValueSettingCtgWebservice extends WebService {

	@Inject
	private PerInfoInitValueSettingFinder finder;

	@Inject
	private UpdateInitValueSettingHandler update;

	@POST
	@Path("find/{settingId}")
	public PerInitValueSettingDto getAllInitValueSetting(@PathParam("settingId") String settingId) {
		return this.finder.getAllInitValueSetting(settingId);
	}

	@POST
	@Path("update")
	public void update(UpdateInitValueSettingCommand command) {
		this.update.handle(command);
	}

}
