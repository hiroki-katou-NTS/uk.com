package nts.uk.ctx.exio.ws.exo.exechist;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.exechist.CondSetAndExecHistDto;
import nts.uk.ctx.exio.app.find.exo.exechist.ExecHistFinder;

@Path("exio/exo/exechist")
@Produces("application/json")
public class ExecHistWebService {
	@Inject
	private ExecHistFinder execHistFinder;

	@POST
	@Path("getExOutCondSetAndExecHist")
	public CondSetAndExecHistDto getExOutCondSetAndExecHist() {
		return execHistFinder.getExOutCondSetAndExecHist();
	}
}
