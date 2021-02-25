package nts.uk.screen.at.app.kmk004.m;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.kmk004.o.SelectEmployeeDeforDto;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployee;
import nts.uk.screen.at.app.query.kmk004.common.YearlyListByWorkplace;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.M：職場別法定労働時間の登録（変形労働）.メニュー別OCD.職場を選択する（変形労働）
 * 
 * @author tutt
 *
 */
@Stateless
public class SelectWorkplaceDefor {

	@Inject
	private DisplayDeforBasicSettingByWorkplace displayDeforBasicSettingByWorkplace;

	@Inject
	private YearlyListByWorkplace yearlyListByWorkplace;

	public SelectWorkplaceDeforDto selectWorkplaceDefor(String wkpId) {

		SelectWorkplaceDeforDto result = new SelectWorkplaceDeforDto();

		// 1. 職場別基本設定（変形労働）を表示する
		// 職場ID＝選択中の職場ID
		result.setDeforLaborMonthTimeWkpDto(
				displayDeforBasicSettingByWorkplace.displayDeforBasicSettingByWorkplace(wkpId));

		//2. 職場別年度リストを表示する
		//職場ID = 選択中の職場ID
		//勤務区分 = 1:変形労働
		result.setYears(yearlyListByWorkplace.get(wkpId, LaborWorkTypeAttr.DEFOR_LABOR));

		return result;

	}
}
