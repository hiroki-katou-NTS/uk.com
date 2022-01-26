package nts.uk.screen.com.app.smm.smm001.screencommand;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSettingRepository;
import nts.uk.smile.dom.smilelinked.cooperationoutput.EmploymentAndLinkedMonthSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversion;
import nts.uk.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversionRepository;
import nts.uk.smile.dom.smilelinked.cooperationoutput.PaymentCategory;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;

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
		/**
		 * Function: 会社IDを指定してSM連携受入設定を取得する - Specify the company ID to get the SM linkage acceptance settings
		 * Param: 契約コード、会社ID -Contract code, company ID
		 * Return : List＜SM連携受入設定＞ - List <SM cooperation acceptance setting>
		 */
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		List<SmileCooperationAcceptanceSetting> acceptanceSettings = smileCooperationAcceptanceSettingRepository.get(contractCode, companyId);
		List<SmileCooperationAcceptanceSetting> newSmileCooperationAcceptanceSettings = command.convertScreenCommandToListSetting();
		if(acceptanceSettings.isEmpty()) {
			smileCooperationAcceptanceSettingRepository.insertAll(newSmileCooperationAcceptanceSettings);
		} else {
			smileCooperationAcceptanceSettingRepository.updateAll(newSmileCooperationAcceptanceSettings);
		}
//		
//		/**
//		 * Object＜Smile連携出力設定＞	
//		 * 
//		 */
//		SmileLinkageOutputSetting smileLinkageOutputSetting = smileLinkageOutputSettingRepository.get(contractCode, companyId);
//		
//		if(smileLinkageOutputSetting == null) {
//			smileLinkageOutputSettingRepository.insert(smileLinkageOutputSetting);
//		} else {
//			smileLinkageOutputSettingRepository.update(smileLinkageOutputSetting);
//		}
//		
//		/**
//		 * 支払コードを指定して連動支払変換を取得する
//		 * 契約コード、会社ID、支払コード
//		 */
//		PaymentCategory paymentCategory = EnumAdaptor.valueOf(command.getPaymentCode(), PaymentCategory.class);
//		List<EmploymentAndLinkedMonthSetting> employmentAndLinkedMonthSettings = linkedPaymentConversionRepository.getByPaymentCode(contractCode, companyId, paymentCategory);
//	
//		LinkedPaymentConversion domain = new LinkedPaymentConversion(
//				paymentCategory,
//				employmentAndLinkedMonthSettings
//		);
//		if (!employmentAndLinkedMonthSettings.isEmpty()) {
//			linkedPaymentConversionRepository.update(domain);
//		} else {
//			linkedPaymentConversionRepository.insert(domain);
//		}
	}
}
