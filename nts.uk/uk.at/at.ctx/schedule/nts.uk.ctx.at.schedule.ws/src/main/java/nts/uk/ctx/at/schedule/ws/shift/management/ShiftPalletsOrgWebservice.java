package nts.uk.ctx.at.schedule.ws.shift.management;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.PageandName;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgFinder;

@Path("at/schedule/shift/management")
@Produces("application/json")
public class ShiftPalletsOrgWebservice  extends WebService{
	@Inject
		private ShiftPalletsOrgFinder shiftPalletsOrgFinder;
	
	@POST
	public List <PageandName> getListShijtPalletsByCom(){
		return null;
	}
}
