package nts.uk.ctx.pr.core.ws.rule.employment.allot;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.core.app.find.rule.employment.allot.AllotLayoutHistFinder;
import nts.uk.ctx.pr.core.app.find.rule.employment.layout.LayoutHistoryDto;

@Path("pr/core/allot")
@Produces("application/json")
public class AllotLayoutHistWebService {
	@Inject 
	private AllotLayoutHistFinder find;
	@POST
	@Path("findallotlayouthistory/{baseYm}")
	public List<LayoutHistoryDto> GetAllAllotLayoutHistory(@PathParam("baseYm") int baseYm){
		List<LayoutHistoryDto> test = this.find.getSel1LayoutHistory(baseYm);;
		return test;
	}
	
	@POST
	@Path("findcompanyallotlayoutname/{stmtCode}")
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String GetAllotLayoutName(@PathParam("stmtCode") String stmtCode){
		return this.find.getAllotLayoutName(stmtCode);
	}
}
