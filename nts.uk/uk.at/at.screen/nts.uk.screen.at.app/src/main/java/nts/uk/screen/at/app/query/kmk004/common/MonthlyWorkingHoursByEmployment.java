package nts.uk.screen.at.app.query.kmk004.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetThePeriodOfTheYear;
import nts.uk.shr.com.context.AppContexts;

/**
 * 雇用別月単位労働時間を表示する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.雇用別月単位労働時間を表示する.雇用別月単位労働時間を表示する
 * @author chungnt
 *
 */

@Stateless
public class MonthlyWorkingHoursByEmployment {

	@Inject
	private CompanyRepository companyRepo;
	
	@Inject
	private MonthlyWorkTimeSetRepo workTimeSetRepo;
	
	/**
	 * 
	 * @param employmentCode	雇用コード
	 * @param laborAttr			勤務区分
	 * @param year				年度
	 */
	public List<DisplayMonthlyWorkingDto> get(String employmentCode, LaborWorkTypeAttr laborAttr, int year) {
		
		String cid = AppContexts.user().companyId();
		List<DisplayMonthlyWorkingDto> result = new ArrayList<>();
		
		//1 Call DS 年度の期間を取得
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, cid,
				year);
		
		result = yearMonths.stream().map(m -> {
			LaborTime laborTime = new LaborTime(0,0,0);
			DisplayMonthlyWorkingDto s = new DisplayMonthlyWorkingDto(m.v(), laborTime);
			return s;
		}).collect(Collectors.toList());
		
		//2 Call 雇用別月単位労働時間
		List<MonthlyWorkTimeSetEmp> emps = workTimeSetRepo.findEmploymentByPeriod(cid, employmentCode, laborAttr, yearMonths);
		
		if (emps.isEmpty()) {
			return result;
		}
		
		result.stream().forEach(m -> {
			Optional<MonthlyWorkTimeSetEmp> setEmp = emps.stream()
					.filter(x -> x.getYm().v() == m.getYearMonth()).findFirst();
			if (setEmp.isPresent()) {
				m.setLaborTime(new LaborTime(setEmp.get().getLaborTime().getLegalLaborTime().v(),
						setEmp.get().getLaborTime().getWithinLaborTime().map(c -> c.v()).orElse(null),
						setEmp.get().getLaborTime().getWeekAvgTime().map(c -> c.v()).orElse(null)));
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
