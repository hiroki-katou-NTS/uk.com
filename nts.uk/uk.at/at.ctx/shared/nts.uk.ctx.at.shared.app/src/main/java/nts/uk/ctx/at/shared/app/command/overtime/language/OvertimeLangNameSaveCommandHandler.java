/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.overtime.language;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.OvertimeNameLang;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.OvertimeNameLangRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeLangNameSaveCommandHandler.
 */
@Stateless
public class OvertimeLangNameSaveCommandHandler
		extends CommandHandler<OvertimeLangNameSaveCommand> {

	/** The repository. */
	@Inject
	private OvertimeNameLangRepository repository;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OvertimeLangNameSaveCommand> context) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get command
		OvertimeLangNameSaveCommand command = context.getCommand();

		
		// to domain
		List<OvertimeNameLang> domains= command.toListDomain(companyId);

		// save domain
		this.repository.saveAll(domains);
	}

}
