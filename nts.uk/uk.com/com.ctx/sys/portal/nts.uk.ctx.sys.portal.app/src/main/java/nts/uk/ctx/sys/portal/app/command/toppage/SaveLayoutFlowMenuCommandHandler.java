package nts.uk.ctx.sys.portal.app.command.toppage;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNew;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNewRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * class RegisterLayoutFlowMenuCommandHandler
 */
@Stateless
@Transactional
public class SaveLayoutFlowMenuCommandHandler extends CommandHandler< SaveLayoutFlowMenuCommand> {

	@Inject
	private LayoutNewRepository layoutNewRepository;

	@Override
	protected void handle(CommandHandlerContext<SaveLayoutFlowMenuCommand> context) {
		SaveLayoutFlowMenuCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「レイアウト」を登録する
		Optional<LayoutNew> findLayout = layoutNewRepository.getByCidAndCode(companyId, command.getTopPageCode(), command.getLayoutNo());
		if (findLayout.isPresent()) {
			command.setWidgetSettings(findLayout.get().getWidgetSettings());
			LayoutNew layout = LayoutNew.createFromMemento(command);
			layoutNewRepository.update(layout);
		} else {
			LayoutNew layout = LayoutNew.createFromMemento(command);
			layoutNewRepository.insert(layout);
		}
	}
}
