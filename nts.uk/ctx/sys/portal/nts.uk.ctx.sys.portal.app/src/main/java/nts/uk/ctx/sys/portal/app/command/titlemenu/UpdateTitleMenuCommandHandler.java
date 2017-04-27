/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.titlemenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.titlemenu.Name;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenu;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenuCD;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateTitleMenuCommandHandler extends CommandHandler<UpdateTitleMenuCommand>{

	
	@Inject
	private TitleMenuRepository repository;

	@Override
	protected void handle(CommandHandlerContext<UpdateTitleMenuCommand> context) {
		String companyID = AppContexts.user().companyID();
		TitleMenu title = new TitleMenu (
				companyID, 
				new TitleMenuCD(context.getCommand().getTitleMenuCD()),
				context.getCommand().getLayoutID(),
				new Name (context.getCommand().getName()));
		repository.update(title);
		title.validate();
	}
}
