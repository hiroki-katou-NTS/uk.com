package nts.uk.screen.at.app.kmk004.o;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.O：社員別法定労働時間の登録（変形労働）.メニュー別OCD.社員別基本設定（変形労働）を表示する.社員別基本設定（変形労働）を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class DisplayDeforBasicSettingByEmployee {

	@Inject
	private DeforLaborTimeShaRepo timeShaRepo;
	
	@Inject
	private ShaDeforLaborMonthActCalSetRepo monthActCalSetRepo;
	
	public DeforLaborMonthTimeShaDto displayDeforBasicSettingByEmployee(String sId) {

		String companyId = AppContexts.user().companyId();
		
		DeforLaborMonthTimeShaDto deforLaborMonthTimeShaDto = new DeforLaborMonthTimeShaDto();
		
		// 1. 社員別変形労働法定労働時間
		Optional<DeforLaborTimeSha> optShaDefor = timeShaRepo.find(AppContexts.user().companyId(),
				sId);
		
		if (optShaDefor.isPresent()) {
			deforLaborMonthTimeShaDto.setDeforLaborTimeShaDto(DeforLaborTimeShaDto.fromDomain(optShaDefor.get()));
		}
		
		// 2. 社員別変形労働集計設定
	    Optional<ShaDeforLaborMonthActCalSet> optMonthActCalSet = monthActCalSetRepo.find(companyId, sId);
	    if (optMonthActCalSet.isPresent()) {
			deforLaborMonthTimeShaDto.setShaDeforLaborMonthActCalSetDto(ShaDeforLaborMonthActCalSetDto.fromDomain(optMonthActCalSet.get()));
		}

		return deforLaborMonthTimeShaDto;

	}

}
