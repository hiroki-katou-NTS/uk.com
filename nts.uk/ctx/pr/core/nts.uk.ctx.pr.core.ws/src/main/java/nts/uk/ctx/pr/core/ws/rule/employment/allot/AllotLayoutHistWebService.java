package nts.uk.ctx.pr.core.ws.rule.employment.allot;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.rule.employment.allot.AllotLayoutHistFinder;
import nts.uk.ctx.pr.core.app.find.rule.employment.layout.LayoutHistoryDto;

@Path("pr/core")
@Produces("application/json")
public class AllotLayoutHistWebService extends WebService {
	@Inject
	private AllotLayoutHistFinder find;

	@POST
	@Path("allot/findallotlayouthistory1")
	public List<LayoutHistoryDto> GetAllAllotLayoutHistory() {
		return this.find.getSel1LayoutHistory(201707);
	}

	@POST
	@Path("allot/findcompanyallotlayoutname")
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String GetAllotLayoutName(String stmtCode) {
		return this.find.getAllotLayoutName(stmtCode);
	}
}
