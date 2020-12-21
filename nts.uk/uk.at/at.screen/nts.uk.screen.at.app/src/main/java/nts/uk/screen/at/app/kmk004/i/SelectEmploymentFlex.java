package nts.uk.screen.at.app.kmk004.i;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployment;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.I：雇用別法定労働時間の登録（フレックス勤務）.メニュー別OCD.雇用を選択する（フレックス勤務）
 */
@Stateless
public class SelectEmploymentFlex {

	@Inject
	private DisplayFlexBasicSettingByEmployment displayFlexBasicSettingByEmployment;

	@Inject
	private YearListByEmployment yearListByEmployment;

	public SelectEmploymentFlexDto selectEmploymentFlex(String employmentCd) {
		SelectEmploymentFlexDto result = new SelectEmploymentFlexDto();
		// 1. 雇用別基本設定（フレックス勤務）を表示する
		result.setFlexBasicSetting(
				this.displayFlexBasicSettingByEmployment.displayFlexBasicSettingByEmployment(employmentCd));
		// 2. 雇用別年度リストを表示する
		result.setYearList(this.yearListByEmployment.get(employmentCd, LaborWorkTypeAttr.FLEX).stream().map(x -> x.year)
				.collect(Collectors.toList()));

		return result;
	}
}
