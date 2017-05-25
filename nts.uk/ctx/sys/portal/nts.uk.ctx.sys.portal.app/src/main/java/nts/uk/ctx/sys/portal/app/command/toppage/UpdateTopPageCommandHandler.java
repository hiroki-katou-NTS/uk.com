/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.command.toppage;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UpdateTopPageCommandHandler.
 */
@Stateless
public class UpdateTopPageCommandHandler extends CommandHandler<UpdateTopPageCommand>{
	
	/** The top page repository. */
	@Inject
	private TopPageRepository topPageRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateTopPageCommand> context) {
		UpdateTopPageCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<TopPage> findTopPage = topPageRepository.findByCode(companyId,command.getTopPageCode());
		//if exist top page -> update 
		if(findTopPage.isPresent())
		{
			TopPage topPage = command.toDomain();
			topPageRepository.update(topPage);
		}
	}

}
