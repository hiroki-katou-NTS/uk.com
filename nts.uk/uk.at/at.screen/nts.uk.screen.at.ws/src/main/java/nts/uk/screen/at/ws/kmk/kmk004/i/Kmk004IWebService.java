package nts.uk.screen.at.ws.kmk.kmk004.i;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.i.DeleteFlexMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.i.DeleteFlexMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.i.RegisterFlexMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.i.UpdateFlexMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetemp.SaveMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.kmk004.i.AfterChangeFlexEmploymentSetting;
import nts.uk.screen.at.app.kmk004.i.AfterChangeFlexEmploymentSettingDto;
import nts.uk.screen.at.app.kmk004.i.AfterCopyFlexMonthlyWorkTimeSetEmp;
import nts.uk.screen.at.app.kmk004.i.DisplayInitialFlexScreenByEmployment;
import nts.uk.screen.at.app.kmk004.i.DisplayInitialFlexScreenByEmploymentDto;
import nts.uk.screen.at.app.kmk004.i.SelectEmploymentFlex;
import nts.uk.screen.at.app.kmk004.i.SelectEmploymentFlexDto;
import nts.uk.screen.at.app.kmk004.i.SelectFlexYearByEmployment;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentCodeDto;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;

@Path("screen/at/kmk004/i")
@Produces("application/json")
public class Kmk004IWebService {

	@Inject
	private DisplayInitialFlexScreenByEmployment displayInitialFlexScreenByEmployment;

	@Inject
	private SelectEmploymentFlex selectEmploymentFlex;

	@Inject
	private SelectFlexYearByEmployment selectFlexYearByEmployment;

	@Inject
	private RegisterFlexMonthlyWorkTimeSetEmpCommandHandler registerHandler;

	@Inject
	private UpdateFlexMonthlyWorkTimeSetEmpCommandHandler updateHandler;

	@Inject
	private DeleteFlexMonthlyWorkTimeSetEmpCommandHandler deleteHandler;

	@Inject
	private AfterCopyFlexMonthlyWorkTimeSetEmp afterCopyFlexMonthlyWorkTimeSetEmp;

	@Inject
	private AfterChangeFlexEmploymentSetting afterChangeFlexEmploymentSetting;

	@POST
	@Path("init-screen")
	public DisplayInitialFlexScreenByEmploymentDto initScreen() {
		return this.displayInitialFlexScreenByEmployment.displayInitialFlexScreenByEmployment();
	}

	@POST
	@Path("change-year/{empCd}")
	public List<YearDto> changeYear(@PathParam("empCd") String empCd) {
		return this.selectFlexYearByEmployment.selectFlexYearByEmployment(empCd);
	}

	@POST
	@Path("change-empCd/{empCd}")
	public SelectEmploymentFlexDto changeempCd(@PathParam("empCd") String empCd) {
		return this.selectEmploymentFlex.selectEmploymentFlex(empCd);
	}

	@POST
	@Path("register")
	public List<EmploymentCodeDto> register(SaveMonthlyWorkTimeSetEmpCommand command) {
		return this.registerHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(SaveMonthlyWorkTimeSetEmpCommand command) {
		this.updateHandler.handle(command);
	}

	@POST
	@Path("delete")
	public List<EmploymentCodeDto> delete(DeleteFlexMonthlyWorkTimeSetEmpCommand command) {
		return this.deleteHandler.handle(command);
	}

	@POST
	@Path("after-copy")
	public List<EmploymentCodeDto> afterCopy() {
		return this.afterCopyFlexMonthlyWorkTimeSetEmp.afterCopyFlexMonthlyWorkTimeSetEmp();
	}

	@POST
	@Path("change-setting/{empCd}")
	public AfterChangeFlexEmploymentSettingDto changeSetting(@PathParam("empCd") String empCd) {
		return this.afterChangeFlexEmploymentSetting.afterChangeFlexEmploymentSetting(empCd);
	}

}
