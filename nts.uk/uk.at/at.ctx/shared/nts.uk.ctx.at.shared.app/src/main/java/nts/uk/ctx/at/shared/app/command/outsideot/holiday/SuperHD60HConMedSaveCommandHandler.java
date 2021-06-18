/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.holiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMedRepository;

/**
 * The Class SuperHD60HConMedSaveCommandHandler.
 */
@Stateless
public class SuperHD60HConMedSaveCommandHandler extends CommandHandler<SuperHD60HConMedSaveCommand>{

	/** The repository. */
	@Inject
	private SuperHD60HConMedRepository repository;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SuperHD60HConMedSaveCommand> context) {
		
		// get command
		SuperHD60HConMedSaveCommand command = context.getCommand();
		
		// to domain
		SuperHD60HConMed domain = command.domain();
		
		// save domain
		this.repository.save(domain);
	}

}
