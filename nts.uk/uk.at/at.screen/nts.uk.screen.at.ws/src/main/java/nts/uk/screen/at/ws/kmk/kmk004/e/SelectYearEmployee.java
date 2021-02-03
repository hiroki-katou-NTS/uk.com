package nts.uk.screen.at.ws.kmk.kmk004.e;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetThePeriodOfTheYear;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.LaborTime;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.E：社員別法定労働時間の登録（通常勤務）.メニュー別OCD.社員別年度（通常勤務）を選択する
 * @author chungnt
 *
 */

@Stateless
public class SelectYearEmployee {

	@Inject
	private CompanyRepository companyRepo;
	
	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
	
	/**
	 * 
	 * @param sid		　社員ID
	 * @param laborAttr	　勤務区分
	 * @param year		　年度
	 */
	public List<DisplayMonthlyWorkingDto> get(String sid, LaborWorkTypeAttr laborAttr, int year) {
		
		String cid = AppContexts.user().companyId();
		List<DisplayMonthlyWorkingDto> result = new ArrayList<>();
		
		//1 Call DS 年度の期間を取得
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, cid, year);
		
		result = yearMonths.stream().map(m -> {
			LaborTime laborTime = new LaborTime(-1, null, null);
			DisplayMonthlyWorkingDto s = new DisplayMonthlyWorkingDto(m.v(), laborTime);
			return s;
		}).collect(Collectors.toList());
		
		//2 Call 社員別月単位労働時間
		List<MonthlyWorkTimeSetSha> list = monthlyWorkTimeSetRepo.findEmployeeByPeriod(cid, sid, laborAttr, yearMonths);
		
		if (list.isEmpty()) {
			return result;
		}
		
		result.stream().forEach(m -> {
			Optional<MonthlyWorkTimeSetSha> setSha = list.stream().filter(x -> x.getYm().v() == m.getYearMonth())
					.findFirst();
			if (setSha.isPresent()) {
				m.setLaborTime(new LaborTime(setSha.get().getLaborTime().getLegalLaborTime().v(),
						setSha.get().getLaborTime().getWithinLaborTime().map(c -> c.v()).orElse(null),
						setSha.get().getLaborTime().getWeekAvgTime().map(c -> c.v()).orElse(null)));
			}
		});
		
		return result;
	}
	
	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}
	}
	
}
