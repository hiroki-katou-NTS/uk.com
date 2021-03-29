package nts.uk.screen.at.app.kmk004.g;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.DisplayYearListByCompany;
import nts.uk.screen.at.app.query.kmk004.common.GetUsageUnitSetting;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.G：会社別法定労働時間の登録（フレックス勤務）.メニュー別OCD.会社別法定労働時間の登録（フレックス勤務）の初期画面を表示する
 */
@Stateless
public class DisplayInitialFlexScreenByCompany {

	@Inject
	private GetUsageUnitSetting getUsageUnit;

	@Inject
	private DisplayFlexBasicSettingByCompany displayFlexBasicSettingByCompany;

	@Inject
	private DisplayYearListByCompany displayYearListByCompany;

	public DisplayInitialFlexScreenByCompanyDto displayInitialScreen() {

		DisplayInitialFlexScreenByCompanyDto result = new DisplayInitialFlexScreenByCompanyDto();
		// 1. 利用単位の設定を取得する
		result.setUsageUnitSetting(this.getUsageUnit.get());
		// 2. 会社別基本設定（フレックス勤務）を表示する
		result.setFlexBasicSetting(this.displayFlexBasicSettingByCompany.displayFlexBasicSettingByCompany());
		// 3. 会社別年度リストを表示する
		result.setYearList(this.displayYearListByCompany.get(LaborWorkTypeAttr.FLEX.value).stream().map(x -> x.year)
				.collect(Collectors.toList()));

		return result;
	}
}
