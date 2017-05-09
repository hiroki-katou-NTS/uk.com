/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.payment.refundsetting.refundpadding;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.RefundPaddingThreeSaveCommand;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.RefundPaddingThreeSaveCommandHandler;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.RefundPaddingFinder;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.dto.RefundPaddingThreeOut;

/**
 * The Class RefundPaddingWs.
 */
@Path("ctx/pr/report/payment/refundsetting/refundpadding")
@Produces("application/json")
public class RefundPaddingWs extends WebService {

	/** The find. */
	@Inject
	private RefundPaddingFinder finder;

	/** The save. */
	@Inject
	private RefundPaddingThreeSaveCommandHandler savethree;

	/**
	 * Find all.
	 *
	 * @param dto
	 *            the dto
	 * @return the list
	 */
	@POST
	@Path("printtype/three/find")
	public RefundPaddingThreeOut findPrintTypeThree() {
		return this.finder.findPrintTypeThree();
	}

	/**
	 * Save three.
	 *
	 * @param command the command
	 */
	@POST
	@Path("printtype/three/save")
	public void saveThree(RefundPaddingThreeSaveCommand command) {
		this.savethree.handle(command);
	}

}
