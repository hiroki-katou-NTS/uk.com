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
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.RefundPaddingOnceSaveCommand;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.RefundPaddingOnceSaveCommandHandler;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.RefundPaddingThreeSaveCommand;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.RefundPaddingThreeSaveCommandHandler;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.RefundPaddingTwoSaveCommand;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.RefundPaddingTwoSaveCommandHandler;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.RefundPaddingFinder;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.dto.RefundPaddingOnceOut;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.dto.RefundPaddingThreeOut;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.dto.RefundPaddingTwoOut;

/**
 * The Class RefundPaddingWs.
 */
@Path("ctx/pr/report/payment/refundsetting/refundpadding")
@Produces("application/json")
public class RefundPaddingWs extends WebService {

	/** The find. */
	@Inject
	private RefundPaddingFinder finder;

	/** The save three. */
	@Inject
	private RefundPaddingThreeSaveCommandHandler savethree;

	/** The save two. */
	@Inject
	private RefundPaddingTwoSaveCommandHandler savetwo;

	/** The save one. */
	@Inject
	private RefundPaddingOnceSaveCommandHandler saveone;

	/**
	 * Find print type three.
	 *
	 * @return the refund padding three out
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

	/**
	 * Find print type two.
	 *
	 * @return the refund padding three out
	 */
	@POST
	@Path("printtype/two/find")
	public RefundPaddingTwoOut findPrintTypeTwo() {
		return this.finder.findPrintTypeTwo();
	}

	/**
	 * Save two.
	 *
	 * @param command the command
	 */
	@POST
	@Path("printtype/two/save")
	public void saveTwo(RefundPaddingTwoSaveCommand command) {
		this.savetwo.handle(command);
	}

	/**
	 * Find print type once.
	 *
	 * @return the refund padding once out
	 */
	@POST
	@Path("printtype/once/find")
	public RefundPaddingOnceOut findPrintTypeOnce() {
		return this.finder.findPrintTypeOne();
	}

	/**
	 * Save once type.
	 *
	 * @param command the command
	 */
	@POST
	@Path("printtype/once/save")
	public void saveOnceType(RefundPaddingOnceSaveCommand command) {
		this.saveone.handle(command);
	}

}
