package nts.uk.ctx.bs.person.ws.person.setting.init.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import command.person.setting.init.UpdateInitValueSettingCommand;
import command.person.setting.init.UpdateInitValueSettingHandler;
import find.person.setting.init.PerInfoInitValueSettingFinder;
import find.person.setting.init.PerInitValueSettingDto;
import find.person.setting.init.category.SettingCtgDto;
import find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/info/setting/init/ctg")
@Produces("application/json")
public class PerInfoInitValueSettingCtgWebservice extends WebService {

	// sonnlb code start
	@Inject
	private PerInfoInitValueSettingCtgFinder cgtFinder;
	// sonnlb code end

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
	// sonnlb code start

	@POST
	@Path("findAllBySetId/{settingId}")
	public List<SettingCtgDto> getAllCategoryBySetId(@PathParam("settingId") String settingId) {
		return this.cgtFinder.getAllCategoryBySetId(settingId);
	}

	// sonnlb code end

}
