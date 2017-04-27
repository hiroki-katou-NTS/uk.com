package nts.uk.ctx.sys.portal.app.toppage.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;

/**
 * The Class UpdateTopPageCommandHandler.
 */
@Stateless
public class UpdateTopPageCommandHandler extends CommandHandler<UpdateTopPageCommand>{
	
	/** The top page repository. */
	@Inject
	TopPageRepository topPageRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateTopPageCommand> context) {
		UpdateTopPageCommand command = context.getCommand();
		Optional<TopPage> findTopPage = topPageRepository.findByCode(command.getCompanyId(),command.getTopPageCode());
		//if exist top page -> update 
		if(findTopPage.isPresent())
		{
			TopPage topPage = command.toDomain();
			topPageRepository.update(topPage);
		}
	}

}
