/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.titlemenu;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenu;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenuRepository;
import nts.uk.shr.com.context.AppContexts;


@Stateless
@Transactional
public class CreateTitleMenuCommandHandler extends CommandHandlerWithResult<CreateTitleMenuCommand, Boolean> {

	@Inject
	public TitleMenuRepository repository;

	@Override
	protected Boolean handle(CommandHandlerContext<CreateTitleMenuCommand> context) {
		String companyID = AppContexts.user().companyID();
		CreateTitleMenuCommand command = context.getCommand();
		
		Optional<TitleMenu> titleMenu = repository.findByCode(companyID, command.getTitleMenuCD());
		if (titleMenu.isPresent()) {
			return false;
		}
		repository.add(context.getCommand().toDomain());
		return true;
	}
}
