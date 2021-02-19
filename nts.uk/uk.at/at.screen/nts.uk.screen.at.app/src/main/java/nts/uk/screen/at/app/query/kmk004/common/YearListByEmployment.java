package nts.uk.screen.at.app.query.kmk004.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetYearFromYearMonthPeriod;
import nts.uk.shr.com.context.AppContexts;

/**
 * 雇用別年度リストを表示する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.雇用別年度リストを表示する.雇用別年度リストを表示する
 * 
 * @author chungnt
 *
 */

@Stateless
public class YearListByEmployment {

	@Inject
	private MonthlyWorkTimeSetRepo workTimeSetRepo;
	
	@Inject
	private CompanyRepository companyRepository;

	public List<YearDto> get(String employmentCode, LaborWorkTypeAttr laborAttr) {

		String cid = AppContexts.user().companyId();
		List<YearDto> result = new ArrayList<>();

		// 1 Call 雇用別月単位労働時間
		List<MonthlyWorkTimeSetEmp> workTimeSetEmps = workTimeSetRepo.findEmployment(cid, employmentCode, laborAttr);

		// 2 Call DS 年月期間から年度を取得
		if(!workTimeSetEmps.isEmpty()) {
			Require require = new Require(companyRepository);

			List<Year> years = GetYearFromYearMonthPeriod.getYearFromYearMonthPeriod(require, cid,
					workTimeSetEmps.stream().map(m -> m.getYm()).collect(Collectors.toList()));

			result = years.stream().map(m -> {
				return new YearDto(m.v());
			}).collect(Collectors.toList());
		}

		return result;
	}

	@AllArgsConstructor
	public static class Require implements GetYearFromYearMonthPeriod.Require {

		private CompanyRepository companyRepository;

		@Override
		public Optional<Company> find(String companyId) {
			return companyRepository.find(AppContexts.user().companyId());
		}

	}

}
