package nts.uk.screen.at.ws.knr.knr002.d;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.d.GetDestinationCopy;
import nts.uk.screen.at.app.query.knr.knr002.d.GetDestinationCopyDto;

/**
 * 
 * @author xuannt
 *
 */
@Path("screen/at/destinationcopy")
@Produces(MediaType.APPLICATION_JSON)
public class DestinationCopyScreenWS {
	@Inject
	private GetDestinationCopy getDestinationCopy;

//	@POST
//	@Path("getdestinationcopylist/{empInfoTerCode}")
//	public List<EmpInfoTerminal> getDestinationCopyList(@PathParam("empInfoTerCode") String empInforTerCode) {
//		List<EmpInfoTerminal> result = this.getDestinationCopy.getEmpInfoTerDestinalList(empInforTerCode);
//		return result;
//	}

	@POST
	@Path("getdestinationcopylist/{empInfoTerCode}")
	public List<GetDestinationCopyDto> getDestinationCopyList(@PathParam("empInfoTerCode") String empInforTerCode) {
		List<GetDestinationCopyDto> result = this.getDestinationCopy.getEmpInfoTerDestinalList(empInforTerCode);
		return result;
	}
}
