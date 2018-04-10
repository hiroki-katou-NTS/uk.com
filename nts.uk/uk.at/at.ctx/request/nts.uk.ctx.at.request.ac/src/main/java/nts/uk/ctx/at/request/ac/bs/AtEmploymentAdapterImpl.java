package nts.uk.ctx.at.request.ac.bs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmploymentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmploymentHisImport;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class AtEmploymentAdapterImpl implements AtEmploymentAdapter{

	@Inject
	private SyEmploymentPub syEmploymentPub;
	
	@Override
	public List<EmploymentHisImport> findByListSidAndPeriod(String sId, DatePeriod datePeriod) {
		List<EmploymentHisImport> empHist =  syEmploymentPub
					.findByListSidAndPeriod(Arrays.asList(sId), datePeriod)
					.stream().map(c -> new EmploymentHisImport(c.getEmployeeId(),
							c.getHistoryID(), c.getEmploymentCode(), c.getSalarySegment()))
					.collect(Collectors.toList());
		return empHist;
	}

}
