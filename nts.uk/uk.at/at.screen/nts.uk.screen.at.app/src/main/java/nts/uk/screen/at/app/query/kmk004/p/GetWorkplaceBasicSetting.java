package nts.uk.screen.at.app.query.kmk004.p;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.P：基本設定（変形労働）.メニュー別OCD.職場別基本設定（変形労働）の初期画面を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetWorkplaceBasicSetting {

	@Inject
	private DeforLaborTimeWkpRepo timeWkpRepo;

	@Inject
	private WkpDeforLaborMonthActCalSetRepo monthActCalSetRepo;

	public DeforLaborWkpDto get(String wkpId) {

		String cid = AppContexts.user().companyId();
		
		DeforLaborWkpDto deforLaborWkpDto = new DeforLaborWkpDto();

		// 労働時間設定
		Optional<DeforLaborTimeWkp> optTimeWkp = timeWkpRepo.find(cid, wkpId);

		if (optTimeWkp.isPresent()) {
			deforLaborWkpDto.setDeforLaborTimeWkp(WorkingTimeSettingDto.fromDomain(optTimeWkp.get()));
		}

		// 変形労働時間勤務の法定内集計設定
		Optional<WkpDeforLaborMonthActCalSet> optMonthActCalSet = monthActCalSetRepo.find(cid, wkpId);

		if (optMonthActCalSet.isPresent()) {
			deforLaborWkpDto.setSetting(DeforWorkTimeAggrSetDto.fromDomain(optMonthActCalSet.get()));
		}

		return deforLaborWkpDto;
	}
}
