package nts.uk.file.hr.ws.databeforereflecting.retirementinformation;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.hr.app.databeforereflecting.retirementinformation.RetirementInformationExportService;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.SearchRetiredEmployeesQuery;

@Path("file/hr/report/retirementinformation")
@Produces("application/json")
public class RetirementInformationExportWebSevices extends WebService {
	@Inject
	private RetirementInformationExportService service;

	@POST
	@Path("export")
	public ExportServiceResult generate(SearchRetiredEmployeesQuery query) {
		return service.start(query);
	}
}
