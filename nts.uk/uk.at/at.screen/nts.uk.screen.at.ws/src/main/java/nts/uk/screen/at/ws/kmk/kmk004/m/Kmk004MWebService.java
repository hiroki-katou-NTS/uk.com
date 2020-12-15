package nts.uk.screen.at.ws.kmk.kmk004.m;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.m.DeleteTransMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.m.DeleteTransMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.m.RegisterTransMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.m.UpdateTransMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.kmk004.m.DeforLaborMonthTimeWkpDto;
import nts.uk.screen.at.app.kmk004.m.DisplayDeforBasicSettingByWorkplace;
import nts.uk.screen.at.app.kmk004.m.DisplayInitialDeforScreenByWorkPlaceDto;
import nts.uk.screen.at.app.kmk004.m.DisplayInitialDeforScreenByWorkplace;
import nts.uk.screen.at.app.kmk004.m.SelectWorkplaceDefor;
import nts.uk.screen.at.app.kmk004.m.SelectWorkplaceDeforDto;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;
import nts.uk.screen.at.app.query.kmk004.common.YearlyListByWorkplace;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004MWebService {

	@Inject
	private YearlyListByWorkplace yearlyListByWorkplace;

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

	@POST
	@Path("viewM/getListYear/{wkpId}")
	public List<YearDto> getWkpYearList(@PathParam("wkpId") String wkpId) {
		return yearlyListByWorkplace.get(wkpId, LaborWorkTypeAttr.DEFOR_LABOR);
	}

	@POST
	@Path("viewM/monthlyWorkTimeSet/add")
	public void registerMonthlyWorkTimeSet(CommandHandlerContext<SaveMonthlyWorkTimeSetWkpCommand> command) {
		registerHandler.handle(command);
	}

	@POST
	@Path("viewM/monthlyWorkTimeSet/update")
	public void updateMonthlyWorkTimeSet(CommandHandlerContext<SaveMonthlyWorkTimeSetWkpCommand> command) {
		updateHandler.handle(command);
	}

	@POST
	@Path("viewM/monthlyWorkTimeSet/delete")
	public void deleteMonthlyWorkTimeSet(CommandHandlerContext<DeleteTransMonthlyWorkTimeSetWkpCommand> command) {
		deleteHandler.handle(command);
	}
	
	@POST
	@Path("viewM/getBasicSetting/{wkpId}")
	public DeforLaborMonthTimeWkpDto getBasicSetting(@PathParam("wkpId") String wkpId) {
		return dislaySetting.displayDeforBasicSettingByWorkplace(wkpId);
	}
	
	@POST
	@Path("viewM/selectWkp/{wkpId}")
	public SelectWorkplaceDeforDto selectWkp(@PathParam("wkpId") String wkpId) {
		return select.selectWorkplaceDefor(wkpId);
	}
	
	@POST
	@Path("viewM/initScreen")
	public DisplayInitialDeforScreenByWorkPlaceDto initScreen() {
		return initScreen.displayInitialDeforScreenByWorkplace() ;
	}
}
