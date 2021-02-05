package nts.uk.screen.at.app.kmk004.g;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.G：会社別法定労働時間の登録（フレックス勤務）.メニュー別OCD.会社別基本設定（フレックス勤務）を表示する.会社別基本設定（フレックス勤務）を表示する
 */
@Stateless
public class DisplayFlexBasicSettingByCompany {

	/*
	 * @Inject private GetFlexPredWorkTimeRepository getFlexRepo;
	 */

	@Inject
	private ComFlexMonthActCalSetRepo comFlexRepo;

	// 会社別基本設定（フレックス勤務）を表示する
	public DisplayFlexBasicSettingByCompanyDto displayFlexBasicSettingByCompany() {

		DisplayFlexBasicSettingByCompanyDto result = new DisplayFlexBasicSettingByCompanyDto();

		String comId = AppContexts.user().companyId();

		/*
		 * // 1.get(ログイン会社ID) this.getFlexRepo.find(comId).ifPresent(x -> {
		 * result.setFlexPredWorkTime(GetFlexPredWorkTimeDto.fromDomain(x)); });
		 */

		// 2.get(ログイン会社ID)

		this.comFlexRepo.find(comId).ifPresent(x -> {
			result.setComFlexMonthActCalSet(ComFlexMonthActCalSetDto.fromDomain(x));
		});

		return result;
	}
}
