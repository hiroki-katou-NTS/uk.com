package nts.uk.ctx.hr.shared.ws.personalinfo.perinfo.perinfohr;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation.GetDispatchedInformationApp;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation.InputDispatchedInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation.ReceiveInfoForBaseDateApp;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation.TemporaryDispatchInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.AddPersonInfoHR;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.AddPersonInfoHRInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.DeletePersonInfoHR;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.GetPersonInfoHR;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.GetPersonInfoHRInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.UpdatePersonInfoHR;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.UpdatePersonInfoHRInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HistoryGetDegreeApp;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HistoryGetDegreeDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HistoryGetDegreeInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HoldingQualificationApp;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HoldingQualificationDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory.HoldingQualificationInput;

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
	@Path("deletePersonInfo/{histId}")
	public void testDeletePersonHR(@PathParam("histId") String histId) {
		deletePerson.deletePersonalInfo(histId);
	}
	
	@POST
	@Path("get")
	public void testGetPersonHR(GetPersonInfoHRInput input) {
		getPerson.getPersonInfo(input);
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
	public List<HoldingQualificationDto> testHoldingQualification(HoldingQualificationInput input) {
		return holdingQualification.getHoldingQualification(input);
	}
	
	@POST
	@Path("getHistoryGetDegree")
	public List<HistoryGetDegreeDto> testHistoryGetDegree(HistoryGetDegreeInput input) {
		return historyGetDegreeApp.get(input);
	}
}