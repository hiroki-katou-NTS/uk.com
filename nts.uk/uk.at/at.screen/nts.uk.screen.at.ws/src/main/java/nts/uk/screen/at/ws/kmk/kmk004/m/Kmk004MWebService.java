package nts.uk.screen.at.ws.kmk.kmk004.m;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.m.DeleteTransMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.m.DeleteTransMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.m.RegisterTransMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.m.UpdateTransMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.kmk004.m.AfterCopyDeforMonthlyWorkTimeSetWkp;
import nts.uk.screen.at.app.kmk004.m.DeforLaborMonthTimeWkpDto;
import nts.uk.screen.at.app.kmk004.m.DisplayDeforBasicSettingByWorkplace;
import nts.uk.screen.at.app.kmk004.m.DisplayInitialDeforScreenByWorkPlaceDto;
import nts.uk.screen.at.app.kmk004.m.DisplayInitialDeforScreenByWorkplace;
import nts.uk.screen.at.app.kmk004.m.SelectWorkplaceDefor;
import nts.uk.screen.at.app.kmk004.m.SelectWorkplaceDeforDto;
import nts.uk.screen.at.app.kmk004.m.SelectYearByWorkplace;
import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingByWkpInputDto;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kmk004/viewM")
@Produces("application/json")
public class Kmk004MWebService {

	@Inject
	private RegisterTransMonthlyWorkTimeSetWkpCommandHandler registerHandler;

	@Inject
	private UpdateTransMonthlyWorkTimeSetWkpCommandHandler updateHandler;

	@Inject
	private DeleteTransMonthlyWorkTimeSetWkpCommandHandler deleteHandler;
	
	@Inject
	private DisplayDeforBasicSettingByWorkplace dislaySetting;
	
	@Inject
	private DisplayInitialDeforScreenByWorkplace initScreen;
	
	@Inject
	private SelectWorkplaceDefor select;
	
	@Inject
	private SelectYearByWorkplace selectYearByWkp;
	
	@Inject
	private AfterCopyDeforMonthlyWorkTimeSetWkp afterCopy;

	@POST
	@Path("monthlyWorkTimeSet/add")
	public void registerMonthlyWorkTimeSet(SaveMonthlyWorkTimeSetWkpCommand command) {
		registerHandler.handle(command);
	}

	@POST
	@Path("monthlyWorkTimeSet/update")
	public void updateMonthlyWorkTimeSet(SaveMonthlyWorkTimeSetWkpCommand command) {
		updateHandler.handle(command);
	}

	@POST
	@Path("monthlyWorkTimeSet/delete")
	public void deleteMonthlyWorkTimeSet(DeleteTransMonthlyWorkTimeSetWkpCommand command) {
		deleteHandler.handle(command);
	}
	
	@POST
	@Path("getBasicSetting/{wkpId}")
	public DeforLaborMonthTimeWkpDto getBasicSetting(@PathParam("wkpId") String wkpId) {
		return dislaySetting.displayDeforBasicSettingByWorkplace(wkpId);
	}
	
	@POST
	@Path("selectWkp/{wkpId}")
	public SelectWorkplaceDeforDto selectWkp(@PathParam("wkpId") String wkpId) {
		return select.selectWorkplaceDefor(wkpId);
	}
	
	@POST
	@Path("initScreen")
	public DisplayInitialDeforScreenByWorkPlaceDto initScreen() {
		return initScreen.displayInitialDeforScreenByWorkplace() ;
	}
	
	@POST
	@Path("getWorkingHoursByWkp")
	public List<WorkTimeComDto> getWorkingHoursByWkp(DisplayMonthlyWorkingByWkpInputDto param) {
		return selectYearByWkp.getDeforDisplayMonthlyWorkingHoursByWkp(param) ;
	}
	
	@POST
	@Path("after-copy")
	public List<String> afterCopy() {
		return this.afterCopy.afterCopyDeforMonthlyWorkTimeSetWkp();
	}
}
