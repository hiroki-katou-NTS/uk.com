package nts.uk.ctx.at.shared.app.command.statutory.worktime.app;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（New）.APP.労働時間と日数の設定の利用単位の設定を更新する
 */
public class UpdateUsageUnitSettingCommandHandler extends CommandHandler<UpdateUsageUnitSettingCommand> {

	@Inject
	private UsageUnitSettingRepository usageUnitSettingRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateUsageUnitSettingCommand> context) {

		UpdateUsageUnitSettingCommand cmd = context.getCommand();
		// update(ログイン会社ID)
		this.usageUnitSettingRepo.update(cmd.toDomain(AppContexts.user().companyId()));

	}

}
