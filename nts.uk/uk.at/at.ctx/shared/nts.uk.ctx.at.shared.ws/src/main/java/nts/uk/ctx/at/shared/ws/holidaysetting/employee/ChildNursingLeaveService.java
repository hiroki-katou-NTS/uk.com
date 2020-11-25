package nts.uk.ctx.at.shared.ws.holidaysetting.employee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.Data;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.holidaysetting.employee.ManagementClassificationByEmployeeDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave.ChildNursingLeaveFinder;

@Path("at/shared/holidaysetting/employee")
@Produces(MediaType.APPLICATION_JSON)
public class ChildNursingLeaveService extends WebService {
	@Inject
	private ChildNursingLeaveFinder childNursingLeaveFinder;
	
	@Path("startPage")
	@POST
	public ManagementClassificationByEmployeeDto startPage(StartPageParam param) {
		return this.childNursingLeaveFinder.startPage(param.getEmployeeIds(), param.getBaseDate());
	}
	
}

@Data
class StartPageParam {
	private GeneralDate baseDate;
	private List<String> employeeIds;
}

