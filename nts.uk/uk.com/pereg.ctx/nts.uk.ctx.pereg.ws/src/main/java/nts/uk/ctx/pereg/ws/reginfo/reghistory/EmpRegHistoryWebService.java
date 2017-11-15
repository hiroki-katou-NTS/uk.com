package nts.uk.ctx.pereg.ws.reginfo.reghistory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pereg.app.find.reginfo.reghistory.EmpRegHistoryDto;
import nts.uk.ctx.pereg.app.find.reginfo.reghistory.EmpRegHistoryFinder;

/**
 * @author sonnlb
 *
 */
@Path("reginfo/empreghistory")
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
