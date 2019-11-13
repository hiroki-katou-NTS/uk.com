package nts.uk.ctx.hr.shared.ws.personalinfo.medicalhistory;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.dom.personalinfo.humanresourceevaluation.HumanResourceEvaluation;
import nts.uk.ctx.hr.develop.dom.personalinfo.humanresourceevaluation.PersonnelAssessment;
import nts.uk.ctx.hr.develop.dom.personalinfo.stresscheck.StressCheck;
import nts.uk.ctx.hr.develop.dom.personalinfo.stresscheck.StressCheckManagement;

@Path("hrtest")
@Produces(MediaType.APPLICATION_JSON)
public class TestWs {

	@Inject
	private HumanResourceEvaluation hrEval;

	@Inject
	private StressCheckManagement stresscheck;

	@POST
	@Path("/testhr") // test service
	public List<PersonnelAssessment> testDomain(TestDto dto) {
		return hrEval.loadHRevaluation(dto.getSid(), dto.getBaseDate().addMonths(-1));
	}

	@POST
	@Path("/teststress") // test service
	public List<StressCheck> testStress(TestDto dto) {
		return stresscheck.loadStressCheck(dto.getSid(), dto.getBaseDate().addMonths(-1));
	}

}
