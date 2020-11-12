package nts.uk.ctx.sys.portal.app.command.toppage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNew;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNewRepository;
import nts.uk.ctx.sys.portal.dom.layout.WidgetSetting;
import nts.uk.ctx.sys.portal.dom.layout.WidgetType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * class SaveLayoutWidgetCommandHandler
 */
@Stateless
@Transactional
public class SaveLayoutWidgetCommandHandler extends CommandHandler< SaveLayoutCommand> {
	
	@Inject
	private LayoutNewRepository layoutNewRepository;

	@Override
	protected void handle(CommandHandlerContext<SaveLayoutCommand> context) {
		SaveLayoutCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<LayoutNew> findLayout = layoutNewRepository.getByCidAndCode(companyId, command.getTopPageCode(), command.getLayoutNo());
		if (findLayout.isPresent()) {
			LayoutNew layoutChoose = findLayout.get();
			// 「レイアウト.ウィジェット設定」が登録されている
			List<WidgetSetting> lstWidget = layoutChoose.getWidgetSettings();
			if (!lstWidget.isEmpty()) {
				Map<WidgetType, WidgetSetting> mapExistedWidget = command.getWidgetSettings().stream().collect(Collectors.toMap(item -> item.getWidgetType(), item -> item));
				List<WidgetSetting> lstWidgetNew = command.getWidgetSettings();
				for(WidgetSetting setting: lstWidget) {
					if (mapExistedWidget.get(setting.getWidgetType()) == null) {
						// レイアウトの「ウィジェット設定」を削除する
						layoutNewRepository.deleteWidget(companyId, command.getLayoutNo(), command.getTopPageCode(), BigDecimal.valueOf(setting.getWidgetType().value));
					} else {
						// レイアウトの「ウィジェット設定」を更新する
						layoutNewRepository.updateWidget(layoutChoose, setting);
						lstWidgetNew.removeIf(item -> item.getWidgetType() == setting.getWidgetType());
					}
				}
				if (!lstWidgetNew.isEmpty()) {
					for(WidgetSetting widget: lstWidgetNew) {
						// レイアウトの「ウィジェット設定」を登録する
						layoutNewRepository.insertWidget(layoutChoose, widget);
					}
				}
			}
		} else {
			// 新規モード
			LayoutNew layout = LayoutNew.createFromMemento(command);
			// ドメインモデル「レイアウト」を登録する
			layoutNewRepository.insert(layout);
			
			if (!command.getWidgetSettings().isEmpty()) {
				for(WidgetSetting widget: command.getWidgetSettings()) {
					// レイアウトの「ウィジェット設定」を登録する
					layoutNewRepository.insertWidget(layout, widget);
				}
			}
		}
		
	}

}
