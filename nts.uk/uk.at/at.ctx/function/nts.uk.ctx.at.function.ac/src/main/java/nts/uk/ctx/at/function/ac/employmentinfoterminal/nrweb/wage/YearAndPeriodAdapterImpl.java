package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.wage;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual.YearAndPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual.YearAndPeriodImported;
import nts.uk.ctx.bs.company.pub.company.YearAndPeriodExport;
import nts.uk.ctx.bs.company.pub.company.YearAndPeriodPub;

/**
* @author sakuratani
*
*			指定した締め期間の年期間を算出するAdapterImpl
*
*/
@Stateless
public class YearAndPeriodAdapterImpl implements YearAndPeriodAdapter {

	@Inject
	YearAndPeriodPub yearAndPeriodPub;

	@Override
	public YearAndPeriodImported get(String cid, DatePeriod period) {
		return this.toImport(yearAndPeriodPub.get(cid, period));
	}

	//YearAndPeriodExport -> YearAndPeriodImported へ変換
	private YearAndPeriodImported toImport(YearAndPeriodExport export) {
		return new YearAndPeriodImported(export.getYear(), new DatePeriod(export.getStartDate(), export.getEndDate()));
	}
}
