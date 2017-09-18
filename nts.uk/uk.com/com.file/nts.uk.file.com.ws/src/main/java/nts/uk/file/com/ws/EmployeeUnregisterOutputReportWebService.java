package nts.uk.file.com.ws;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.time.GeneralDate;
import nts.uk.file.com.app.EmployeeUnregisterOutputExportService;

@Path("employee/report")
@Produces("application/json") 
public class EmployeeUnregisterOutputReportWebService {

	@Inject
	private EmployeeUnregisterOutputExportService reportService;

	@POST
	@Path("a")
	public ExportServiceResult generate(GeneralDate query) {

		return this.reportService.start(query);
	}
	@GET
	@Path("b")
	public void test() {
		System.out.println("a");
	}
}
