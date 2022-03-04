package nts.uk.screen.com.app.smm.smm001.screencommand;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSettingRepository;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversionRepository;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Smile連携受入外部出設定を登録する
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class RegisterSmileCooperationAcceptanceSettingScreenCommandHandler extends CommandHandler<RegisterSmileCooperationAcceptanceSettingScreenCommand> {

	// Smile連携受入設定
	@Inject
	private SmileCooperationAcceptanceSettingRepository smileCooperationAcceptanceSettingRepository;
	
	@Inject
	private SmileLinkageOutputSettingRepository smileLinkageOutputSettingRepository;
	
	@Inject
	private LinkedPaymentConversionRepository linkedPaymentConversionRepository;

	@Override
	protected void handle(CommandHandlerContext<RegisterSmileCooperationAcceptanceSettingScreenCommand> context) {
		RegisterSmileCooperationAcceptanceSettingScreenCommand command = context.getCommand();
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
