package nts.uk.ctx.sys.portal.app.command.widget;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeleteWidgetCommandHandler extends CommandHandler<DeleteWidgetCommand> {

	@Inject
	private OptionalWidgetRepository opWidgetRepository;
	
	@Inject
	private MyPageSettingRepository myPageSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteWidgetCommand> context) {
		String companyID = AppContexts.user().companyId();
		DeleteWidgetCommand command = context.getCommand();
		List<Integer> ls = command.getDisplayItemTypes().stream().map(WidgetDisplayItemCommand::getDisplayItemType)
				.collect(Collectors.toList());
		this.opWidgetRepository.remove(companyID, command.getTopPagePartID(), ls);
		myPageSettingRepository.removeTopPagePartUseSettingById(companyID, command.getTopPagePartID());
		
	}

}
