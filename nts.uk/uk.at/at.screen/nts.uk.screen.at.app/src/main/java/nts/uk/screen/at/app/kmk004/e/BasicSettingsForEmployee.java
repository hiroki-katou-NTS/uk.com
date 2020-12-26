package nts.uk.screen.at.app.kmk004.e;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeShaRepo;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.E：社員別法定労働時間の登録（通常勤務）.メニュー別OCD.社員を選択する（通常勤務）.社員を選択する（通常勤務）
 * @author chungnt
 *
 */

@Stateless
public class BasicSettingsForEmployee {

	@Inject
	private RegularLaborTimeShaRepo laborTimeShaRepo;
	
	@Inject
	private ShaRegulaMonthActCalSetRepo regulaMonthActCalSetRepo;
	
	public DisplayBasicSettingsDto getSetting(String sid) {
		DisplayBasicSettingsDto result = new DisplayBasicSettingsDto();
		String companyId = AppContexts.user().companyId();

		// 1 get(職場別通常勤務法定労働時間)
		Optional<RegularLaborTimeSha> regularLaborTimeSha = laborTimeShaRepo.find(companyId, sid);

		if (regularLaborTimeSha.isPresent()) {
			result.setDaily(regularLaborTimeSha.get().getDailyTime().getDailyTime().v());
			result.setWeekly(regularLaborTimeSha.get().getWeeklyTime().getTime().v());
		} else {
			return null;
		}

		// 2 get(職場別通常勤務集計設定)
		Optional<ShaRegulaMonthActCalSet> shaRegulaMonthActCalSet = regulaMonthActCalSetRepo.find(companyId, sid);

		if (shaRegulaMonthActCalSet.isPresent()) {
			result.setDeforWorkLegalHoliday(shaRegulaMonthActCalSet.get().getAggregateTimeSet().isLegalHoliday());
			result.setDeforWorkLegalOverTimeWork(
					shaRegulaMonthActCalSet.get().getAggregateTimeSet().isLegalOverTimeWork());
			result.setDeforWorkSurchargeWeekMonth(
					shaRegulaMonthActCalSet.get().getAggregateTimeSet().isSurchargeWeekMonth());
			result.setOutsidedeforWorkLegalHoliday(
					shaRegulaMonthActCalSet.get().getExcessOutsideTimeSet().isLegalHoliday());
			result.setOutsidedeforWorkLegalOverTimeWork(
					shaRegulaMonthActCalSet.get().getExcessOutsideTimeSet().isLegalOverTimeWork());
			result.setOutsideSurchargeWeekMonth(
					shaRegulaMonthActCalSet.get().getExcessOutsideTimeSet().isSurchargeWeekMonth());
		} else {
			return null;
		}

		return result;
	}
	
}
