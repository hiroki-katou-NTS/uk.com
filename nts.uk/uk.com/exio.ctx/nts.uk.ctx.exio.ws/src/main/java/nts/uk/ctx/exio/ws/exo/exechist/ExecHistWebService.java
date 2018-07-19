package nts.uk.ctx.exio.ws.exo.exechist;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.exechist.ExecHistDto;
import nts.uk.ctx.exio.app.find.exo.exechist.ExecHistFinder;
import nts.uk.ctx.exio.app.find.exo.exechist.ExecHistResultDto;
import nts.uk.ctx.exio.app.find.exo.exechist.ExecHistSearchParam;

@Path("exio/exo/exechist")
@Produces("application/json")
public class ExecHistWebService {
	@Inject
	private ExecHistFinder execHistFinder;

	@POST
	@Path("getExecHist")
	public ExecHistResultDto getExecHist() {
		return execHistFinder.getExecHist();
	}

	@POST
	@Path("getExOutExecHistSearch")
	public List<ExecHistDto> getExOutExecHistSearch(ExecHistSearchParam param) {

		return execHistFinder.getExOutExecHistSearch(param);
	}
}
