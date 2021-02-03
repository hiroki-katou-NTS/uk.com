package nts.uk.screen.at.app.kmk004.o;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.kmk004.j.SelectEmployeeFlexDto;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployee;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.O：社員別法定労働時間の登録（変形労働）.メニュー別OCD.社員を選択する（変形労働）
 * 
 * @author tutt
 *
 */
@Stateless
public class SelectEmployeeDefor {

	@Inject
	private DisplayDeforBasicSettingByEmployee displayDeforBasicSettingByEmployee;

	@Inject
	private YearListByEmployee yearListByEmployee;

	public SelectEmployeeDeforDto selectEmployeeDefor(String sId) {

		SelectEmployeeDeforDto result = new SelectEmployeeDeforDto();

		// 社員別基本設定（変形労働）を表示する
		result.setDeforLaborMonthTimeShaDto(displayDeforBasicSettingByEmployee.displayDeforBasicSettingByEmployee(sId));

		// 社員別年度リストを表示する
		result.setYears(yearListByEmployee.get(sId, LaborWorkTypeAttr.DEFOR_LABOR));

		return result;

	}

}
