package nts.uk.ctx.pereg.ws.person.setting.init;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.person.setting.init.CopyInitValueSetCommand;
import nts.uk.ctx.pereg.app.command.person.setting.init.CopyInitValueSetCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.init.DeleteInitValueSettingCommand;
import nts.uk.ctx.pereg.app.command.person.setting.init.DeleteInitValueSettingHandler;
import nts.uk.ctx.pereg.app.command.person.setting.init.InsertInitValueSettingCommand;
import nts.uk.ctx.pereg.app.command.person.setting.init.InsertInitValueSettingHandler;
import nts.uk.ctx.pereg.app.find.person.setting.init.PerInfoInitValueSettingDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.PerInfoInitValueSettingFinder;

@Path("ctx/pereg/person/info/setting/init")
@Produces("application/json")
public class PerInfoInitValueSettingWebservice extends WebService {
	@Inject
	private PerInfoInitValueSettingFinder finder;

	@Inject
	private InsertInitValueSettingHandler add;

	@Inject
	private CopyInitValueSetCommandHandler copyInitValue;

	@Inject
	private DeleteInitValueSettingHandler delete;

	@POST
	@Path("findAll")
	public List<PerInfoInitValueSettingDto> getAllInitValueSetting() {
		return this.finder.getAllInitValueSetting();
	}

	@POST
	@Path("add")
	public JavaTypeResult<String> add(InsertInitValueSettingCommand command) {
		return new JavaTypeResult<String>(this.add.handle(command));
	}

	@POST
	@Path("delete")
	public void delete(DeleteInitValueSettingCommand command) {
		this.delete.handle(command);
	}

	// sonnlb

	@POST
	@Path("findAllHasChild")
	public List<PerInfoInitValueSettingDto> getAllInitValueSettingHasChild() {
		return this.finder.getAllInitValueSettingHasChild();
	}

	// sonnlb

	// hoatt
	@POST
	@Path("copyInitValue")
	public JavaTypeResult<String> copyInitValueCtg(CopyInitValueSetCommand command) {
		return new JavaTypeResult<String>(this.copyInitValue.handle(command));
	}
}
