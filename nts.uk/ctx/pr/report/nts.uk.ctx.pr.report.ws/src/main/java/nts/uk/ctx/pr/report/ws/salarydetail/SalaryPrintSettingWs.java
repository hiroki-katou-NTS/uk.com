package nts.uk.ctx.pr.report.ws.salarydetail;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/salarydetail/printsetting")
@Produces("application/json")
public class SalaryPrintSettingWs {

	@POST
	@Path("find")
	public void find() {
		// TODO create finder
	}

	@POST
	@Path("save")
	public void save() {
		// TODO create command
	}
}
