package nts.uk.screen.at.app.query.kdp.kdps01.a;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KDP_打刻.KDPS01_打刻入力(スマホ).A:打刻入力(スマホ).メニュー別OCD.打刻入力(スマホ)を起動する
 */
@Stateless
public class GetSettingSmartPhone {
	
	@Inject
	private GetSettingStampInputSmartPhone getSettingStampInputSmartPhone;

	public SettingSmartPhoneDto GetSetting() {

		return getSettingStampInputSmartPhone.GetSetting();
	}

}
