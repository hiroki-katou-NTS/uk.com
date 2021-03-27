package nts.uk.screen.at.ws.kdl.kdl053;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.screen.at.app.kdl053.RegistrationErrorListDto;
import nts.uk.screen.at.app.kdl053.RegistrationErrorListExportService;

/**
 * 
 * @author quytb
 *
 */
@Path("screen/at/kdl053")
@Produces("application/json")
public class RegistrationErrorListWS {
	@Inject
	private RegistrationErrorListExportService registrationErrorListExportService;
	
	@POST
	@Path("exportCsv")
	public ExportServiceResult exportCsvRegistrationErrorList(List<RegistrationErrorListDto> command) {
		return this.registrationErrorListExportService.start(command);
	}
}
