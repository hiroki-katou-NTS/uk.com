package nts.uk.screen.at.ws.kmk.kmk004.l;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.SaveMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.l.DeleteTransMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.l.DeleteTransMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.l.RegisterTransMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.l.UpdateTransMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.kmk004.l.DeforLaborMonthTimeComDto;
import nts.uk.screen.at.app.kmk004.l.DisplayDeforBasicSettingByCompany;
import nts.uk.screen.at.app.kmk004.l.DisplayInitialDeforScreenByCom;
import nts.uk.screen.at.app.kmk004.l.DisplayInitialDeforScreenByCompanyDto;
import nts.uk.screen.at.app.kmk004.l.SelectYearByCompany;
import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingInput;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kmk004/viewL")
@Produces("application/json")
public class Kmk004LWebService {

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
	
	@Inject
	private SelectYearByCompany selectYearByCompany;

	@POST
	@Path("monthlyWorkTimeSet/add")
	public void registerMonthlyWorkTimeSet(SaveMonthlyWorkTimeSetComCommand command) {
		registerHandler.handle(command);
	}

	@POST
	@Path("monthlyWorkTimeSet/update")
	public void updateMonthlyWorkTimeSet(SaveMonthlyWorkTimeSetComCommand command) {
		updateHandler.handle(command);
	}

	@POST
	@Path("monthlyWorkTimeSet/delete")
	public void deleteMonthlyWorkTimeSet(DeleteTransMonthlyWorkTimeSetComCommand command) {
		deleteHandler.handle(command);
	}
	
	@POST
	@Path("getBasicSetting")
	public DeforLaborMonthTimeComDto getBasicSetting() {
		return dislaySetting.displayDeforBasicSettingByCompany();
	}
	
	@POST
	@Path("initScreen")
	public DisplayInitialDeforScreenByCompanyDto initScreen() {
		return initScreen.displayInitialDeforScreenByCom() ;
	}
	
	@POST
	@Path("getWorkingHoursByCompany")
	public List<WorkTimeComDto> getWorkingHoursByCompany(DisplayMonthlyWorkingInput param) {
		return selectYearByCompany.getDeforDisplayMonthlyWorkingHoursByCompany(param) ;
	}
	
}
