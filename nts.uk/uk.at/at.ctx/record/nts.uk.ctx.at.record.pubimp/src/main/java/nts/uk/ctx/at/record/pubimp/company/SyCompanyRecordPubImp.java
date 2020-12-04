package nts.uk.ctx.at.record.pubimp.company;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.at.record.pub.company.StatusOfEmployeeExportPub;
import nts.uk.ctx.at.record.pub.company.SyCompanyRecordPub;
@Stateless
public class SyCompanyRecordPubImp implements SyCompanyRecordPub {
	@Inject
	private SyCompanyRecordAdapter comRecordAdapter;

	@Override
	public List<StatusOfEmployeeExportPub> getAffCompanyHistByEmployee(List<String> sid, DatePeriod datePeriod) {
		List<StatusOfEmployeeExportPub> getListAffComHistByListSidAndPeriod = comRecordAdapter.getListAffComHistByListSidAndPeriod(sid, datePeriod)
				.stream().map(x -> new StatusOfEmployeeExportPub(x.getEmployeeId(), x.getListPeriod()))
				.collect(Collectors.toList());
		return getListAffComHistByListSidAndPeriod;
	}

}
