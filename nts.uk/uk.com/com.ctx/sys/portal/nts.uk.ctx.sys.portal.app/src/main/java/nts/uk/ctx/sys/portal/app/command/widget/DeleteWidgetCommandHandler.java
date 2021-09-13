package nts.uk.ctx.sys.portal.app.command.widget;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeleteWidgetCommandHandler extends CommandHandler<DeleteWidgetCommand> {
	
	@Inject
	private MyPageSettingRepository myPageSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteWidgetCommand> context) {
		String companyID = AppContexts.user().companyId();
		DeleteWidgetCommand command = context.getCommand();
		myPageSettingRepository.removeTopPagePartUseSettingById(companyID, command.getTopPagePartID());
	}

}
