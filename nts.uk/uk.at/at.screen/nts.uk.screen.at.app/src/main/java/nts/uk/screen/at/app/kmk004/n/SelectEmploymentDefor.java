package nts.uk.screen.at.app.kmk004.n;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployment;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.M：職場別法定労働時間の登録（変形労働）.メニュー別OCD.職場を選択する（変形労働）
 * 
 * @author tutt
 *
 */
@Stateless
public class SelectEmploymentDefor {

	@Inject
	private DisplayDeforBasicSettingByEmployment displayDeforBasicSettingByEmployment;

	@Inject
	private YearListByEmployment yearListByEmployment;

	public SelectEmploymentDeforDto selectEmploymentDefor(String empCd) {

		SelectEmploymentDeforDto result = new SelectEmploymentDeforDto();

		// 1. 雇用別基本設定（変形労働）を表示する
		// 雇用コード = 選択中の雇用コード
		result.setDeforLaborMonthTimeEmpDto(
				displayDeforBasicSettingByEmployment.displayDeforBasicSettingByEmployment(empCd));

		//2. 雇用別年度リストを表示する
		//雇用コード = 選択中の雇用コード
		//勤務区分 = 1:変形労働
		result.setYears(yearListByEmployment.get(empCd, LaborWorkTypeAttr.DEFOR_LABOR));

		return result;

	}
}
