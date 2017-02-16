/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.service.PensionAvgearnService;

/**
 * The Class UpdatePensionAvgearnCommandHandler.
 */
@Stateless
public class UpdatePensionAvgearnCommandHandler extends CommandHandler<UpdatePensionAvgearnCommand> {

	/** The pension avgearn service. */
	@Inject
	private PensionAvgearnService pensionAvgearnService;

	/** The pension avgearn repository. */
	@Inject
	private PensionAvgearnRepository pensionAvgearnRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdatePensionAvgearnCommand> context) {
		// Get command.
		UpdatePensionAvgearnCommand command = context.getCommand();

		command.getListPensionAvgearn().forEach(dto -> {
			// Get the pensionAvgearn.
			PensionAvgearn pensionAvgearn = pensionAvgearnRepository.find(dto.getHistoryId(), dto.getLevelCode()).get();

			// Transfer data
			PensionAvgearn updatedPensionAvgearn = dto.toDomain(pensionAvgearn.getHistoryId(),
					pensionAvgearn.getLevelCode());

			// Validate
			pensionAvgearnService.validateRequiredItem(updatedPensionAvgearn);
			// Update to db.
			pensionAvgearnRepository.update(updatedPensionAvgearn);
		});
	}
}
