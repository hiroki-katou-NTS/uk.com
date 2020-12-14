package nts.uk.screen.at.ws.kmk.kmk004.k;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.k.DeleteFlexBasicSettingByEmployeeCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.k.DeleteFlexBasicSettingByEmploymentCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.k.DeleteFlexBasicSettingByWorkPlaceCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.k.EmpFlexMonthActCalSetCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.k.RegisterFlexBasicSettingByEmployeeCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.k.RegisterFlexBasicSettingByEmploymentCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.k.RegisterFlexBasicSettingByWorkPlaceCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.k.ShaFlexMonthActCalSetCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.k.UpdateFlexBasicSettingByCompanyCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.k.UpdateFlexBasicSettingByCompanyCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.k.UpdateFlexBasicSettingByEmployeeCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.k.UpdateFlexBasicSettingByEmploymentCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.k.UpdateFlexBasicSettingByWorkPlaceCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.k.WkpFlexMonthActCalSetCommand;
import nts.uk.screen.at.app.kmk004.g.DisplayFlexBasicSettingByCompanyDto;
import nts.uk.screen.at.app.kmk004.h.WkpFlexMonthActCalSetDto;
import nts.uk.screen.at.app.kmk004.i.EmpFlexMonthActCalSetDto;
import nts.uk.screen.at.app.kmk004.j.ShaFlexMonthActCalSetDto;
import nts.uk.screen.at.app.kmk004.k.DisplayInitialFlexBasicSettingByCompany;
import nts.uk.screen.at.app.kmk004.k.DisplayInitialFlexBasicSettingByEmployee;
import nts.uk.screen.at.app.kmk004.k.DisplayInitialFlexBasicSettingByEmployment;
import nts.uk.screen.at.app.kmk004.k.DisplayInitialFlexBasicSettingByWorkPlace;

@Path("screen/at/kmk004/k")
@Produces("application/json")
public class Kmk004KWebService {

	@Inject
	private DisplayInitialFlexBasicSettingByCompany displayCom;
	@Inject
	private DisplayInitialFlexBasicSettingByWorkPlace displayWkp;
	@Inject
	private DisplayInitialFlexBasicSettingByEmployment displayEmp;
	@Inject
	private DisplayInitialFlexBasicSettingByEmployee displaySha;
	@Inject
	private UpdateFlexBasicSettingByCompanyCommandHandler updateComHandler;
	@Inject
	private UpdateFlexBasicSettingByWorkPlaceCommandHandler updateWkpHandler;
	@Inject
	private UpdateFlexBasicSettingByEmploymentCommandHandler updateEmpHandler;
	@Inject
	private UpdateFlexBasicSettingByEmployeeCommandHandler updateShaHandler;
	@Inject
	private RegisterFlexBasicSettingByWorkPlaceCommandHandler registerWkpHandler;
	@Inject
	private RegisterFlexBasicSettingByEmploymentCommandHandler registerEmpHandler;
	@Inject
	private RegisterFlexBasicSettingByEmployeeCommandHandler registerShaHandler;
	@Inject
	private DeleteFlexBasicSettingByWorkPlaceCommandHandler deleteWkpHandler;
	@Inject
	private DeleteFlexBasicSettingByEmploymentCommandHandler deleteEmpHandler;
	@Inject
	private DeleteFlexBasicSettingByEmployeeCommandHandler deleteShaHandler;

	@POST
	@Path("display-com")
	public DisplayFlexBasicSettingByCompanyDto displayComSetting() {
		return this.displayCom.displayInitialFlexBasicSettingByCompany();
	}

	@POST
	@Path("display-wkp/{wkpId}")
	public WkpFlexMonthActCalSetDto displayWkpSetting(@PathParam("wkpId") String wkpId) {
		return this.displayWkp.displayInitialFlexBasicSettingByWorkPlace(wkpId);
	}

	@POST
	@Path("display-emp/{empId}")
	public EmpFlexMonthActCalSetDto displayEmpSetting(@PathParam("empId") String empId) {
		return this.displayEmp.displayInitialFlexBasicSettingByEmployment(empId);
	}

	@POST
	@Path("display-sha/{sID}")
	public ShaFlexMonthActCalSetDto displayShaSetting(@PathParam("sID") String sID) {
		return this.displaySha.displayInitialFlexBasicSettingByEmployee(sID);
	}

	@POST
	@Path("update-com")
	public void updateCom(UpdateFlexBasicSettingByCompanyCommand command) {
		this.updateComHandler.handle(command);
	}

	@POST
	@Path("update-wkp")
	public void updateWkp(WkpFlexMonthActCalSetCommand command) {
		this.updateWkpHandler.handle(command);
	}

	@POST
	@Path("update-emp")
	public void updateEmp(EmpFlexMonthActCalSetCommand command) {
		this.updateEmpHandler.handle(command);
	}

	@POST
	@Path("update-sha")
	public void updateSha(ShaFlexMonthActCalSetCommand command) {
		this.updateShaHandler.handle(command);
	}

	@POST
	@Path("register-wkp")
	public void registerWkp(WkpFlexMonthActCalSetCommand command) {
		this.registerWkpHandler.handle(command);
	}

	@POST
	@Path("register-emp")
	public void registerEmp(EmpFlexMonthActCalSetCommand command) {
		this.registerEmpHandler.handle(command);
	}

	@POST
	@Path("register-sha")
	public void registerSha(ShaFlexMonthActCalSetCommand command) {
		this.registerShaHandler.handle(command);
	}

	@POST
	@Path("delete-wkp")
	public void deleteWkp(String wkpId) {
		this.deleteWkpHandler.handle(wkpId);
	}

	@POST
	@Path("delete-emp")
	public void deleteEmp(String empCd) {
		this.deleteEmpHandler.handle(empCd);
	}

	@POST
	@Path("delete-sha")
	public void deleteSha(String sId) {
		this.deleteShaHandler.handle(sId);
	}

}
