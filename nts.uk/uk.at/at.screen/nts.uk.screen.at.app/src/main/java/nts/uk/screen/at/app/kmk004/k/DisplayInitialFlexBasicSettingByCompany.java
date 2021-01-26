package nts.uk.screen.at.app.kmk004.k;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.screen.at.app.kmk004.g.ComFlexMonthActCalSetDto;
import nts.uk.screen.at.app.kmk004.g.DisplayFlexBasicSettingByCompanyDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.K：基本設定（フレックス勤務）.メニュー別OCD.会社別基本設定（フレックス勤務）の初期画面を表示する
 */
@Stateless
public class DisplayInitialFlexBasicSettingByCompany {

	/*
	 * @Inject private GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepo;
	 */

	@Inject
	private ComFlexMonthActCalSetRepo comFlexMonthActCalSetRepo;

	public DisplayFlexBasicSettingByCompanyDto displayInitialFlexBasicSettingByCompany() {

		DisplayFlexBasicSettingByCompanyDto result = new DisplayFlexBasicSettingByCompanyDto();
		// 会社別フレックス勤務集計方法
		/*
		 * this.getFlexPredWorkTimeRepo.find(AppContexts.user().companyId()).ifPresent(x
		 * -> {
		 * 
		 * result.setFlexPredWorkTime(GetFlexPredWorkTimeDto.fromDomain(x));
		 * 
		 * });
		 */

		// 会社別フレックス勤務集計方法
		this.comFlexMonthActCalSetRepo.find(AppContexts.user().companyId()).ifPresent(x -> {

			result.setComFlexMonthActCalSet(ComFlexMonthActCalSetDto.fromDomain(x));

		});

		return result;

	}
}
