package nts.uk.smile.app.smilelinked.ocd.screenquery;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSettingRepository;

/**
 * Smile連携受入外部出設定を登録する
 *
 */
@Stateless
public class RegisterSmileCooperationAcceptanceSettingScreenQuery {

	// Smile連携受入設定
	@Inject
	private SmileCooperationAcceptanceSettingRepository smileCooperationAcceptanceSettingRepository;

	/**
	 * Function: 会社IDを指定してSM連携受入設定を取得する - Specify the company ID to get the SM linkage acceptance settings
	 * Param: 契約コード、会社ID -Contract code, company ID
	 * Return : List＜SM連携受入設定＞ - List <SM cooperation acceptance setting>
	 */
	private List<SmileCooperationAcceptanceSetting> get() {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		return smileCooperationAcceptanceSettingRepository.get(contractCode, companyId);
	}
}
