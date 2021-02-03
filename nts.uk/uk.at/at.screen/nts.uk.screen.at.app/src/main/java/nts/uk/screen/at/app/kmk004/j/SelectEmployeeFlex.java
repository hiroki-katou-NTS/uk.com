package nts.uk.screen.at.app.kmk004.j;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployee;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.J：社員別法定労働時間の登録（フレックス勤務）.メニュー別OCD.社員を選択する（フレックス勤務）
 */
@Stateless
public class SelectEmployeeFlex {

	@Inject
	private DisplayFlexBasicSettingByEmployee displayFlexBasicSettingByEmployee;

	@Inject
	private YearListByEmployee yearListByEmployee;

	public SelectEmployeeFlexDto selectEmployeeFlex(String sId) {
		SelectEmployeeFlexDto result = new SelectEmployeeFlexDto();

		// 社員別基本設定（フレックス勤務）を表示する
		result.getFlexBasicSetting().setFlexMonthActCalSet(
				this.displayFlexBasicSettingByEmployee.displayFlexBasicSettingByEmployee(sId).getFlexMonthActCalSet());

		// 社員別年度リストを表示する
		result.setYearList(this.yearListByEmployee.get(sId, LaborWorkTypeAttr.FLEX).stream().map(x -> x.year)
				.collect(Collectors.toList()));

		return result;
	}
}
