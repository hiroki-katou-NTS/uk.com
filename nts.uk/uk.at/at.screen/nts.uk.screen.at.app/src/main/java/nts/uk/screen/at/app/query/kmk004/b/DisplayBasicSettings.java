package nts.uk.screen.at.app.query.kmk004.b;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 会社別基本設定（通常勤務）を表示する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.F：基本設定（通常勤務）.old.old.会社別基本設定（通常勤務）を表示する
 * @author chungnt
 *
 */

@Stateless
public class DisplayBasicSettings {

	@Inject
	private RegularLaborTimeComRepo comRepo;
	
	@Inject
	private ComRegulaMonthActCalSetRepo actCalSetRepo;
	
	public DisplayBasicSettingsDto getSetting() {
		DisplayBasicSettingsDto result = new DisplayBasicSettingsDto();
		String companyId = AppContexts.user().companyId();

		Optional<RegularLaborTimeCom> regularLaborTimeCom = comRepo.find(companyId);

		if (regularLaborTimeCom.isPresent()) {
			result.setDaily(regularLaborTimeCom.get().getDailyTime().getDailyTime().v());
			result.setWeekly(regularLaborTimeCom.get().getWeeklyTime().getTime().v());
		}

		Optional<ComRegulaMonthActCalSet> comRegulaMonthActCalSet = actCalSetRepo.find(companyId);

		if (comRegulaMonthActCalSet.isPresent()) {
			result.setDeforWorkLegalHoliday(comRegulaMonthActCalSet.get().getAggregateTimeSet().isLegalHoliday());
			result.setDeforWorkLegalOverTimeWork(
					comRegulaMonthActCalSet.get().getAggregateTimeSet().isLegalOverTimeWork());
			result.setDeforWorkSurchargeWeekMonth(
					comRegulaMonthActCalSet.get().getAggregateTimeSet().isSurchargeWeekMonth());
			result.setOutsidedeforWorkLegalHoliday(
					comRegulaMonthActCalSet.get().getExcessOutsideTimeSet().isLegalHoliday());
			result.setOutsidedeforWorkLegalOverTimeWork(
					comRegulaMonthActCalSet.get().getExcessOutsideTimeSet().isLegalOverTimeWork());
			result.setOutsideSurchargeWeekMonth(
					comRegulaMonthActCalSet.get().getExcessOutsideTimeSet().isSurchargeWeekMonth());
		}
		
		return result;
	}
}
