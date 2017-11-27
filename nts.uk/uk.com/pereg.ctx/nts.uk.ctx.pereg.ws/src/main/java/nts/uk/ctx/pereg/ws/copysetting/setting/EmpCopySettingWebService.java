package nts.uk.ctx.pereg.ws.copysetting.setting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import find.person.setting.init.category.SettingCtgDto;
import nts.uk.ctx.pereg.app.command.copysetting.setting.UpdatePerInfoCtgCopyCommand;
import nts.uk.ctx.pereg.app.command.copysetting.setting.UpdatePerInfoCtgCopyCommandHandler;
import nts.uk.ctx.pereg.app.find.copysetting.setting.EmpCopySettingFinder;

/**
 * @author sonnlb
 *
 */
@Path("ctx/pereg/copysetting/setting")
@Produces("application/json")
public class EmpCopySettingWebService {

	@Inject
	private EmpCopySettingFinder finder;
	
	@Inject
	private UpdatePerInfoCtgCopyCommandHandler updatePerInfoCtgCopyHandler;

	@POST
	@Path("getCopySetting")
	public List<SettingCtgDto> getEmpCopySetting() {
		return this.finder.getEmpCopySetting();
	}

	@POST
	@Path("update/updatePerInfoCtgCopy")
	public void UpdatePerInfoCtgCopyHandler(UpdatePerInfoCtgCopyCommand command) {
		this.updatePerInfoCtgCopyHandler.handle(command);
	}
}
