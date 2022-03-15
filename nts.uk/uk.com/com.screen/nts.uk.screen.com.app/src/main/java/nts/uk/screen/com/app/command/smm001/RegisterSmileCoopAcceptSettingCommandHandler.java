package nts.uk.screen.com.app.command.smm001;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Smile連携受入外部出設定を登録する
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class RegisterSmileCoopAcceptSettingCommandHandler extends CommandHandler<RegisterSmileCoopAcceptSettingCommand> {

	// Smile連携受入設定
	@Inject
	private SmileCooperationAcceptanceSettingRepository smileCooperationAcceptanceSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<RegisterSmileCoopAcceptSettingCommand> context) {
		RegisterSmileCoopAcceptSettingCommand command = context.getCommand();
		// 会社IDを指定してSM連携受入設定を取得する
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		List<SmileCooperationAcceptanceSetting> acceptanceSettings = smileCooperationAcceptanceSettingRepository.get(contractCode, companyId);
		List<SmileCooperationAcceptanceSetting> newSmileCooperationAcceptanceSettings = command.convertScreenCommandToListSetting();
		if(acceptanceSettings.isEmpty()) {
			smileCooperationAcceptanceSettingRepository.insertAll(newSmileCooperationAcceptanceSettings);
		} else {
			smileCooperationAcceptanceSettingRepository.updateAll(newSmileCooperationAcceptanceSettings);
		}
	}
}
