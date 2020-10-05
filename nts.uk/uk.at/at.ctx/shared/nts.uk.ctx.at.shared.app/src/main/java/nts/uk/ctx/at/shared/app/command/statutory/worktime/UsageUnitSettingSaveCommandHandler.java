/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UsageUnitSettingSaveCommandHandler.
 */
@Stateless
public class UsageUnitSettingSaveCommandHandler
		extends CommandHandler<UsageUnitSettingSaveCommand> {

	/** The repository. */
	@Inject
	private UsageUnitSettingRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UsageUnitSettingSaveCommand> context) {

		// get company id
		String companyId = AppContexts.user().companyId();

		// get command
		UsageUnitSettingSaveCommand command = context.getCommand();

		// to domain
		UsageUnitSetting domain = command.toDomain(companyId);

		// Save
		Optional<UsageUnitSetting> optional = this.repository.findByCompany(companyId);
		if (optional.isPresent()) {
			this.repository.update(domain);
		} else {
			this.repository.create(domain);
		}
	}

}
