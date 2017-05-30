/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSetting;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingRepository;

@Stateless
public class ContactPersonalSettingSaveCommandHandler extends CommandHandler<ContactPersonalSettingSaveCommand> {

	@Inject
	private ContactPersonalSettingRepository repository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<ContactPersonalSettingSaveCommand> context) {
		// Get command.
		ContactPersonalSettingSaveCommand command = context.getCommand();

		command.getListContactPersonalSetting().forEach(item -> {
			ContactPersonalSetting setting = new ContactPersonalSetting(item);

			// Validate
			setting.validate();

			// Persist.
			repository.create(setting);
		});
	}
}
