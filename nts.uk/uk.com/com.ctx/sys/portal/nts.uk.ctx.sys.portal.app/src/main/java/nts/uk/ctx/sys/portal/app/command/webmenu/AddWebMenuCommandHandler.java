package nts.uk.ctx.sys.portal.app.command.webmenu;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.webmenu.MenuBar;
import nts.uk.ctx.sys.portal.dom.webmenu.TitleBar;
import nts.uk.ctx.sys.portal.dom.webmenu.TreeMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddWebMenuCommandHandler extends CommandHandler<WebMenuCommandBase> {

	@Inject
	private WebMenuRepository webMenuRepository;

	@Override
	protected void handle(CommandHandlerContext<WebMenuCommandBase> context) {
		WebMenuCommandBase command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		Optional<WebMenu> webMenu = webMenuRepository.find(companyId, command.getWebMenuCode());
		if (webMenu.isPresent()) {
			throw new BusinessException("Msg_3");
		}

		WebMenu domain = command.toDomain(companyId);

		if (domain.isDefault()) {
			webMenuRepository.changeNotDefault(companyId, command.getWebMenuCode());
		}
		
		domain.validate();

		webMenuRepository.add(domain);

	}

}
