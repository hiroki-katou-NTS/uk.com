package nts.uk.screen.at.app.kmk004.i;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.MonthlyWorkingHoursByEmployment;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.I：雇用別法定労働時間の登録（フレックス勤務）.メニュー別OCD.雇用別年度（フレックス勤務）を選択する
 */
@Stateless
public class SelectFlexYearByEmployment {

	@Inject
	private MonthlyWorkingHoursByEmployment monthlyWorkingHoursByEmployment;

	public List<DisplayMonthlyWorkingDto> selectFlexYearByEmployment(String employmentCd, int year) {

		// 雇用別月単位労働時間を表示する

		return this.monthlyWorkingHoursByEmployment.get(employmentCd, LaborWorkTypeAttr.FLEX, year);
	}

}
