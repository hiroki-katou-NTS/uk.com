package nts.uk.smile.app.smilelinked.ocd.screencommand;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSettingRepository;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;

/**
 * Smile連携受入外部出設定を登録する
 *
 */
@Stateless
public class RegisterSmileCooperationAcceptanceSettingScreenCommand {

	// Smile連携受入設定
	@Inject
	private SmileCooperationAcceptanceSettingRepository smileCooperationAcceptanceSettingRepository;
	
	@Inject
	private SmileLinkageOutputSettingRepository smileLinkageOutputSettingRepository;
	
	public void get() {
		/**
		 * Function: 会社IDを指定してSM連携受入設定を取得する - Specify the company ID to get the SM linkage acceptance settings
		 * Param: 契約コード、会社ID -Contract code, company ID
		 * Return : List＜SM連携受入設定＞ - List <SM cooperation acceptance setting>
		 */
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		List<SmileCooperationAcceptanceSetting> acceptanceSettings = smileCooperationAcceptanceSettingRepository.get(contractCode, companyId);
		if(acceptanceSettings.isEmpty()) {
			smileCooperationAcceptanceSettingRepository.insertAll(acceptanceSettings);
		} else {
			smileCooperationAcceptanceSettingRepository.updateAll(acceptanceSettings);
		}
		
		/**
		 * Object＜Smile連携出力設定＞	
		 * 
		 */
		SmileLinkageOutputSetting smileLinkageOutputSetting = smileLinkageOutputSettingRepository.get(contractCode, companyId);
		
		if(smileLinkageOutputSetting == null) {
			smileLinkageOutputSettingRepository.insert(smileLinkageOutputSetting);
		} else {
			smileLinkageOutputSettingRepository.update(smileLinkageOutputSetting);
		}
	}
}
