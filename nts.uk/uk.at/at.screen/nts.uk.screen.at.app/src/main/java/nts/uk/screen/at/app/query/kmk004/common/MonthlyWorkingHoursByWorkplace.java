package nts.uk.screen.at.app.query.kmk004.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetThePeriodOfTheYear;
import nts.uk.shr.com.context.AppContexts;

/**
 * 職場別月単位労働時間を表示する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.職場別月単位労働時間を表示する.職場別月単位労働時間を表示する
 * @author chungnt
 *
 */
@Stateless
public class MonthlyWorkingHoursByWorkplace {
	
	@Inject
	private CompanyRepository companyRepo;
	@Inject
	private MonthlyWorkTimeSetRepo workTimeSetRepo;
	
	/**
	 * 
	 * @param workplaceId	職場ID
	 * @param labor			勤務区分
	 * @param year			年度
	 */
	public List<DisplayMonthlyWorkingDto> get(String workplaceId, LaborWorkTypeAttr laborAttr, int year) {
		
		String cid = AppContexts.user().companyId();
		List<DisplayMonthlyWorkingDto> result = new ArrayList<>();
		
		//1 Call DS 年度の期間を取得
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, cid,
				year);
		
		//2 Call 職場別月単位労働時間
		List<MonthlyWorkTimeSetWkp> workTimeSetWkps = workTimeSetRepo.findWorkplaceByPeriod(cid, workplaceId, laborAttr, yearMonths);
		
		result = workTimeSetWkps.stream().map(m -> {
			DisplayMonthlyWorkingDto s = new DisplayMonthlyWorkingDto(m.getYm().v(),
					new LaborTime(m.getLaborTime().getLegalLaborTime().v(),
							m.getLaborTime().getWithinLaborTime().map(c -> c.v()).orElse(null),
							m.getLaborTime().getWeekAvgTime().map(c -> c.v()).orElse(null)));
			return s;
		}).collect(Collectors.toList());
		
		return result;
	}
	
	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}

	}

}
