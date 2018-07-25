package nts.uk.ctx.sys.portal.app.command.widget;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.enums.UseDivision;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;
import nts.uk.ctx.sys.portal.dom.mypage.setting.TopPagePartUseSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.WidgetDisplayItem;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Size;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
@Transactional
public class AddWidgetCommandHandler extends CommandHandler<AddWidgetCommand> {

	@Inject
	private OptionalWidgetRepository opWidgetRepository;

	@Inject
	private MyPageSettingRepository myPageSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<AddWidgetCommand> context) {
		String companyID = AppContexts.user().companyId();
		String topPagePartId = IdentifierUtil.randomUniqueId();
		AddWidgetCommand command = context.getCommand();
		if (opWidgetRepository.isExist(companyID, command.getTopPageCode(), TopPagePartType.OptionalWidget.value)) {
			throw new BusinessException("Msg_3");
		}
		List<WidgetDisplayItem> wItems = new ArrayList<WidgetDisplayItem>();
		command.getDisplayItemTypes().stream().forEach(x -> {
			wItems.add(WidgetDisplayItem.createFromJavaType(x.getDisplayItemType(), x.getNotUseAtr()));
		});

		opWidgetRepository.add(new OptionalWidget(companyID, topPagePartId,
				new TopPagePartCode(command.getTopPageCode()), new TopPagePartName(command.getTopPageName()),
				TopPagePartType.OptionalWidget, Size.createFromJavaType(command.getWidth(), command.getHeight()), wItems));

		myPageSettingRepository.addTopPagePartUseSetting(new TopPagePartUseSetting(companyID, topPagePartId,
				new TopPagePartCode(command.getTopPageCode()), new TopPagePartName(command.getTopPageName()),
				UseDivision.NotUse, TopPagePartType.OptionalWidget));
	}

}
