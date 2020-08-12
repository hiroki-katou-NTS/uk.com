package nts.uk.ctx.at.schedule.ws.shift.management;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgDto;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgFinder;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgFinder.PageDto;

@Path("at/schedule/shift/management/shiftpalletorg")
@Produces("application/json")
public class ShiftPalletsOrgWebservice  extends WebService{
	@Inject
		private ShiftPalletsOrgFinder shiftPalletsOrgFinder;
	
	
	@POST
	@Path("getbyWorkplaceId")
	public List<ShiftPalletsOrgDto> getListShijtPalletsOrg(String workplaceId){
		return shiftPalletsOrgFinder.getbyWorkPlaceId(workplaceId);
	}

	@POST
	@Path("getShiftPaletteByWP/{workplaceId}")
	public List<PageDto> getShiftPaletteByWP(@PathParam("workplaceId") String workplaceId){
		return shiftPalletsOrgFinder.getShiftPaletteByWP(workplaceId);
	}

	@POST
	@Path("getShiftPaletteByWPG/{WPGId}")
	public List<PageDto> getShiftPaletteByWPG(@PathParam("WPGId") String wPGId){
		return shiftPalletsOrgFinder.getShiftPaletteByWP(wPGId);
	}
}
