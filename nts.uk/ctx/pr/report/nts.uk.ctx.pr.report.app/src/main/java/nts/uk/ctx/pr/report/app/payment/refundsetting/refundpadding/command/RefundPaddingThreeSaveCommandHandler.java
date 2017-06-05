/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPadding;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPaddingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ContactItemsSettingSaveCommandHandler.
 */

@Stateless
public class RefundPaddingThreeSaveCommandHandler
	extends CommandHandler<RefundPaddingThreeSaveCommand> {

	/** The repository. */
	@Inject
	private RefundPaddingRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<RefundPaddingThreeSaveCommand> context) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code
		String companyCode = loginUserContext.companyCode();

		// get command
		RefundPaddingThreeSaveCommand command = context.getCommand();

		// to domain
		RefundPadding domain = command.toDomain(companyCode);

		// validate domain
		domain.validate();

		// save
		this.repository.save(domain);
	}

}
