package nts.uk.screen.at.app.query.kmk004.common;

import java.util.Optional;

import javax.ejb.Stateless;
/**
 * 
 * @author chungnt
 *
 */
import javax.inject.Inject;

import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetThePeriodOfTheYear;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetYearMonthPeriod {

	@Inject
	private CompanyRepository companyRepo;
	
	public YearMonthPeriod get(int year) {
		String cid = AppContexts.user().companyId();
		
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, cid,
				year);
		
		return yearMonths;
	}
	
	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}

	}
}
