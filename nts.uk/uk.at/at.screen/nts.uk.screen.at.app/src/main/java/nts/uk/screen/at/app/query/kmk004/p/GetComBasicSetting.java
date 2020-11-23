package nts.uk.screen.at.app.query.kmk004.p;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeComRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.P：基本設定（変形労働）.メニュー別OCD.会社別基本設定（変形労働）の初期画面を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetComBasicSetting {

	@Inject
	private DeforLaborTimeComRepo timeComRepo;

	@Inject
	private ComDeforLaborMonthActCalSetRepo monthActCalSetRepo;

	public DeforLaborComDto get() {

		String companyId = AppContexts.user().companyId();
		
		DeforLaborComDto deforLaborComDto = new DeforLaborComDto();

		// 労働時間設定
		Optional<DeforLaborTimeCom> optTimeCom = timeComRepo.find(companyId);

		if (optTimeCom.isPresent()) {
			deforLaborComDto.setDeforLaborTimeComDto(WorkingTimeSettingDto.fromDomain(optTimeCom.get()));
		}

		// 会社別変形労働集計設定
		Optional<ComDeforLaborMonthActCalSet> optSetting = monthActCalSetRepo.find(companyId);

		if (optSetting.isPresent()) {
			deforLaborComDto.setSettingDto(DeforWorkTimeAggrSetDto.fromDomain(optSetting.get()));
		}

		return deforLaborComDto;
	}
}
