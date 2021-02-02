package nts.uk.screen.at.ws.kmk.kmk004.g;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.SaveMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.g.DeleteFlexMonthlyWorkingHoursByComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.g.RegisterFlexMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.g.UpdateFlexMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.kmk004.g.AfterChangeFlexCompanySetting;
import nts.uk.screen.at.app.kmk004.g.DisplayFlexBasicSettingByCompanyDto;
import nts.uk.screen.at.app.kmk004.g.DisplayInitialFlexScreenByCompany;
import nts.uk.screen.at.app.kmk004.g.DisplayInitialFlexScreenByCompanyDto;
import nts.uk.screen.at.app.kmk004.g.SelectFlexYearByCompany;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;

@Path("screen/at/kmk004/g")
@Produces("application/json")
public class Kmk004GWebService {

	@Inject
	private DisplayInitialFlexScreenByCompany displayInitialFlexScreenByCompany;

	@Inject
	private SelectFlexYearByCompany selectYear;

	@Inject
	private RegisterFlexMonthlyWorkTimeSetComCommandHandler registerHandler;

	@Inject
	private UpdateFlexMonthlyWorkTimeSetComCommandHandler updateHandler;

	@Inject
	private DeleteFlexMonthlyWorkingHoursByComCommandHandler deleteHandler;

	@Inject
	private AfterChangeFlexCompanySetting afterChangeSetting;

	@POST
	@Path("init-screen")
	public DisplayInitialFlexScreenByCompanyDto initScreen() {
		return this.displayInitialFlexScreenByCompany.displayInitialScreen();
	}

	@POST
	@Path("change-year/{year}")
	public List<DisplayMonthlyWorkingDto> changeYear(@PathParam("year") int year) {
		return this.selectYear.selectYearByCompany(year);
	}

	@POST
	@Path("register")
	public void register(SaveMonthlyWorkTimeSetComCommand command) {
		this.registerHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(SaveMonthlyWorkTimeSetComCommand command) {
		this.updateHandler.handle(command);
	}

	@POST
	@Path("delete/{year}")
	public void delete(@PathParam("year") int year) {
		this.deleteHandler.handle(year);
	}

	@POST
	@Path("change-setting")
	public DisplayFlexBasicSettingByCompanyDto changeSetting() {
		return this.afterChangeSetting.afterChangeFlexCompanySetting();
	}

}
