package nts.uk.screen.at.app.kmk004.l;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.DisplayYearListByCompany;
import nts.uk.screen.at.app.query.kmk004.common.GetUsageUnitSetting;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.L：会社別法定労働時間の登録（変形労働）.メニュー別OCD.会社別法定労働時間の登録（変形労働）の初期画面を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class DisplayInitialDeforScreenByCom {

	@Inject
	private GetUsageUnitSetting getUsageUnit;

	@Inject
	private DisplayDeforBasicSettingByCompany displayFlexBasicSettingByCompany;

	@Inject
	private DisplayYearListByCompany displayYearListByCompany;

	public DisplayInitialDeforScreenByCompanyDto displayInitialDeforScreenByCom() {

		DisplayInitialDeforScreenByCompanyDto result = new DisplayInitialDeforScreenByCompanyDto();

		// 1. 利用単位の設定を取得する
		result.setUsageUnitSetting(getUsageUnit.get());

		// 2. 会社別基本設定（変形労働）を表示する
		result.setDeforLaborMonthTimeComDto(displayFlexBasicSettingByCompany.displayDeforBasicSettingByCompany());

		// 3. 会社別年度リストを表示する
		result.setYears(displayYearListByCompany.get(LaborWorkTypeAttr.DEFOR_LABOR.value));

		return result;
	}

}
