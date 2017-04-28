/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.titlemenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenu;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CreateTitleMenuCommandHandler extends CommandHandler<CreateTitleMenuCommand> {

	@Inject
	public TitleMenuRepository repository;

	@Override
	protected void handle(CommandHandlerContext<CreateTitleMenuCommand> context) {
		CreateTitleMenuCommand command = context.getCommand();
		String companyID = AppContexts.user().companyID();
		TitleMenu title = command.toDomain();
		this.repository.findByCode(companyID, command.getTitleMenuCD()).ifPresent(x -> {
			throw new BusinessException("ER005");
		});
		this.repository.add(title);
	}
}
