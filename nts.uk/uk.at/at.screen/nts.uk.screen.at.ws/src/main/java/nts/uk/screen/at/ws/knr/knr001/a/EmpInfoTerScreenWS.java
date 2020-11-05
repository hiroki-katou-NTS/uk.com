package nts.uk.screen.at.ws.knr.knr001.a;

import java.util.ArrayList;
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
		List<GetAListOfEmpInfoTerminalsDto> test = new ArrayList<GetAListOfEmpInfoTerminalsDto>();
		test.add(new GetAListOfEmpInfoTerminalsDto(1, "empInfoTer1"));
		test.add(new GetAListOfEmpInfoTerminalsDto(2, "empInfoTer2"));
		test.add(new GetAListOfEmpInfoTerminalsDto(3, "empInfoTer3"));
		test.add(new GetAListOfEmpInfoTerminalsDto(4, "empInfoTer4"));
		test.add(new GetAListOfEmpInfoTerminalsDto(5, "empInfoTer5"));
		test.add(new GetAListOfEmpInfoTerminalsDto(6, "empInfoTer6"));
		test.add(new GetAListOfEmpInfoTerminalsDto(7, "empInfoTer7"));
		return test;
		//return this.screen1.getAll();
	}

	@POST
	@Path("getDetails/{empInfoTerCode}/{workLocationCD}")
	public GetInformationAboutTheSelectedDeviceDto getDetails(@PathParam("empInfoTerCode") int empInforTerCode) {
		return this.screen2.getDetails(empInforTerCode);
	}

}
