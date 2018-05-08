/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UserInfoUseMethodSave Command Handler
 * @author phund
 *
 */
@Stateless
public class UserInfoUseMethodSaveCommandHandler extends CommandHandler<UserInfoUseMethodSaveCommand> {

	@Inject
	private UserInfoUseMethodRepository repo;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UserInfoUseMethodSaveCommand> context) {
		UserInfoUseMethodSaveCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		if (repo.findByCompanyId(companyId).isEmpty()) {
			// save
			this.repo.create(command.toDomain());
		} else {
			this.repo.update(command.toDomain());
		}
	}

}
