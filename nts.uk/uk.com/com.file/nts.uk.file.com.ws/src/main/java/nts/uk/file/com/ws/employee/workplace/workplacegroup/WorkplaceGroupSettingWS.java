package nts.uk.file.com.ws.employee.workplace.workplacegroup;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import approve.employee.workplace.workplacegroup.CreateUnsetWorkplaceReport;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;

@Path("com/employee/workplace/group")
@Produces("application/json")
public class WorkplaceGroupSettingWS extends WebService {
	
	@Inject
	private CreateUnsetWorkplaceReport createUnsetWorkplaceReport;
	
	@POST
	@Path("workplaceReport")
	public ExportServiceResult createUnsetWorkplaceReport() {
		return createUnsetWorkplaceReport.start(GeneralDate.today());
	}
}
