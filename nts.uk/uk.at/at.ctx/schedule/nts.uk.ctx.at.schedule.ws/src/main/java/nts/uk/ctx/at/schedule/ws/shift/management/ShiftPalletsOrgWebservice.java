package nts.uk.ctx.at.schedule.ws.shift.management;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shiftmanagement.shiftwork.shiftpalet.DuplicateOrgShiftPaletCommand;
import nts.uk.ctx.at.schedule.app.command.shiftmanagement.shiftwork.shiftpalet.DuplicateOrgShiftPaletHandler;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgDto;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgFinder;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgFinder.PageDto;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgFinder.ShiftPalletsOrgDtoJB;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.Param;

@Path("at/schedule/shift/management/shiftpalletorg")
@Produces("application/json")
public class ShiftPalletsOrgWebservice extends WebService {
	
	@Inject
	private ShiftPalletsOrgFinder shiftPalletsOrgFinder;
	
	@Inject
	private DuplicateOrgShiftPaletHandler duplicateOrgShiftPaletHandler;
	
	@POST
	@Path("getbyWorkplaceId")
	public ShiftPalletsOrgDtoJB getListShijtPalletsOrg(String workplaceId) {
		return shiftPalletsOrgFinder.getbyWorkPlaceIdJb(workplaceId);
	}
	
	@POST
	@Path("getbyWorkplaceGrId")
	public ShiftPalletsOrgDtoJB getListShijtPalletsOrgGr(String workplaceGrId) {
		return shiftPalletsOrgFinder.getbyWorkPlaceGrId(workplaceGrId);
	}

	@POST
	@Path("getShiftPaletteByWP")
	public List<PageDto> getShiftPaletteByWP(Param param) {
		return shiftPalletsOrgFinder.getShiftPaletteByWP(param.getId());
	}

	@POST
	@Path("getShiftPaletteByWPG")
	public List<PageDto> getShiftPaletteByWPG(Param param) {
		return shiftPalletsOrgFinder.getShiftPaletteByWPG(param.getId());
	}

	@POST
	@Path("duplicateOrgShiftPalet")
	public void duplicateOrgShiftPalet(DuplicateOrgShiftPaletCommand command) {
		this.duplicateOrgShiftPaletHandler.handle(command);
	}
}
