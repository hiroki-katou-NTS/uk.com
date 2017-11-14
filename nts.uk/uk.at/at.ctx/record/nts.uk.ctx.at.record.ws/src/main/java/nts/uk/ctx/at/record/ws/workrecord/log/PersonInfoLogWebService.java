package nts.uk.ctx.at.record.ws.workrecord.log;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.find.log.PersonInfoLogFinder;
import nts.uk.ctx.at.record.dom.adapter.person.EmpBasicInfoImport;

@Path("at/record/personlog")
@Produces("application/json")
public class PersonInfoLogWebService {
	@Inject
	private PersonInfoLogFinder personInfoLogFinder;
	
	@POST
	@Path("getallbylistsid")
	public List<EmpBasicInfoImport> getAllErrMessageInfoByEmpID(List<String> listSid){
		return this.personInfoLogFinder.getListPersonByListSid(listSid);
	}

}
