package nts.uk.screen.at.app.kmk004.g;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.G：会社別法定労働時間の登録（フレックス勤務）.メニュー別OCD.会社別基本設定（フレックス勤務）を変更した時
 */
@Stateless
public class AfterChangeFlexCompanySetting {
	@Inject
	private DisplayFlexBasicSettingByCompany displayFlexBasicSettingByCompany;

	public DisplayFlexBasicSettingByCompanyDto afterChangeFlexCompanySetting() {
		// 会社別基本設定（フレックス勤務）を表示する
		return this.displayFlexBasicSettingByCompany.displayFlexBasicSettingByCompany();
	}
}
