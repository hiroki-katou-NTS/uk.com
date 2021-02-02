package nts.uk.file.at.ws.workinghours;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.workinghours.CompanyExportSevice;
import nts.uk.file.at.app.export.workinghours.YearsInput;

/**
 * 
 * @author chungnt
 *
 */

@Path("file/working/hours/report")
@Produces("application/json")
public class WorkingHoursExpostWs extends WebService {
	
	@Inject
	private CompanyExportSevice exportSevice;
	
	@POST
	@Path("export/company")
	public ExportServiceResult exportCompany(YearsInput input) {
		return this.exportSevice.start(input);
	}
}
