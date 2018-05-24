/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.EmployeeInfoContactAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.EnvPasswordAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.PersonContactAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.EmployeeInfoContactImport;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.PersonContactImport;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class MailNoticeSetSaveCommandHandler.
 */
@Stateless
@Transactional
public class MailNoticeSetSaveCommandHandler extends CommandHandler<MailNoticeSetSaveCommand> {

	/** The use contact setting repository. */
	@Inject
	private UseContactSettingRepository useContactSettingRepository;

	/** The employee info contact adapter. */
	@Inject
	private EmployeeInfoContactAdapter employeeInfoContactAdapter;

	/** The person contact adapter. */
	@Inject
	private PersonContactAdapter personContactAdapter;

	/** The password adapter. */
	@Inject
	private EnvPasswordAdapter passwordAdapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<MailNoticeSetSaveCommand> context) {

		String companyId = AppContexts.user().companyId();
		MailNoticeSetSaveCommand command = context.getCommand();

		if (command.getIsPasswordUpdate()) {

			// TODO Check password - Request List 383 (dung EnvPasswordAdapter)

			// Update password - Request List 384
			String newPassword = command.getNewPassword();
			if (!StringUtil.isNullOrEmpty(newPassword, true)) {
				this.passwordAdapter.updatePassword(newPassword);
			}
		}

		if (command.getIsContactUpdate()) {
			// Update import domain 社員連絡先 - Request List 380
			this.employeeInfoContactAdapter.register(new EmployeeInfoContactImport(command.getEmployeeInfoContact()));

			// Update import domain 個人連絡先 - Request List 381
			this.personContactAdapter.register(new PersonContactImport(command.getPersonContact()));

			// Insert/Update domain 連絡先使用設定
			command.getListUseContactSetting().forEach(item -> {
				Optional<UseContactSetting> useContact = this.useContactSettingRepository.find(companyId,
						item.getEmployeeID(), item.getSettingItem());
				if (useContact.isPresent()) {
					this.useContactSettingRepository.update(new UseContactSetting(item), companyId);
				} else {
					this.useContactSettingRepository.add(new UseContactSetting(item), companyId);
				}
			});
		}
	}
	
}
