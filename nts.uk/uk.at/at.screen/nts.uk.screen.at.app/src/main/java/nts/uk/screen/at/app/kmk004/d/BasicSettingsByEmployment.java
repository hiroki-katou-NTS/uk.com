package nts.uk.screen.at.app.kmk004.d;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.D：雇用別法定労働時間の登録（通常勤務）.メニュー別OCD.雇用別基本設定（通常勤務）を表示する.雇用別基本設定（通常勤務）を表示する
 *
 */

@Stateless
public class BasicSettingsByEmployment {

	@Inject
	private RegularLaborTimeEmpRepo empRepo;
	
	@Inject
	private EmpRegulaMonthActCalSetRepo monthActCalSetRepo;
	
	public DisplayBasicSettingsDto getSetting(String employmentCode) {
		DisplayBasicSettingsDto result = new DisplayBasicSettingsDto();
		String companyId = AppContexts.user().companyId();

		// 1 get(職場別通常勤務法定労働時間)
		Optional<RegularLaborTimeEmp> regularLaborTimeEmp = empRepo.findById(companyId, employmentCode);

		if (regularLaborTimeEmp.isPresent()) {
			result.setDaily(regularLaborTimeEmp.get().getDailyTime().getDailyTime().v());
			result.setWeekly(regularLaborTimeEmp.get().getWeeklyTime().getTime().v());
		} else {
			return null;
		}

		// 2 get(職場別通常勤務集計設定)
		Optional<EmpRegulaMonthActCalSet> comRegulaMonthActCalSet = monthActCalSetRepo.find(companyId, employmentCode);

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
