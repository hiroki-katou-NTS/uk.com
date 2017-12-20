package nts.uk.ctx.pereg.ws.reghistory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pereg.app.find.reghistory.EmpRegHistoryDto;
import nts.uk.ctx.pereg.app.find.reghistory.EmpRegHistoryFinder;

/**
 * @author sonnlb
 *
 */
@Path("ctx/pereg/empreghistory")
@Produces("application/json")
public class EmpRegHistoryWebService {
	@Inject
	private EmpRegHistoryFinder finder;

	@POST
	@Path("getLastRegHistory")
	public EmpRegHistoryDto getLastRegHistory() {
		return this.finder.getLastRegHistory();
	}
}
