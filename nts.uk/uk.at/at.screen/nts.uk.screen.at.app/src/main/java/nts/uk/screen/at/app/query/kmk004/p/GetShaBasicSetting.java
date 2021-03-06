package nts.uk.screen.at.app.query.kmk004.p;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.P：基本設定（変形労働）.メニュー別OCD.社員別基本設定（変形労働）の初期画面を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetShaBasicSetting {

	@Inject
	private DeforLaborTimeShaRepo timeShaRepo;

	@Inject
	private ShaDeforLaborMonthActCalSetRepo monthActCalSetRepo;

	public DeforLaborShaDto get(String empId) {
		
		String companyId = AppContexts.user().companyId();

		DeforLaborShaDto deforLaborShaDto = new DeforLaborShaDto();

		// 労働時間設定
		Optional<DeforLaborTimeSha> optTimeSha = timeShaRepo.find(companyId, empId);

		if (optTimeSha.isPresent()) {
			deforLaborShaDto.setDeforLaborTimeComDto(WorkingTimeSettingDto.fromDomain(optTimeSha.get()));
		}

		// 変形労働時間勤務の法定内集計設定
		Optional<ShaDeforLaborMonthActCalSet> optMonthActCalSet = monthActCalSetRepo.find(companyId, empId);

		if (optMonthActCalSet.isPresent()) {
			deforLaborShaDto.setSettingDto(DeforWorkTimeAggrSetDto.fromDomain(optMonthActCalSet.get()));
		}

		return deforLaborShaDto;
	}

}
