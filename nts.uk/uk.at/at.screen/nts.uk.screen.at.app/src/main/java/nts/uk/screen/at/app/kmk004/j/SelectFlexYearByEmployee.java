package nts.uk.screen.at.app.kmk004.j;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.MonthlyWorkingHoursByEmployee;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.J：社員別法定労働時間の登録（フレックス勤務）.メニュー別OCD.社員別年度（フレックス勤務）を選択する
 */
@Stateless
public class SelectFlexYearByEmployee {

	@Inject
	private MonthlyWorkingHoursByEmployee monthlyWorkingHoursByEmployee;

	public List<DisplayMonthlyWorkingDto> selectFlexYearByEmployee(String sId, int year) {

		// 社員別月単位労働時間を表示する
		// input：
		// 社員ID＝選択中の社員ID
		// 勤務区分＝2：フレックス勤務
		// 年度＝選択中の年度

		return this.monthlyWorkingHoursByEmployee.get(sId, LaborWorkTypeAttr.FLEX, year);
	}
}
