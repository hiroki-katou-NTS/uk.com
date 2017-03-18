package nts.uk.ctx.pr.report.ws.payment.comparing;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.payment.comparing.find.ComparingFormDto;
import nts.uk.ctx.pr.report.app.payment.comparing.find.ComparingFormFinder;
import nts.uk.ctx.pr.report.app.payment.comparing.find.ComparingFormHeaderDto;
import nts.uk.ctx.pr.report.app.payment.comparing.find.ComparingFormHeaderFinder;

@Path("report/payment/comparing")
@Produces("application/json")
public class ComparingFormHeaderWebService extends WebService {

	@Inject
	private ComparingFormHeaderFinder ComparingFormHeaderFinder;
	@Inject
	private ComparingFormFinder comparingFormFinder;

	@POST
	@Path("find/formHeader")
	public List<ComparingFormHeaderDto> getListComparingFormHeader() {
		return ComparingFormHeaderFinder.getListComparingFormHeader();
	}
	
	
	@POST
	@Path("getDataTab")
	public ComparingFormDto getDataForTab() {
		return null;
	}

}
