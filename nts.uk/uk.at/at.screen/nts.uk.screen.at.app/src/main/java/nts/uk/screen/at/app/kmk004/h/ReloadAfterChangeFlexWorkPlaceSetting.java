package nts.uk.screen.at.app.kmk004.h;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceList;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.H：職場別法定労働時間の登録（フレックス勤務）.メニュー別OCD.職場別基本設定（フレックス勤務）を作成・変更・削除した時
 */
@Stateless
public class ReloadAfterChangeFlexWorkPlaceSetting {

	@Inject
	private DisplayFlexBasicSettingByWorkPlace displayFlexBasicSettingByWorkPlace;

	@Inject
	private WorkplaceList workplaceList;

	public ReloadAfterChangeFlexWorkPlaceSettingDto reloadAfterChangeFlexWorkPlaceSetting(String wkpId) {
		ReloadAfterChangeFlexWorkPlaceSettingDto result = new ReloadAfterChangeFlexWorkPlaceSettingDto();

		// 職場別基本設定（フレックス勤務）を表示する
//		result.setDisplayFlexBasicSettingByWokPlace(
//				this.displayFlexBasicSettingByWorkPlace.displayFlexBasicSettingByWokPlace(wkpId));
//		// 職場リストを表示する
//		result.setWkpIds(this.workplaceList.get(LaborWorkTypeAttr.FLEX));

		return result;
	}
}
