package nts.uk.ctx.exio.ws.exo.exechist;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import nts.uk.ctx.exio.app.find.exo.exechist.CondSetAndCtgDto;
import nts.uk.ctx.exio.app.find.exo.exechist.ExecHistFinder;

public class ExecHistWebService {
	@Inject
	private ExecHistFinder execHistFinder;

	@POST
	@Path("getExOutCondSetList")
	public CondSetAndCtgDto getExOutCondSetList() {
		return execHistFinder.getExOutCondSetList();
	}
}
