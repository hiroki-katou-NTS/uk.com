package nts.uk.screen.at.ws.kmk.kmk004.h;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.h.DeleteFlexMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.h.DeleteFlexMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.h.RegisterFlexMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.h.UpdateFlexMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.kmk004.h.AfterChangeFlexWorkPlaceSetting;
import nts.uk.screen.at.app.kmk004.h.AfterChangeFlexWorkPlaceSettingDto;
import nts.uk.screen.at.app.kmk004.h.AfterCopyFlexMonthlyWorkTimeSetWkp;
import nts.uk.screen.at.app.kmk004.h.DisplayInitialFlexScreenByWorkPlace;
import nts.uk.screen.at.app.kmk004.h.DisplayInitialFlexScreenByWorkPlaceDto;
import nts.uk.screen.at.app.kmk004.h.SelectFlexYearByWorkPlace;
import nts.uk.screen.at.app.kmk004.h.SelectWorkPlaceFlex;
import nts.uk.screen.at.app.kmk004.h.SelectWorkPlaceFlexDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceIdDto;

@Path("screen/at/kmk004/h")
@Produces("application/json")
public class Kmk004HWebService {

	@Inject
	private DisplayInitialFlexScreenByWorkPlace displayInitialFlexScreenByWorkPlace;

	@Inject
	private SelectWorkPlaceFlex selectWorkPlaceFlex;

	@Inject
	private SelectFlexYearByWorkPlace selectFlexYearByWorkPlace;

	@Inject
	private RegisterFlexMonthlyWorkTimeSetWkpCommandHandler registerHandler;

	@Inject
	private UpdateFlexMonthlyWorkTimeSetWkpCommandHandler updateHandler;

	@Inject
	private DeleteFlexMonthlyWorkTimeSetWkpCommandHandler deleteHandler;

	@Inject
	private AfterCopyFlexMonthlyWorkTimeSetWkp afterCopyFlexMonthlyWorkTimeSetWkp;

	@Inject
	private AfterChangeFlexWorkPlaceSetting afterChangeFlexWorkPlaceSetting;

	@POST
	@Path("init-screen")
	public DisplayInitialFlexScreenByWorkPlaceDto initScreen() {
		return this.displayInitialFlexScreenByWorkPlace.displayInitialFlexScreenByWorkPlace();
	}

	@POST
	@Path("change-year/{wkpId}/{year}")
	public List<DisplayMonthlyWorkingDto> changeYear(@PathParam("wkpId") String wkpId, @PathParam("year") int year) {
		return this.selectFlexYearByWorkPlace.selectFlexYearByWorkPlace(wkpId, year);
	}

	@POST
	@Path("change-wkpid/{wkpId}/{year}")
	public SelectWorkPlaceFlexDto changeWkpId(@PathParam("wkpId") String wkpId) {
		return this.selectWorkPlaceFlex.selectWorkPlaceFlex(wkpId);
	}

	@POST
	@Path("register")
	public List<WorkplaceIdDto> register(SaveMonthlyWorkTimeSetWkpCommand command) {
		return this.registerHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(SaveMonthlyWorkTimeSetWkpCommand command) {
		this.updateHandler.handle(command);
	}

	@POST
	@Path("delete")
	public List<WorkplaceIdDto> delete(DeleteFlexMonthlyWorkTimeSetWkpCommand command) {
		return this.deleteHandler.handle(command);
	}

	@POST
	@Path("after-copy")
	public List<WorkplaceIdDto> afterCopy() {
		return this.afterCopyFlexMonthlyWorkTimeSetWkp.afterCopyFlexMonthlyWorkTimeSetWkp();
	}

	@POST
	@Path("change-setting")
	public AfterChangeFlexWorkPlaceSettingDto changeSetting(String wkpId) {
		return this.afterChangeFlexWorkPlaceSetting.afterChangeFlexWorkPlaceSetting(wkpId);
	}

}
