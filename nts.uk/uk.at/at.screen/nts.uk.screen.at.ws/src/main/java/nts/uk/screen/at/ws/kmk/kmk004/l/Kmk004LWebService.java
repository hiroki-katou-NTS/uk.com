package nts.uk.screen.at.ws.kmk.kmk004.l;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.l.DeleteTransMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.l.DeleteTransMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.l.RegisterTransMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.l.UpdateTransMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom.SaveMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.kmk004.l.DeforLaborMonthTimeComDto;
import nts.uk.screen.at.app.kmk004.l.DisplayDeforBasicSettingByCompany;
import nts.uk.screen.at.app.kmk004.l.DisplayInitialDeforScreenByCom;
import nts.uk.screen.at.app.kmk004.l.DisplayInitialDeforScreenByCompanyDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayYearListByCompany;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004LWebService {

	@Inject
	private DisplayYearListByCompany yearsByCompany;

	@Inject
	private RegisterTransMonthlyWorkTimeSetComCommandHandler registerHandler;

	@Inject
	private UpdateTransMonthlyWorkTimeSetComCommandHandler updateHandler;

	@Inject
	private DeleteTransMonthlyWorkTimeSetComCommandHandler deleteHandler;
	
	@Inject
	private DisplayDeforBasicSettingByCompany dislaySetting;
	
	@Inject
	private DisplayInitialDeforScreenByCom initScreen;

	@POST
	@Path("viewL/getListYear")
	public List<YearDto> getComYearList() {
		return yearsByCompany.get(LaborWorkTypeAttr.DEFOR_LABOR.value);
	}

	@POST
	@Path("viewL/monthlyWorkTimeSet/add")
	public void registerMonthlyWorkTimeSet(CommandHandlerContext<SaveMonthlyWorkTimeSetComCommand> command) {
		registerHandler.handle(command);
	}

	@POST
	@Path("viewL/monthlyWorkTimeSet/update")
	public void updateMonthlyWorkTimeSet(CommandHandlerContext<SaveMonthlyWorkTimeSetComCommand> command) {
		updateHandler.handle(command);
	}

	@POST
	@Path("viewL/monthlyWorkTimeSet/delete")
	public void deleteMonthlyWorkTimeSet(CommandHandlerContext<DeleteTransMonthlyWorkTimeSetComCommand> command) {
		deleteHandler.handle(command);
	}
	
	@POST
	@Path("viewL/getBasicSetting")
	public DeforLaborMonthTimeComDto getBasicSetting() {
		return dislaySetting.displayDeforBasicSettingByCompany();
	}
	
	@POST
	@Path("viewL/initScreen")
	public DisplayInitialDeforScreenByCompanyDto initScreen() {
		return initScreen.displayInitialDeforScreenByCom() ;
	}
	
}
