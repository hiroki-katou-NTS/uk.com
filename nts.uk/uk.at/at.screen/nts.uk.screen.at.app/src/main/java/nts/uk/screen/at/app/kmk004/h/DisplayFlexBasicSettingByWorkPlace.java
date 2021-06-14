package nts.uk.screen.at.app.kmk004.h;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;
import nts.uk.screen.at.app.kmk004.g.ComFlexMonthActCalSetDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.H：職場別法定労働時間の登録（フレックス勤務）.メニュー別OCD.職場別基本設定（フレックス勤務）を表示する.職場別基本設定（フレックス勤務）を表示する
 */
@Stateless
public class DisplayFlexBasicSettingByWorkPlace {

	@Inject
	private WkpFlexMonthActCalSetRepo wkpFlexMonthActCalSetRepo;

	/*
	 * @Inject private GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepository;
	 */
	
	@Inject
	private ComFlexMonthActCalSetRepo comFlexRepo;

	public DisplayFlexBasicSettingByWorkPlaceDto displayFlexBasicSettingByWokPlace(String wkpId) {
		
		
		String companyId  = AppContexts.user().companyId();

		DisplayFlexBasicSettingByWorkPlaceDto result = new DisplayFlexBasicSettingByWorkPlaceDto();
		// 1. get(ログイン会社ID,職場ID)
		this.wkpFlexMonthActCalSetRepo.find(AppContexts.user().companyId(), wkpId).ifPresent(x -> {
			result.setFlexMonthActCalSet(WkpFlexMonthActCalSetDto.fromDomain(x));
		});

		// 2. get(ログイン会社ID,職場ID)
		/*
		 * this.getFlexPredWorkTimeRepository.find(companyId).ifPresent(x -> {
		 * 
		 * result.setFlexPredWorkTime(GetFlexPredWorkTimeDto.fromDomain(x)); });
		 */
		
		this.comFlexRepo.find(companyId).ifPresent(x -> {
			result.setComFlexMonthActCalSet(ComFlexMonthActCalSetDto.fromDomain(x));
		});

		return result;

	}
}
