package nts.uk.ctx.at.request.ws.application.gobackdirectlycommon;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingDto;
import nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingFinder;
@Path("at/request/application/gobackdirectlycommon")
@Produces("application/json")
public class GoBackDirectlyCommonSettingWebservice extends WebService{
	@Inject
	private GoBackDirectlyCommonSettingFinder goBackSettingFinder;
	@POST
	@Path("getGoBackCommonByCid")
	public GoBackDirectlyCommonSettingDto getGoBackCommonByCid(){
		// Hoang Yen
		// ten AppId nhung thuc chat truyen vao companyid
		return this.goBackSettingFinder.findGoBackDirectlyCommonSettingbyAppID();
	}
}
