package nts.uk.screen.at.ws.knr.knr001.a;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.knr.knr001.a.GetAListOfEmpInfoTerminals;
import nts.uk.screen.at.app.query.knr.knr001.a.GetAListOfEmpInfoTerminalsDto;
import nts.uk.screen.at.app.query.knr.knr001.a.GetInformationAboutTheSelectedDevice;
import nts.uk.screen.at.app.query.knr.knr001.a.GetInformationAboutTheSelectedDeviceDto;

/**
 *
 * @author xuannt
 *
 */
@Path("screen/at/empInfoTerminal")
@Produces(MediaType.APPLICATION_JSON)
public class EmpInfoTerScreenWS extends WebService {

	@Inject
	private GetAListOfEmpInfoTerminals screen1;
	@Inject
	private GetInformationAboutTheSelectedDevice screen2;

	@POST
	@Path("getAll")
	public List<GetAListOfEmpInfoTerminalsDto> getAll() {
		return this.screen1.getAll();
	}

	@POST
	@Path("getDetails/{empInfoTerCode}")
	public GetInformationAboutTheSelectedDeviceDto getDetails(@PathParam("empInfoTerCode") int empInforTerCode) {
		return this.screen2.getDetails(empInforTerCode);
	}

}
