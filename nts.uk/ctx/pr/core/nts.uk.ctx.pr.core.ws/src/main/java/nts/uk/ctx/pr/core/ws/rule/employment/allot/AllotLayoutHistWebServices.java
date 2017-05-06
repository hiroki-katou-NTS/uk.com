package nts.uk.ctx.pr.core.ws.rule.employment.allot;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.rule.employment.allot.AllotLayoutHistFinder;
import nts.uk.ctx.pr.core.app.find.rule.employment.layout.LayoutHistoryDto;

@Path("pr/core/allotlayouthist")
@Produces("application/json")
public class AllotLayoutHistWebServices extends WebService {
	@Inject
	private AllotLayoutHistFinder find;

	@POST
	@Path("getall/{baseYm}")
	public List<LayoutHistoryDto> GetAllAllotLayoutHistory(@PathParam("baseYm") int baseYm) {
		return this.find.getSel1LayoutHistory(baseYm);
	}

	@POST
	@Path("findname/{stmtCode}")
	public String GetAllotLayoutName(@PathParam("stmtCode") String stmtCode) {
		return this.find.getAllotLayoutName(stmtCode);
	}
}
