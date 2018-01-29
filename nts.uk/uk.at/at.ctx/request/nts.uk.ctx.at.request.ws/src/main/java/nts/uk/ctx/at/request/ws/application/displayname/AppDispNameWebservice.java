package nts.uk.ctx.at.request.ws.application.displayname;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.company.displayname.AppDispNameDto;
import nts.uk.ctx.at.request.app.find.setting.company.displayname.AppDispNameFinder;

@Path("at/request/application/displayname")
@Produces("application/json")
public class AppDispNameWebservice extends WebService{
	@Inject
	private AppDispNameFinder dispFinder;
	@POST
	@Path("disp")
	public List<AppDispNameDto> getAppSet(){
		 return dispFinder.findByCom();
	}

	@POST
	@Path("app/{appType}")
	public AppDispNameDto getAppSet(@PathParam("appType") int appType){
		 return dispFinder.findByApp(appType);
	}
}
