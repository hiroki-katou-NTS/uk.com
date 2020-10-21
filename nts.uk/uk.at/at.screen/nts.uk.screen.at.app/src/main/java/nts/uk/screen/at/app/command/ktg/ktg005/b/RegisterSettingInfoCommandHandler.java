package nts.uk.screen.at.app.command.ktg.ktg005.b;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.B：設定ダイアログ.アルゴリズム.設定情報を登録する.設定情報を登録する
 */
@Stateless
public class RegisterSettingInfoCommandHandler extends CommandHandler<RegisterSettingInfoCommand> {
	
	@Inject
	private ApproveWidgetRepository widgetRepo;

	@Override
	protected void handle(CommandHandlerContext<RegisterSettingInfoCommand> context) {
		RegisterSettingInfoCommand cmd = context.getCommand();
		this.widgetRepo.updateAppStatus(cmd.toDomain(), AppContexts.user().companyId());
	}

}
