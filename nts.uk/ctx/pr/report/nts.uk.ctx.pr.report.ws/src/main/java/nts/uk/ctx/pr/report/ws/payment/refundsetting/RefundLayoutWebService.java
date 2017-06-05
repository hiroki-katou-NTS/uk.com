package nts.uk.ctx.pr.report.ws.payment.refundsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundlayout.command.InsertUpdateRefundLayoutCommand;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundlayout.command.InsertUpdateRefundLayoutCommandHandler;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundlayout.find.RefundLayoutDto;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundlayout.find.RefundLayoutFinder;

@Path("report/payment/refundsetting/refundlayout")
@Produces("application/json")
public class RefundLayoutWebService extends WebService {

	@Inject
	private RefundLayoutFinder refundLayoutFinder;

	@Inject
	private InsertUpdateRefundLayoutCommandHandler inserUpdateCommandHandler;

	@POST
	@Path("find/{printType}")
	public RefundLayoutDto getRefundLayout(@PathParam("printType") int printType) {
		return this.refundLayoutFinder.getRefundLayout(printType);
	}

	@POST
	@Path("insertUpdateData")
	public void insertUpdateData(InsertUpdateRefundLayoutCommand insertUpdateRefundLayout) {
		this.inserUpdateCommandHandler.handle(insertUpdateRefundLayout);
	}
}
