package nts.uk.screen.at.ws.kmk.kmk004.p;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.p.AddEmpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.AddShaBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.AddWkpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.ComBasicSettingCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.p.EmpBasicSettingCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.p.RemoveEmpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.RemoveShaBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.RemoveWkpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.ShaBasicSettingCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.p.UpdateComBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.UpdateEmpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.UpdateShaBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.UpdateWkpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.WkpBasicSettingCommand;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborComDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborEmpDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborShaDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborWkpDto;
import nts.uk.screen.at.app.query.kmk004.p.GetComBasicSetting;
import nts.uk.screen.at.app.query.kmk004.p.GetEmpBasicSetting;
import nts.uk.screen.at.app.query.kmk004.p.GetShaBasicSetting;
import nts.uk.screen.at.app.query.kmk004.p.GetWorkplaceBasicSetting;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kmk004/viewP")
@Produces("application/json")
public class KMK004PWebService {

	/** Company */
	@Inject
	private GetComBasicSetting comBasicSetting;

	@Inject
	private UpdateComBasicSettingCommandHandler updateComBasicSettingCommandHandler;

	/** Workplace */
	@Inject
	private GetWorkplaceBasicSetting wkpBasicSetting;

	@Inject
	private AddWkpBasicSettingCommandHandler addWkpBasicSettingCommandHandler;

	@Inject
	private UpdateWkpBasicSettingCommandHandler updateWkpBasicSettingCommandHandler;

	@Inject
	private RemoveWkpBasicSettingCommandHandler removeWkpBasicSettingCommandHandler;

	/** Employment */
	@Inject
	private GetEmpBasicSetting empBasicSetting;

	@Inject
	private AddEmpBasicSettingCommandHandler addEmpBasicSettingCommandHandler;

	@Inject
	private UpdateEmpBasicSettingCommandHandler updateEmpBasicSettingCommandHandler;

	@Inject
	private RemoveEmpBasicSettingCommandHandler removeEmpBasicSettingCommandHandler;

	/** Employee */
	@Inject
	private GetShaBasicSetting shaBasicSetting;

	@Inject
	private AddShaBasicSettingCommandHandler addShaBasicSettingCommandHandler;

	@Inject
	private UpdateShaBasicSettingCommandHandler updateShaBasicSettingCommandHandler;

	@Inject
	private RemoveShaBasicSettingCommandHandler removeShaBasicSettingCommandHandler;

	// Company
	@POST
	@Path("com/basicSetting")
	public DeforLaborComDto getComBasicSetting() {
		return comBasicSetting.get();
	}

	@POST
	@Path("com/basicSetting/update")
	public void updateComBasicSetting(ComBasicSettingCommand command) {
		updateComBasicSettingCommandHandler.handle(command);
	}

	// Workplace
	@POST
	@Path("wkp/basicSetting/{wkpId}")
	public DeforLaborWkpDto getWkpBasicSetting(@PathParam("wkpId") String wkpId) {
		return wkpBasicSetting.get(wkpId);
	}

	@POST
	@Path("wkp/basicSetting/add")
	public void addWkpBasicSetting(WkpBasicSettingCommand command) {
		addWkpBasicSettingCommandHandler.handle(command);
	}

	@POST
	@Path("wkp/basicSetting/update")
	public void updateWkpBasicSetting(WkpBasicSettingCommand command) {
		updateWkpBasicSettingCommandHandler.handle(command);
	}

	@POST
	@Path("wkp/basicSetting/delete")
	public void deleteWkpBasicSetting(WkpBasicSettingCommand command) {
		removeWkpBasicSettingCommandHandler.handle(command);
	}

	// Employment
	@POST
	@Path("emp/basicSetting/{empCode}")
	public DeforLaborEmpDto getEmpBasicSetting(@PathParam("empCode") String empCode) {
		return empBasicSetting.get(empCode);
	}

	@POST
	@Path("emp/basicSetting/add")
	public void addEmpBasicSetting(EmpBasicSettingCommand command) {
		addEmpBasicSettingCommandHandler.handle(command);
	}

	@POST
	@Path("emp/basicSetting/update")
	public void updateEmpBasicSetting(EmpBasicSettingCommand command) {
		updateEmpBasicSettingCommandHandler.handle(command);
	}

	@POST
	@Path("emp/basicSetting/delete")
	public void deleteEmpBasicSetting(EmpBasicSettingCommand command) {
		removeEmpBasicSettingCommandHandler.handle(command);
	}

	// Employee
	@POST
	@Path("sha/basicSetting/{empId}")
	public DeforLaborShaDto getshaBasicSetting(@PathParam("empId") String empId) {
		return shaBasicSetting.get(empId);
	}

	@POST
	@Path("sha/basicSetting/add")
	public void addShaBasicSetting(ShaBasicSettingCommand command) {
		addShaBasicSettingCommandHandler.handle(command);
	}

	@POST
	@Path("sha/basicSetting/update")
	public void updateShaBasicSetting(ShaBasicSettingCommand command) {
		updateShaBasicSettingCommandHandler.handle(command);
	}

	@POST
	@Path("sha/basicSetting/delete")
	public void deleteShaBasicSetting(ShaBasicSettingCommand command) {
		removeShaBasicSettingCommandHandler.handle(command);
	}

}
