package nts.uk.screen.at.ws.knr.knr002.g;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.g.GetWorkType;
import nts.uk.screen.at.app.query.knr.knr002.g.GetWorkTypeDto;

/**
*
* @author xuannt
*
*/
@Path("screen/at/worktypetransfer")
@Produces(MediaType.APPLICATION_JSON)
public class GetWorkTypeScreenWS {
	@Inject
	private GetWorkType getWorkType;
	
	@POST
	@Path("getWorkTypes/{empInfoTerCode}")
	public GetWorkTypeDto getTimeRecordReqSetting(@PathParam("empInfoTerCode") String empInforTerCode) {
		return this.getWorkType.getWorkTypes(empInforTerCode);
	}

}
