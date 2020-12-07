package nts.uk.screen.at.ws.kdl045.query;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.kdl045.query.GetInformationStartup;
import nts.uk.screen.at.app.kdl045.query.GetInformationStartupCmd;
import nts.uk.screen.at.app.kdl045.query.GetInformationStartupOutput;
import nts.uk.screen.at.app.kdl045.query.GetMoreInformation;
import nts.uk.screen.at.app.kdl045.query.GetMoreInformationCmd;
import nts.uk.screen.at.app.kdl045.query.GetMoreInformationOutput;

/**
 * 
 * @author tutk
 *
 */
@Path("at/screen/kdl045/query")
@Produces("application/json")
public class GetInformationWS extends WebService {
	
	@Inject
	private GetInformationStartup getInformationStartup;
	
	@Inject
	private GetMoreInformation getMoreInformation;
	
	@POST
	@Path("getInformationStartup")
	public GetInformationStartupOutput getInformationStartup(GetInformationStartupCmd command){
		GetInformationStartupOutput data = getInformationStartup.getInformationStartup(
				command.getEmployeeId(), 
				GeneralDate.fromString(command.getBaseDate(), "yyyy/MM/dd"),
				command.getListTimeVacationAndType(),
				command.getWorkTimeCode(), 
				command.getTargetOrgIdenInforDto());
		return data;
	}
	
	@POST
	@Path("getMoreInformation")
	public GetMoreInformationOutput getMoreInformation(GetMoreInformationCmd command){
		GetMoreInformationOutput data = getMoreInformation.getMoreInformation(command.getEmployeeId(), command.getWorkType(), command.getWorkTimeCode());
		return data;
	}
}
