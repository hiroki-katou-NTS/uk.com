package nts.uk.screen.at.ws.kdl.kdl014.a;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kdl.kdl014.a.ReferToTheStampingResults;
import nts.uk.screen.at.app.query.kdl.kdl014.a.dto.Kdl014EmpParamDto;
import nts.uk.screen.at.app.query.kdl.kdl014.a.dto.ReferToTheStampingResultsDto;

@Path("screen/at/kdl014")
@Produces("application/json")
public class Kdl014EmpWebService extends WebService {

	@Inject
	private ReferToTheStampingResults stampResultSQ;
	
	@POST
	@Path("get")
	public ReferToTheStampingResultsDto get(Kdl014EmpParamDto input) {
		return stampResultSQ.get(input);
	}
}
