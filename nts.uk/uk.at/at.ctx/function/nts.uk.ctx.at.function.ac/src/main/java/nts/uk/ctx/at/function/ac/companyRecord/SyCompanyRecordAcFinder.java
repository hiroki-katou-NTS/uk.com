package nts.uk.ctx.at.function.ac.companyRecord;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.companyRecord.StatusOfEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.companyRecord.SyCompanyRecordFuncAdapter;
import nts.uk.ctx.at.record.pub.company.SyCompanyRecordPub;
@Stateless
public class SyCompanyRecordAcFinder implements SyCompanyRecordFuncAdapter {

	@Inject
	private SyCompanyRecordPub comPub;
	@Override
	public List<StatusOfEmployeeAdapter> getAffCompanyHistByEmployee(List<String> sid, DatePeriod datePeriod) {
		List<StatusOfEmployeeAdapter> lstEmp = comPub.getAffCompanyHistByEmployee(sid, datePeriod)
				.stream().map(x -> new StatusOfEmployeeAdapter(x.getEmployeeId(), x.getListPeriod()))
				.collect(Collectors.toList());
		return lstEmp;
	}


}
