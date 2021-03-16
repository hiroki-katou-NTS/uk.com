package nts.uk.screen.at.ws.kmk.kmk004.h;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.h.DeleteFlexMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.h.DeleteFlexMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.h.RegisterFlexMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.h.UpdateFlexMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.kmk004.h.AfterChangeFlexWorkPlaceSetting;
import nts.uk.screen.at.app.kmk004.h.AfterChangeFlexWorkPlaceSettingDto;
import nts.uk.screen.at.app.kmk004.h.AfterCopyFlexMonthlyWorkTimeSetWkp;
import nts.uk.screen.at.app.kmk004.h.DisplayInitialFlexScreenByWorkPlace;
import nts.uk.screen.at.app.kmk004.h.DisplayInitialFlexScreenByWorkPlaceDto;
import nts.uk.screen.at.app.kmk004.h.SelectFlexYearByWorkPlace;
import nts.uk.screen.at.app.kmk004.h.SelectWorkPlaceFlex;
import nts.uk.screen.at.app.kmk004.h.SelectWorkPlaceFlexDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;

@Path("screen/at/kmk004/h")
@Produces("application/json")
public class Kmk004HWebService {

	@Inject
	private DisplayInitialFlexScreenByWorkPlace initScreen;

	@Inject
	private SelectWorkPlaceFlex selectWorkPlace;

	@Inject
	private SelectFlexYearByWorkPlace selectFlexYear;

	@Inject
	private RegisterFlexMonthlyWorkTimeSetWkpCommandHandler registerHandler;

	@Inject
	private UpdateFlexMonthlyWorkTimeSetWkpCommandHandler updateHandler;

	@Inject
	private DeleteFlexMonthlyWorkTimeSetWkpCommandHandler deleteHandler;

	@Inject
	private AfterCopyFlexMonthlyWorkTimeSetWkp afterCopy;

	@Inject
	private AfterChangeFlexWorkPlaceSetting afterChangeSetting;

	@POST
	@Path("init-screen/{wkpId}")
	public DisplayInitialFlexScreenByWorkPlaceDto initScreen(@PathParam("wkpId") String wkpId) {
		return this.initScreen.displayInitialFlexScreenByWorkPlace(wkpId);
	}

	@POST
	@Path("change-year/{wkpId}/{year}")
	public List<DisplayMonthlyWorkingDto> changeYear(@PathParam("wkpId") String wkpId, @PathParam("year") int year) {
		return this.selectFlexYear.selectFlexYearByWorkPlace(wkpId, year);
	}

	@POST
	@Path("change-wkpid/{wkpId}")
	public SelectWorkPlaceFlexDto changeWkpId(@PathParam("wkpId") String wkpId) {
		return this.selectWorkPlace.selectWorkPlaceFlex(wkpId);
	}

	@POST
	@Path("register")
	public List<String> register(SaveMonthlyWorkTimeSetWkpCommand command) {
		return this.registerHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(SaveMonthlyWorkTimeSetWkpCommand command) {
		this.updateHandler.handle(command);
	}

	@POST
	@Path("delete")
	public List<String> delete(DeleteFlexMonthlyWorkTimeSetWkpCommand command) {
		return this.deleteHandler.handle(command);
	}

	@POST
	@Path("after-copy")
	public List<String> afterCopy() {
		return this.afterCopy.afterCopyFlexMonthlyWorkTimeSetWkp();
	}

	@POST
	@Path("change-setting/{wkpId}")
	public AfterChangeFlexWorkPlaceSettingDto changeSetting(@PathParam("wkpId") String wkpId) {
		return this.afterChangeSetting.afterChangeFlexWorkPlaceSetting(wkpId);
	}

}
