package nts.uk.screen.at.ws.dailyschedule;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputConditionRepository;

@Path("screen/at/dailyschedule")
@Produces("application/json")
public class Kwr001WebService extends WebService {
	@Inject
	private WorkScheduleOutputConditionRepository outputConditionRepository;
	
	@POST
	@Path("export")
	public void exportData() {
		
	}
}
