package nts.uk.screen.at.ws.knr.knr002.g;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.g.GetWorkTime;
import nts.uk.screen.at.app.query.knr.knr002.g.GetWorkTimeDto;

/**
 * 
 * @author xuannt
 *
 */
@Path("screen/at/worktimetransfer")
@Produces(MediaType.APPLICATION_JSON)
public class GetWorkTimeScreenWS {
	@Inject
	private GetWorkTime getWorkTime;

	@POST
	@Path("getWorkTimes/{empInfoTerCode}")
	public GetWorkTimeDto getTimeRecordReqSetting(@PathParam("empInfoTerCode") String empInforTerCode) {
		return this.getWorkTime.getWorkTimes(empInforTerCode);
	}
}
