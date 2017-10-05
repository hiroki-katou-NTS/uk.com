package nts.uk.ctx.bs.person.ws.person.setting.copysetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import find.person.setting.copysetting.EmpCopySettingDto;
import find.person.setting.copysetting.EmpCopySettingFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/info/setting/copySetting")
@Produces("application/json")
public class EmpCopySettingWebService extends WebService {

	@Inject
	private EmpCopySettingFinder finder;

	@POST
	@Path("getCopySetting")
	public EmpCopySettingDto getEmpCopySetting() {
		return this.finder.getEmpCopySetting();
	}

}
