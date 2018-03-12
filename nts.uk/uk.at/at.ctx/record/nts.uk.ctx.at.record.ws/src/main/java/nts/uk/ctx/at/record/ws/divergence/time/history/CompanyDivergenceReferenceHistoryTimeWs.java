package nts.uk.ctx.at.record.ws.divergence.time.history;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.divergence.time.history.CompanyDivergenceReferenceTimeHistoryCommandHandler;
import nts.uk.ctx.at.record.app.find.divergence.time.history.CompanyDivergenceReferenceTimeHistoryDto;
import nts.uk.ctx.at.record.app.find.divergence.time.history.CompanyDivergenceReferenceTimeHistoryFinder;

@Path("at/record/divergence/time/history/companyDivergenceRefTime")
@Produces("application/json")
public class CompanyDivergenceReferenceHistoryTimeWs extends WebService{
	
	@Inject
	private CompanyDivergenceReferenceTimeHistoryFinder historyFinder;
	
	@Inject
	private CompanyDivergenceReferenceTimeHistoryCommandHandler historyHandler;
	
	@POST
	@Path("history/findAllHistory")
	public List<CompanyDivergenceReferenceTimeHistoryDto> getAllCompanyDivergenceReferenceTimeHistory() {
	    return this.historyFinder.getAllHistories();
	}
}
