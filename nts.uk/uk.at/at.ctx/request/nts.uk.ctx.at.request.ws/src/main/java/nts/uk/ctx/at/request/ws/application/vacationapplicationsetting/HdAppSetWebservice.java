package nts.uk.ctx.at.request.ws.application.vacationapplicationsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.company.vacationapplicationsetting.HdAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.vacationapplicationsetting.HdAppSetFinder;

@Path("at/request/vacation/setting")
@Produces("application/json")
public class HdAppSetWebservice extends WebService{
	@Inject
	private HdAppSetFinder hdAppFinder;
	
	@POST
	@Path("hdapp")
	public HdAppSetDto getAppSet(){
		 return hdAppFinder.findByApp();
	}
}
