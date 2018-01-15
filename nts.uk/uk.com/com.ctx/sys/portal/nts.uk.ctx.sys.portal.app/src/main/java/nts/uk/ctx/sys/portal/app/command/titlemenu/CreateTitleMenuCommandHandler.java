package nts.uk.ctx.sys.portal.app.command.titlemenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.portal.dom.titlemenu.service.TitleMenuService;

@Stateless
@Transactional
/**
 * @author hieult
 */
public class CreateTitleMenuCommandHandler extends CommandHandler<CreateTitleMenuCommand> {
	
	@Inject
	private TitleMenuService titleMenuService;

	@Override
	protected void handle(CommandHandlerContext<CreateTitleMenuCommand> context) {
		CreateTitleMenuCommand command = context.getCommand();
		//Check input
		if (StringUtil.isNullOrEmpty(command.getTitleMenuCD(), true) || StringUtil.isNullOrEmpty(command.getName(), true)){
			throw new BusinessException("");
		}
		titleMenuService.createTitleMenu(command.toDomain());	
	}
}
