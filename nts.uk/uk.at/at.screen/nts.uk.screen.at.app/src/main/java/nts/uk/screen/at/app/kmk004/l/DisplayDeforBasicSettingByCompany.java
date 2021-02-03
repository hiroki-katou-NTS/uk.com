package nts.uk.screen.at.app.kmk004.l;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeComRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.L：会社別法定労働時間の登録（変形労働）.メニュー別OCD.会社別基本設定（変形労働）を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class DisplayDeforBasicSettingByCompany {

	@Inject
	private DeforLaborTimeComRepo timeComRepo;

	@Inject
	private ComDeforLaborMonthActCalSetRepo monthActCalSetRepo;

	public DeforLaborMonthTimeComDto displayDeforBasicSettingByCompany() {
		String companyId = AppContexts.user().companyId();

		DeforLaborMonthTimeComDto deforLaborMonthTimeComDto = new DeforLaborMonthTimeComDto();

		// 1. 会社別変形労働法定労働時間
		Optional<DeforLaborTimeCom> optComDefor = timeComRepo.find(companyId);

		if (optComDefor.isPresent()) {
			deforLaborMonthTimeComDto.setDeforLaborTimeComDto(DeforLaborTimeComDto.fromDomain(optComDefor.get()));
		}

		// 2. 会社別変形労働集計設定
		Optional<ComDeforLaborMonthActCalSet> optMonthActCalSet = monthActCalSetRepo.find(companyId);

		if (optMonthActCalSet.isPresent()) {
			deforLaborMonthTimeComDto.setComDeforLaborMonthActCalSetDto(ComDeforLaborMonthActCalSetDto.fromDomain(optMonthActCalSet.get()));
		}

		return deforLaborMonthTimeComDto;
	}
}
