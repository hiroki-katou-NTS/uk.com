package nts.uk.screen.at.ws.kmk.kmk004.i;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.SaveMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.i.DeleteFlexMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.i.DeleteFlexMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.i.RegisterFlexMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.i.UpdateFlexMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.kmk004.i.AfterChangeFlexEmploymentSetting;
import nts.uk.screen.at.app.kmk004.i.AfterChangeFlexEmploymentSettingDto;
import nts.uk.screen.at.app.kmk004.i.AfterCopyFlexMonthlyWorkTimeSetEmp;
import nts.uk.screen.at.app.kmk004.i.DisplayInitialFlexScreenByEmployment;
import nts.uk.screen.at.app.kmk004.i.DisplayInitialFlexScreenByEmploymentDto;
import nts.uk.screen.at.app.kmk004.i.SelectEmploymentFlex;
import nts.uk.screen.at.app.kmk004.i.SelectEmploymentFlexDto;
import nts.uk.screen.at.app.kmk004.i.SelectFlexYearByEmployment;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;

@Path("screen/at/kmk004/i")
@Produces("application/json")
public class Kmk004IWebService {

	@Inject
	private DisplayInitialFlexScreenByEmployment initScreen;

	@Inject
	private SelectEmploymentFlex selectEmployment;

	@Inject
	private SelectFlexYearByEmployment selectFlexYear;

	@Inject
	private RegisterFlexMonthlyWorkTimeSetEmpCommandHandler registerHandler;

	@Inject
	private UpdateFlexMonthlyWorkTimeSetEmpCommandHandler updateHandler;

	@Inject
	private DeleteFlexMonthlyWorkTimeSetEmpCommandHandler deleteHandler;

	@Inject
	private AfterCopyFlexMonthlyWorkTimeSetEmp afterCopy;

	@Inject
	private AfterChangeFlexEmploymentSetting afterChangeSetting;

	@POST
	@Path("init-screen/{empCd}")
	public DisplayInitialFlexScreenByEmploymentDto initScreen(@PathParam("empCd") String empCd) {
		return this.initScreen.displayInitialFlexScreenByEmployment(empCd);
	}

	@POST
	@Path("change-year/{empCd}/{year}")
	public List<DisplayMonthlyWorkingDto> changeYear(@PathParam("empCd") String empCd, @PathParam("year") int year) {
		return this.selectFlexYear.selectFlexYearByEmployment(empCd, year);
	}

	@POST
	@Path("change-empcd/{empCd}")
	public SelectEmploymentFlexDto changeempCd(@PathParam("empCd") String empCd) {
		return this.selectEmployment.selectEmploymentFlex(empCd);
	}

	@POST
	@Path("register")
	public List<String> register(SaveMonthlyWorkTimeSetEmpCommand command) {
		return this.registerHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(SaveMonthlyWorkTimeSetEmpCommand command) {
		this.updateHandler.handle(command);
	}

	@POST
	@Path("delete")
	public List<String> delete(DeleteFlexMonthlyWorkTimeSetEmpCommand command) {
		return this.deleteHandler.handle(command);
	}

	@POST
	@Path("after-copy")
	public List<String> afterCopy() {
		return this.afterCopy.afterCopyFlexMonthlyWorkTimeSetEmp();
	}

	@POST
	@Path("change-setting/{empCd}")
	public AfterChangeFlexEmploymentSettingDto changeSetting(@PathParam("empCd") String empCd) {
		return this.afterChangeSetting.afterChangeFlexEmploymentSetting(empCd);
	}

}
