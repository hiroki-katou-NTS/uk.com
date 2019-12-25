package nts.uk.ctx.pr.shared.ws.wageprovision.processdatecls.algorithm;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.shared.app.find.wageprovision.processdatecls.algorithm.AlgorithmFinder;
import nts.uk.ctx.pr.shared.dom.wageprovision.processdatecls.algorithm.ClosureDateImport;

@Path("shared/algorithm")
@Produces("application/json")
public class AlgorithmWebServices {
	@Inject
	private AlgorithmFinder finder;

	@POST
	@Path("getPersonInfo/{companyId}")
	public List<ClosureDateImport> getPersonInfo(@PathParam("companyId") String companyId) {
		return this.finder.GetClosingSalaryEmploymentList(companyId);
	}
}
