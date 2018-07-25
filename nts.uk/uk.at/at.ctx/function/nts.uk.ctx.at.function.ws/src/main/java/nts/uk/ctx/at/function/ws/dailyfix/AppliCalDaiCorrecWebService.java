package nts.uk.ctx.at.function.ws.dailyfix;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.command.dailyfix.AppliCalDaiCorrecCommand;
import nts.uk.ctx.at.function.app.command.dailyfix.UpdateAppliCalDaiCorrecCommandHandler;
import nts.uk.ctx.at.function.app.find.dailyfix.AppliCalDaiCorrecDto;
import nts.uk.ctx.at.function.app.find.dailyfix.AppliCalDaiCorrecFinder;

@Path("at/function/dailyfix")
@Produces("application/json")
public class AppliCalDaiCorrecWebService {
	@Inject
	private AppliCalDaiCorrecFinder appCalFinder;
	
	@Inject
	private UpdateAppliCalDaiCorrecCommandHandler update;
	
	@POST
	@Path("find")
	public AppliCalDaiCorrecDto getAppCal(){
		return appCalFinder.find();
	}
	
	@POST
	@Path("update")
	public void update(AppliCalDaiCorrecCommand cm){
		this.update.handle(cm);
	}
}
