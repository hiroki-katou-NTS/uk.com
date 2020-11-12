package nts.uk.ctx.sys.portal.app.command.toppage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.app.find.toppage.WidgetSettingDto;
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
		// ドメインモデル「レイアウト」を登録する
		Optional<LayoutNew> findLayout = layoutNewRepository.getByCidAndCode(companyId, command.getTopPageCode(), command.getLayoutNo());
		if (findLayout.isPresent()) {
			List<WidgetSettingDto> lstDto= findLayout.get().getWidgetSettings().stream()
					.map(x -> WidgetSettingDto.builder()
						.widgetType(x.getWidgetType().value)
						.order(x.getOrder())
						.build()).collect(Collectors.toList());
			command.setWidgetSettings(lstDto);
			LayoutNew layout = LayoutNew.createFromMemento(command);
			layoutNewRepository.update(layout);
		} else {
			LayoutNew layout = LayoutNew.createFromMemento(command);
			layoutNewRepository.insert(layout);
		}
	}
}
