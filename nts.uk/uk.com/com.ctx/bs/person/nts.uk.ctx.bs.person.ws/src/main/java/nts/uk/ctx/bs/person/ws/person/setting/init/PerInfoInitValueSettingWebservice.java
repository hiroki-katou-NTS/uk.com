package nts.uk.ctx.bs.person.ws.person.setting.init;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import command.person.setting.init.InsertInitValueSettingCommand;
import command.person.setting.init.InsertInitValueSettingHandler;
import find.person.setting.init.PerInfoInitValueSettingDto;
import find.person.setting.init.PerInfoInitValueSettingFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/info/setting/init")
@Produces("application/json")
public class PerInfoInitValueSettingWebservice extends WebService {
	@Inject
	private PerInfoInitValueSettingFinder finder;

	@Inject
	private InsertInitValueSettingHandler add;

	@POST
	@Path("findAll")
	public List<PerInfoInitValueSettingDto> getAllInitValueSetting() {
		return this.finder.getAllInitValueSetting();
	}

	@POST
	@Path("add")
	public void add(InsertInitValueSettingCommand command) {
		this.add.handle(command);
	}

}
