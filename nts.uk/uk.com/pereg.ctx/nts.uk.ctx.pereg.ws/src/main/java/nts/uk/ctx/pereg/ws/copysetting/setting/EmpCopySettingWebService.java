package nts.uk.ctx.pereg.ws.copysetting.setting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pereg.app.command.copysetting.setting.UpdatePerInfoCtgCopyCommand;
import nts.uk.ctx.pereg.app.command.copysetting.setting.UpdatePerInfoCtgCopyCommandHandler;
import nts.uk.ctx.pereg.app.find.copysetting.setting.EmpCopySettingFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgMapDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.SettingCtgDto;

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
	
	@POST
	@Path("find/perInfoCtgHasItems")
	public List<PerInfoCtgMapDto> getPerInfoCtgHasItems(String ctgName){
		return finder.getAllPerInfoCategoryWithCondition(ctgName);
	}
}
