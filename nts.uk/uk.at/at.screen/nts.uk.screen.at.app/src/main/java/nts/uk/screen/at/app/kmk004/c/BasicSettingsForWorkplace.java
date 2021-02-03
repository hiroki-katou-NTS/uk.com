package nts.uk.screen.at.app.kmk004.c;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.C：職場別法定労働時間の登録（通常勤務）.メニュー別OCD.職場別基本設定（通常勤務）を表示する.職場別基本設定（通常勤務）を表示する
 * @author chungnt
 *
 */

@Stateless
public class BasicSettingsForWorkplace {
	
	@Inject
	private RegularLaborTimeWkpRepo laborTimeWkpRepo;
	
	@Inject
	private WkpRegulaMonthActCalSetRepo monthActCalSetRepo;
	
	
	public DisplayBasicSettingsDto getSetting(String workPlaceId) {
		DisplayBasicSettingsDto result = new DisplayBasicSettingsDto();
		String companyId = AppContexts.user().companyId();

		// 1 get(職場別通常勤務法定労働時間)
		Optional<RegularLaborTimeWkp> regularLaborTimeCom = laborTimeWkpRepo.find(companyId, workPlaceId);

		if (regularLaborTimeCom.isPresent()) {
			result.setDaily(regularLaborTimeCom.get().getDailyTime().getDailyTime().v());
			result.setWeekly(regularLaborTimeCom.get().getWeeklyTime().getTime().v());
		} else {
			return null;
		}

		// 2 get(職場別通常勤務集計設定)
		Optional<WkpRegulaMonthActCalSet> comRegulaMonthActCalSet = monthActCalSetRepo.find(companyId, workPlaceId);

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
