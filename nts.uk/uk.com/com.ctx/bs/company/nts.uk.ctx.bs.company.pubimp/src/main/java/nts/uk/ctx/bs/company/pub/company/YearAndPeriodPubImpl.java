package nts.uk.ctx.bs.company.pub.company;

import java.util.Optional;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetYearAndPeriodService;
import nts.uk.ctx.bs.company.dom.company.YearAndPeriod;

/**
* @author sakuratani
* 
*			指定した締め期間の年期間を算出するPubImpl
*
*/
public class YearAndPeriodPubImpl implements YearAndPeriodPub {

	@Inject
	private CompanyRepository repo;

	public YearAndPeriodExport get(String cid, DatePeriod period) {
		RequireImpl impl = new RequireImpl(repo);
		return toExport(GetYearAndPeriodService.get(impl, cid, period));
	}

	@AllArgsConstructor
	public class RequireImpl implements GetYearAndPeriodService.Require {

		private CompanyRepository repo;

		@Override
		public Optional<Company> find(String cid) {
			return repo.find(cid);
		}
	}

	//YearAndPeriod -> YearAndPeriodExport へ変換
	private YearAndPeriodExport toExport(YearAndPeriod dom) {
		return new YearAndPeriodExport(dom.getYear(), dom.getStartDate(), dom.getEndDate());
	}
}
