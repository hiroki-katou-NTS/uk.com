package nts.uk.screen.at.app.kmk004.m;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceList;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.M：職場別法定労働時間の登録（変形労働）.メニュー別OCD.職場別法定労働時間の登録（変形労働）の初期画面を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class DisplayInitialDeforScreenByWorkplace {

	@Inject
	private WorkplaceList workplaceList;

	@Inject
	private SelectWorkplaceDefor selectWorkplaceDefor;

	public DisplayInitialDeforScreenByWorkPlaceDto displayInitialDeforScreenByWorkplace() {

		DisplayInitialDeforScreenByWorkPlaceDto result = new DisplayInitialDeforScreenByWorkPlaceDto();

		// 1. 職場リストを表示する
		result.setWkpIds(workplaceList.get(LaborWorkTypeAttr.DEFOR_LABOR));

		// 2. 職場を選択する（変形労働）
		if (!result.getWkpIds().isEmpty()) {

			result.setSelectWorkplaceDeforDto(
					selectWorkplaceDefor.selectWorkplaceDefor(result.getWkpIds().get(0).workplaceId));
		}

		return result;
	}

}
