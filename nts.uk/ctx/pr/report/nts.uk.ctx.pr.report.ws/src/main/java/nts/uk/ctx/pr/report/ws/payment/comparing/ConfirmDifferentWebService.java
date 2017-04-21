package nts.uk.ctx.pr.report.ws.payment.comparing;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.payment.comparing.confirm.find.DetailDifferentialFinder;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;

@Path("report/payment/comparing/confirm")
@Produces("application/json")
public class ConfirmDifferentWebService extends WebService {

	@Inject
	private DetailDifferentialFinder detailDiffFinder;

	@POST
	@Path("getdiff/{processingYMEarlier}/{processingYMLater}")
	public List<DetailDifferential> getDetailDifferential(@PathParam("processingYMEarlier") int processingYMEarlier,
			@PathParam("processingYMLater") int processingYMLater) {
		return this.detailDiffFinder.getDetailDifferential(processingYMEarlier, processingYMLater);
	}

}
