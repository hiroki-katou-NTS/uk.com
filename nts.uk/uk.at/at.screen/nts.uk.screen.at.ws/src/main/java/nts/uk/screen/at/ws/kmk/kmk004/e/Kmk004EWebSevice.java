package nts.uk.screen.at.ws.kmk.kmk004.e;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.kmk004.e.BasicSettingsForEmployee;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeIdDto;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeList;

/**
 * 
 * @author chungnt
 *
 */

@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004EWebSevice extends WebService {
	
	@Inject
	private EmployeeList employeeList;
	
	@Inject
	private BasicSettingsForEmployee basicSettings;
	
	
	@POST
	@Path("viewe/sha/getEmployeeId")
	public List<EmployeeIdDto> getEmployeeId() {
		return this.employeeList.get(LaborWorkTypeAttr.REGULAR_LABOR);
	}
	
	@POST
	@Path("viewe/sha/getBaseSetting/{sid}")
	public DisplayBasicSettingsDto getBaseSetting(@PathParam("sid") String sid) {
		return this.basicSettings.getSetting(sid);
	}
	
}
