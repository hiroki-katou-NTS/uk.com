/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSetting;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ContactItemsSettingSaveCommandHandler.
 */

@Stateless
public class ContactItemsSettingSaveCommandHandler
	extends CommandHandler<ContactItemsSettingSaveCommand> {

	/** The repository. */
	@Inject
	private ContactItemsSettingRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<ContactItemsSettingSaveCommand> context) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code
		String companyCode = loginUserContext.companyCode();

		// get command
		ContactItemsSettingSaveCommand command = context.getCommand();

		// to domain
		ContactItemsSetting domain = command.toDomain(companyCode);

		// validate domain
		domain.validate();

		// save
		this.repository.save(domain);
	}

}
