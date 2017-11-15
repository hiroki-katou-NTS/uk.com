package nts.uk.ctx.at.record.ws.workrecord.log;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.log.CaseSpecExeContentFinder;
import nts.uk.ctx.at.record.app.find.log.dto.CaseSpecExeContentDto;

@Path("at/record/case")
@Produces("application/json")
public class CaseSpecExeContentWebService extends WebService {
	
	@Inject
	private CaseSpecExeContentFinder caseSpecExeContentFinder;
	
	/**
	 * get CaseSpecExeContent by ID
	 * @param caseSpecExeContentID
	 * @return
	 */
	@POST
	@Path("getcasebyid/{caseSpecExeContentID}")
	public CaseSpecExeContentDto getCaseSpecExeContentById(@PathParam("caseSpecExeContentID") String caseSpecExeContentID){
		return this.caseSpecExeContentFinder.getCaseSpecExeContentById(caseSpecExeContentID);
	}
	/**
	 * get all CaseSpecExeContent
	 * @param 
	 * @return
	 */
	@POST
	@Path("getallcase")
	public List<CaseSpecExeContentDto> getAllCaseSpecExeContent(){
		return this.caseSpecExeContentFinder.getAllCaseSpecExeContent();
	}

}
