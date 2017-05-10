package nts.uk.ctx.sys.portal.app.command.titlemenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenuRepository;
import nts.uk.ctx.sys.portal.dom.titlemenu.service.TitleMenuService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
/**
 * @author hieult
 */
public class CreateTitleMenuCommandHandler extends CommandHandler<CreateTitleMenuCommand> {

	@Inject
	private TitleMenuRepository repository;
	
	@Inject
	private TitleMenuService titleMenuService;

	@Override
	protected void handle(CommandHandlerContext<CreateTitleMenuCommand> context) {
		String companyID = AppContexts.user().companyId();
		CreateTitleMenuCommand command = context.getCommand();
		//Check input
		if (StringUtil.isNullOrEmpty(command.getTitleMenuCD(), true) || StringUtil.isNullOrEmpty(command.getName(), true))
			throw new BusinessException("");
		
		if (!titleMenuService.isExist(companyID, command.getTitleMenuCD()))
			repository.add(context.getCommand().toDomain());
		else
			throw new BusinessException("Msg_03");
	}
}
