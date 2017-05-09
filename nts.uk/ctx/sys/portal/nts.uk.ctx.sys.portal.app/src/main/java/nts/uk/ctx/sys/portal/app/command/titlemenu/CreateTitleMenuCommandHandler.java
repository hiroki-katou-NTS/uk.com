/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.titlemenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenuRepository;
import nts.uk.ctx.sys.portal.dom.titlemenu.service.TitleMenuService;
import nts.uk.shr.com.context.AppContexts;


@Stateless
@Transactional
public class CreateTitleMenuCommandHandler extends CommandHandlerWithResult<CreateTitleMenuCommand, Boolean> {

	@Inject
	private TitleMenuRepository repository;
	
	@Inject
	private TitleMenuService titleMenuService;

	@Override
	protected Boolean handle(CommandHandlerContext<CreateTitleMenuCommand> context) {
		String companyID = AppContexts.user().companyID();
		CreateTitleMenuCommand command = context.getCommand();
		
		if (!titleMenuService.isExist(companyID, command.getTitleMenuCD())) {
			repository.add(context.getCommand().toDomain());
			return true;
		}
		else
			return false;
	}
}
