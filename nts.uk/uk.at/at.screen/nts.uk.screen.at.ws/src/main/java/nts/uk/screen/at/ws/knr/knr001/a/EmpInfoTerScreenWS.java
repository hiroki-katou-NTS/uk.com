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
import nts.uk.screen.at.app.query.knr.knr001.a.GetEmpInfoTerminalListOutputDto;
import nts.uk.screen.at.app.query.knr.knr001.a.GetSelectedTerminalInfo;
import nts.uk.screen.at.app.query.knr.knr001.a.GetSelectedTerminalInfoDto;
import nts.uk.screen.at.app.query.knr.knr001.a.GetWorkLocationNameDto;
import nts.uk.screen.at.app.query.knr.knr001.a.GetWorkplaceNameChangingBaseDateDto;
import nts.uk.screen.at.app.query.knr.knr001.a.GetWorkplaceNameChangingBaseDateInput;
import nts.uk.screen.at.app.query.knr.knr001.a.GetWorkplaceNameChangingBaseDateSQ;

/**
 *
 * @author xuannt
 *
 */
@Path("screen/at/empinfoterminal")
@Produces(MediaType.APPLICATION_JSON)
public class EmpInfoTerScreenWS extends WebService {

	@Inject
	private GetEmpInfoTerminalList getEmpInfoTerminalList;
	@Inject
	private GetSelectedTerminalInfo getSelectedTerminalInfo;
	
	@Inject
	private GetWorkplaceNameChangingBaseDateSQ getWorkplaceNameChangingBaseDateSQ;

	@POST
	@Path("getall")
	public GetEmpInfoTerminalListOutputDto getAll() {
		return this.getEmpInfoTerminalList.getAll();
	}

	@POST
	@Path("getdetails/{empInfoTerCode}")
	public GetSelectedTerminalInfoDto getDetails(@PathParam("empInfoTerCode") String empInforTerCode) {
		return this.getSelectedTerminalInfo.getDetails(empInforTerCode);
	}

	@POST
	@Path("getworklocationname/{workLocationCD}")
	public GetWorkLocationNameDto getWorkLocationName(@PathParam("workLocationCD") String workLocationCD) {
		return this.getSelectedTerminalInfo.getWorkLocationName(workLocationCD);
	}
	
	@POST
	@Path("getWorkplaceNameChangingBaseDate")
	public List<GetWorkplaceNameChangingBaseDateDto> getWorkplaceNameChangingBaseDate(GetWorkplaceNameChangingBaseDateInput input) {
		return this.getWorkplaceNameChangingBaseDateSQ.getWorkplaceNameChangingBaseDate(input);
	}
}
