package nts.uk.screen.at.ws.kmk004;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettings;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.screen.at.app.query.kmk004.b.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.b.DisplayMonthlyWorkingHoursByCompany;
import nts.uk.screen.at.app.query.kmk004.b.DisplayMonthlyWorkingInput;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborComDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborTimeEmpDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborTimeShaDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborWkpDto;
import nts.uk.screen.at.app.query.kmk004.p.GetComBasicSetting;
import nts.uk.screen.at.app.query.kmk004.p.GetEmpBasicSetting;
import nts.uk.screen.at.app.query.kmk004.p.GetShaBasicSetting;
import nts.uk.screen.at.app.query.kmk004.p.GetWorkplaceBasicSetting;
import nts.uk.screen.at.app.query.kmk004.s.GetUsageUnitSetting;
import nts.uk.screen.at.app.query.kmk004.s.UsageUnitSettingDto;

/**
 * 
 * @author chungnt
 *
 */

@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004WebService extends WebService{

	@Inject
	private GetUsageUnitSetting getUsageUnitSetting;
	
	@Inject
	private DisplayBasicSettings basicSettings;
	
	@Inject
	private DisplayMonthlyWorkingHoursByCompany getworking;
	
	@Inject
	private GetComBasicSetting comBasicSetting;
	
	@Inject
	private GetWorkplaceBasicSetting wkpBasicSetting;
	
	@Inject
	private GetEmpBasicSetting empBasicSetting;
	
	@Inject
	private GetShaBasicSetting shaBasicSetting;
	
	//View S
	@POST
	@Path("getUsageUnitSetting")
	public UsageUnitSettingDto get() {
		return this.getUsageUnitSetting.get();
	}
	
	//ViewB
	@POST
	@Path("getDisplayBasicSetting")
	public DisplayBasicSettingsDto getDisplayBasicSetting() {
		return this.basicSettings.getSetting();
	}
	
	//Common
	@POST
	@Path("getWorkingHoursByCompany")
	public List<DisplayMonthlyWorkingDto> getDisplayMonthlyWorkingHoursByCompany(DisplayMonthlyWorkingInput param) {
		return this.getworking.get(param);
	}
	
	// View P
	// Company
	@POST
	@Path("viewP/com/basicSetting")
	public DeforLaborComDto getComBasicSetting() {
		return comBasicSetting.get();
	}
	
	// Workplace
	@POST
	@Path("viewP/wkp/basicSetting/{wkpId}")
	public DeforLaborWkpDto getWkpBasicSetting(@PathParam("wkpId") String wkpId) {
		return wkpBasicSetting.get(wkpId);
	}
	
	// Employment
	@POST
	@Path("viewP/emp/basicSetting/{empCode}")
	public DeforLaborTimeEmpDto getEmpBasicSetting(@PathParam("empCode") String empCode) {
		return empBasicSetting.get(empCode);
	}
	
	// Employee
	@POST
	@Path("viewP/emp/basicSetting/{empId}")
	public DeforLaborTimeShaDto getshaBasicSetting(@PathParam("empId") String empId) {
		return shaBasicSetting.get(empId);
	}
}
