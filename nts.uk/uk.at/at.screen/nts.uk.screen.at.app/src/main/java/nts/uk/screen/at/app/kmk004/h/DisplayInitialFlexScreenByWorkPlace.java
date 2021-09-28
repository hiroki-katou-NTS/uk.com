package nts.uk.screen.at.app.kmk004.h;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceList;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.H：職場別法定労働時間の登録（フレックス勤務）.メニュー別OCD.職場別法定労働時間の登録（フレックス勤務）の初期画面を表示する
 */
@Stateless
public class DisplayInitialFlexScreenByWorkPlace {

	/*
	 * @Inject private GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepo;
	 */

	@Inject
	private WorkplaceList workplaceList;

	@Inject
	private SelectWorkPlaceFlex selectWorkPlaceFlex;

	public DisplayInitialFlexScreenByWorkPlaceDto displayInitialFlexScreenByWorkPlace(String wkpId) {

		DisplayInitialFlexScreenByWorkPlaceDto result = new DisplayInitialFlexScreenByWorkPlaceDto();
		String comId = AppContexts.user().companyId();
		// 1.ログイン会社ID

		/*
		 * this.getFlexPredWorkTimeRepo.find(comId).ifPresent(x -> {
		 * result.setFlexPredWorkTime(GetFlexPredWorkTimeDto.fromDomain(x)); });
		 */
		// 2.職場リストを表示する
		result.setAlreadySettings(this.workplaceList.get(LaborWorkTypeAttr.FLEX).stream().map(x -> x.workplaceId)
				.collect(Collectors.toList()));

		// 3.職場を選択する（フレックス勤務）

		SelectWorkPlaceFlexDto selectWorkPlaceFlexDto = this.selectWorkPlaceFlex.selectWorkPlaceFlex(wkpId);
		result.setFlexBasicSetting(selectWorkPlaceFlexDto.getFlexBasicSetting());
		result.setYearList(selectWorkPlaceFlexDto.getYearList());

		return result;
	}
}
