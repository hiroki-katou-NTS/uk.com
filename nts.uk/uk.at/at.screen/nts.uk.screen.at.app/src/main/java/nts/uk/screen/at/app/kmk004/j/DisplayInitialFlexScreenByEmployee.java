package nts.uk.screen.at.app.kmk004.j;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTimeRepository;
import nts.uk.screen.at.app.kmk004.g.GetFlexPredWorkTimeDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.J：社員別法定労働時間の登録（フレックス勤務）.メニュー別OCD.社員別法定労働時間の登録（フレックス勤務）の初期画面を表示する
 */
@Stateless
public class DisplayInitialFlexScreenByEmployee {

	@Inject
	private GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepo;

	public DisplayInitialFlexScreenByEmployeeDto displayInitialFlexScreenByEmployee() {

		DisplayInitialFlexScreenByEmployeeDto result = new DisplayInitialFlexScreenByEmployeeDto();
		// フレックス勤務所定労働時間取得
		// 1. ログイン会社ID
		this.getFlexPredWorkTimeRepo.find(AppContexts.user().companyId()).ifPresent(x -> {

			result.getFlexBasicSetting().setFlexPredWorkTime(GetFlexPredWorkTimeDto.fromDomain(x));
		});

		return result;
	}

}
