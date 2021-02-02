package nts.uk.screen.at.app.kmk004.h;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.YearlyListByWorkplace;

/**
 * 
 * @author sonnlb
 * 
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.H：職場別法定労働時間の登録（フレックス勤務）.メニュー別OCD.職場を選択する（フレックス勤務）
 *
 */
@Stateless
public class SelectWorkPlaceFlex {

	@Inject
	private DisplayFlexBasicSettingByWorkPlace displayFlexBasicSettingByWokPlace;

	@Inject
	private YearlyListByWorkplace yearlyListByWorkplace;

	public SelectWorkPlaceFlexDto selectWorkPlaceFlex(String wkpId) {

		SelectWorkPlaceFlexDto result = new SelectWorkPlaceFlexDto();
		// 1 　職場ID＝選択中の職場ID
		result.setFlexBasicSetting(
				this.displayFlexBasicSettingByWokPlace.displayFlexBasicSettingByWokPlace(wkpId));
		// 2 　職場ID＝選択中の職場ID
		// 勤務区分＝2：フレックス勤務
		result.setYearList(this.yearlyListByWorkplace.get(wkpId, LaborWorkTypeAttr.FLEX).stream().map(x -> x.year)
				.collect(Collectors.toList()));

		return result;
	}
}
