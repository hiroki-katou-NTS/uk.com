package nts.uk.ctx.hr.shared.ws.personalinfo.perinfo.perinfohr;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation.GetDispatchedInformationApp;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation.InputDispatchedInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation.ReceiveInfoForBaseDateApp;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation.TemporaryDispatchInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.AddPersonInfoHR;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.AddPersonInfoHRInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.DeletePersonInfoHR;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.DeletePersonInfoHRInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.GetPersonInfoHR;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.GetPersonInfoHRInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.GetPersonInfoHROutput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.UpdatePersonInfoHR;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.UpdatePersonInfoHRInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HistoryGetDegreeApp;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HistoryGetDegreeInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HistoryGetDegreeOutput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HoldingQualificationApp;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HoldingQualificationInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HoldingQualificationOutput;

/**
 * 
 * @author chungnt
 *
 */

@Path("personInfoHR")
@Produces("application/json")
public class PersonInfoTestWebService extends WebService {
	
	@Inject
	private AddPersonInfoHR addPerson;
	
	@Inject
	private UpdatePersonInfoHR updatePerson;
	
	@Inject 
	private DeletePersonInfoHR deletePerson;
	
	@Inject
	private GetPersonInfoHR getPerson;
	
	@Inject 
	private GetDispatchedInformationApp getDispatched;
	
	@Inject
	private ReceiveInfoForBaseDateApp receiveInfo;
	
	@Inject
	private HoldingQualificationApp holdingQualification;
	
	@Inject
	private HistoryGetDegreeApp historyGetDegreeApp;

	@POST
	@Path("addPersonInfo")
	public void testAddPersonHR(List<AddPersonInfoHRInput> input) {
		addPerson.add(input);
	}
	
	@POST
	@Path("updatePersonInfo")
	public void testUpdatePersonHR(List<UpdatePersonInfoHRInput> input) {
		updatePerson.update(input);
	}
	
	@POST
	@Path("deletePersonInfo")
	public void testDeletePersonHR(List<DeletePersonInfoHRInput> input) {
		deletePerson.deletePersonalInfo(input);
	}
	
	@POST
	@Path("get")
	public List<GetPersonInfoHROutput> testGetPersonHR(GetPersonInfoHRInput input) {
		return getPerson.getPersonInfo(input);
	}
	
	@POST
	@Path("getDispatchedInformation")
	public List<TemporaryDispatchInformation> testGetDispatchedInformation(InputDispatchedInformation input) {
		return getDispatched.get(input);
	}

	@POST
	@Path("getReceiveInfoForBaseDate")
	public List<TemporaryDispatchInformation> testReceiveInfoForBaseDate(InputDispatchedInformation input) {
		return receiveInfo.get(input);
	}

	@POST
	@Path("getHoldingQualification")
	public List<HoldingQualificationOutput> testHoldingQualification(HoldingQualificationInput input) {
		return holdingQualification.getHoldingQualification(input);
	}
	
	@POST
	@Path("getHistoryGetDegree")
	public List<HistoryGetDegreeOutput> testHistoryGetDegree(HistoryGetDegreeInput input) {
		return historyGetDegreeApp.get(input);
	}
}