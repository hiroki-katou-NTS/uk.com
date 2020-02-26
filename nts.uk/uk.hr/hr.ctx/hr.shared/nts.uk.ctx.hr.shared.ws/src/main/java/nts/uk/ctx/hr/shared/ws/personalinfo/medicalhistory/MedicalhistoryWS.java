package nts.uk.ctx.hr.shared.ws.personalinfo.medicalhistory;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryItemResults;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryManagement;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryServices;

@Path("medicalhistory")
@Produces(MediaType.APPLICATION_JSON)
public class MedicalhistoryWS {

	@Inject
	private MedicalhistoryServices medicalhisServices;

	@POST
	@Path("/get1") // test service
	public MedicalhistoryManagement getList(ParamTest param) {

		List<String> listSid = param.listSid;
		GeneralDate baseDate = GeneralDate.legacyDate(param.baseDate.date()).addMonths(-1);

		return medicalhisServices.loadMedicalhistoryItem(listSid, baseDate);
	}
	
	@POST
	@Path("/get2") // test service
	public List<MedicalhistoryItemResults> getSingle(ParamTest param) {

		return medicalhisServices.getMedicalhistoryItem(param.sid, param.baseDate);
	}
}
	/**
	 * "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
	 * "8f9edce4-e135-4a1e-8dca-ad96abe405d6",
	 * "9787c06b-3c71-4508-8e06-c70ad41f042a",
	 * "62785783-4213-4a05-942b-c32a5ffc1d63",
	 * "4859993b-8065-4789-90d6-735e3b65626b",
	 * "aeaa869d-fe62-4eb2-ac03-2dde53322cb5";
	 */
