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
 * class SaveLayoutFlowMenuCommandHandler
 */
@Stateless
@Transactional
public class SaveLayoutFlowMenuCommandHandler extends CommandHandler<SaveLayoutCommand> {

	@Inject
	private LayoutNewRepository layoutNewRepository;

	@Override
	protected void handle(CommandHandlerContext<SaveLayoutCommand> context) {
		SaveLayoutCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		command.setCid(companyId);
		// ドメインモデル「レイアウト」を登録する
		Optional<LayoutNew> findLayout = this.layoutNewRepository.getByCidAndCode(companyId, command.getTopPageCode(), command.getLayoutNo());
		LayoutNew newLayout = LayoutNew.createFromMemento(command);
		if (findLayout.isPresent()) {
			LayoutNew existedLayout = findLayout.get();
			newLayout.setWidgetSetting(existedLayout.getWidgetSettings());
			this.layoutNewRepository.update(newLayout);
		} else {
			this.layoutNewRepository.insert(newLayout);
		}
	}
}
