package nts.uk.ctx.hr.shared.ws.personalinfo.perinfo.perinfohr;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.AddPersonInfoHR;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.AddPersonInfoHRInput;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.DeletePersonInfoHR;

/**
 * 
 * @author chungnt
 *
 */

@Path("personInfoHR")
@Produces("application/json")
public class PersonInfoTestWebService extends WebService {
	
//	@Inject
//	private AddPersonInfoHR addPerson;
	
	@Inject 
	private DeletePersonInfoHR deletePerson;

//	@POST
//	@Path("addPersonInfo")
//	public void testAddPersonHR(List<AddPersonInfoHRInput> list) {
//		addPerson.add(list);
//	}
	
	@POST
	@Path("deletePersonInfo/{histId}")
	public void testDeletePersonHR(@PathParam("histId") String histId) {
		deletePerson.deletePersonalInfo(histId);
	}
}
