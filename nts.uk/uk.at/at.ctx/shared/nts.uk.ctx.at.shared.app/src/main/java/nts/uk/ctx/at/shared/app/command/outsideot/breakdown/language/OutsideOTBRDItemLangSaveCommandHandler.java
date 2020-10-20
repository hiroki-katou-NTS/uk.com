/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.breakdown.language;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLang;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLangRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeLangBRDItemSaveCommandHandler.
 */
@Stateless
public class OutsideOTBRDItemLangSaveCommandHandler
		extends CommandHandler<OutsideOTBRDItemLangSaveCommand> {

	/** The repository. */
	@Inject
	private OutsideOTBRDItemLangRepository repository;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OutsideOTBRDItemLangSaveCommand> context) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get command
		OutsideOTBRDItemLangSaveCommand command = context.getCommand();

		
		// to domain
		List<OutsideOTBRDItemLang> domains= command.toListDomain(companyId);

		// save domain
		this.repository.saveAll(domains);
	}

}
