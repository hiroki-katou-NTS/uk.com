package nts.uk.screen.at.ws.knr.knr001.a;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.knr.knr001.a.GetEmpInfoTerminalList;
import nts.uk.screen.at.app.query.knr.knr001.a.GetEmpInfoTerminalListDto;
import nts.uk.screen.at.app.query.knr.knr001.a.GetSelectedDeviceInfo;
import nts.uk.screen.at.app.query.knr.knr001.a.GetSelectedDeviceInfoDto;
import nts.uk.screen.at.app.query.knr.knr001.a.GetWorkLocationNameDto;

/**
 *
 * @author xuannt
 *
 */
@Path("screen/at/empInfoTerminal")
@Produces(MediaType.APPLICATION_JSON)
public class EmpInfoTerScreenWS extends WebService {

	@Inject
	private GetEmpInfoTerminalList getEmpInfoTerminalList;
	@Inject
	private GetSelectedDeviceInfo getSelectedDeviceInfo;

	@POST
	@Path("getAll")
	public List<GetEmpInfoTerminalListDto> getAll() {
		return this.getEmpInfoTerminalList.getAll();
	}

	@POST
	@Path("getDetails/{empInfoTerCode}")
	public GetSelectedDeviceInfoDto getDetails(@PathParam("empInfoTerCode") int empInforTerCode) {
		return this.getSelectedDeviceInfo.getDetails(empInforTerCode);
	}

	@POST
	@Path("getWorkLocationName/{workLocationCD}")
	public GetWorkLocationNameDto getWorkLocationName(@PathParam("workLocationCD") String workLocationCD) {
		return this.getSelectedDeviceInfo.getWorkLocationName(workLocationCD);
	}
}
