package nts.uk.screen.at.app.kmk004.m;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.M：職場別法定労働時間の登録（変形労働）.メニュー別OCD.職場別基本設定（変形労働）を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class DisplayDeforBasicSettingByWorkplace {

	@Inject
	private DeforLaborTimeWkpRepo timeWkpRepo;

	@Inject
	private WkpDeforLaborMonthActCalSetRepo monthActCalSetRepo;

	public DeforLaborMonthTimeWkpDto displayDeforBasicSettingByWorkplace(String wkpId) {

		String cid = AppContexts.user().companyId();

		DeforLaborMonthTimeWkpDto deforLaborMonthTimeWkpDto = new DeforLaborMonthTimeWkpDto();

		// 1. 職場別変形労働法定労働時間
		Optional<DeforLaborTimeWkp> optWkpDefor = timeWkpRepo.find(cid, wkpId);

		if (optWkpDefor.isPresent()) {
			deforLaborMonthTimeWkpDto.setDeforLaborTimeWkpDto(DeforLaborTimeWkpDto.fromDomain(optWkpDefor.get()));
		}

		// 2. 職場別変形労働集計設定
		Optional<WkpDeforLaborMonthActCalSet> optMonthActCalSet = monthActCalSetRepo.find(cid, wkpId);

		if (optMonthActCalSet.isPresent()) {
			deforLaborMonthTimeWkpDto.setWkpDeforLaborMonthActCalSetDto(
					WkpDeforLaborMonthActCalSetDto.fromDomain(optMonthActCalSet.get()));
		}

		return deforLaborMonthTimeWkpDto;
	}

}
