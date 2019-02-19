package nts.uk.file.com.ws.person.check.consistency;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.file.com.app.person.check.consistency.PersonInfoConsistencyCheckExportService;
import nts.uk.file.com.app.person.check.consistency.datasource.EmployeInfoErrorDataSource;

@Path("person/consistency/check/report/")
@Produces("application/json") 
public class PersonInfoConsistencyCheckWebservice {
	@Inject
	private PersonInfoConsistencyCheckExportService consistencyCheckService;
	@POST
	@Path("print/error")
	public ExportServiceResult generate(List<EmployeInfoErrorDataSource> query) {
		return this.consistencyCheckService.start(query);
	}
}
