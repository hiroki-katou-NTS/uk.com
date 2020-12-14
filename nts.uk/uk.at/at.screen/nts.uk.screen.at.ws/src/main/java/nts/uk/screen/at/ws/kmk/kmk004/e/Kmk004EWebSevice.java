package nts.uk.screen.at.ws.kmk.kmk004.e;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
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
	
	
	@POST
	@Path("viewd/sha/getEmployeeId")
	public List<EmployeeIdDto> getEmployeeId() {
		return this.employeeList.get(LaborWorkTypeAttr.REGULAR_LABOR);
	}
	
}
