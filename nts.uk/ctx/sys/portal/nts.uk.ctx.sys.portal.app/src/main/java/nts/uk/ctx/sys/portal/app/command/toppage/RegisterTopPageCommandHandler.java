/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.command.toppage;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RegisterTopPageCommandHandler.
 */
@Stateless
public class RegisterTopPageCommandHandler extends CommandHandler<RegisterTopPageCommand> {

	/** The top page repository. */
	@Inject
	private TopPageRepository topPageRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RegisterTopPageCommand> context) {
		RegisterTopPageCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String topPageCode = command.getTopPageCode();
		Optional<TopPage> findTopPage = topPageRepository.findByCode(companyId, topPageCode);
		if (findTopPage.isPresent()) {
			throw new BusinessException("Msg_3");
		} else {
			// to Domain
			TopPage topPage = command.toDomain();
			// add
			topPageRepository.add(topPage);
		}
	}
}
