package nts.uk.screen.at.ws.knr.knr002.g;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.g.GetWorkTypeToBeSent;
import nts.uk.screen.at.app.query.knr.knr002.g.GetWorkTypeToBeSentDto;

/**
*
* @author xuannt
*
*/
@Path("screen/at/worktypetransfer")
@Produces(MediaType.APPLICATION_JSON)
public class GetWorkTypeScreenWS {
	@Inject
	private GetWorkTypeToBeSent getWorkTypeToBeSent;
	
	@POST
	@Path("getworktypetobesent/{empInfoTerCode}")
	public GetWorkTypeToBeSentDto getTimeRecordReqSetting(@PathParam("empInfoTerCode") String empInforTerCode) {
		return this.getWorkTypeToBeSent.getWorkTypeToBeSent(empInforTerCode);
	}

}
