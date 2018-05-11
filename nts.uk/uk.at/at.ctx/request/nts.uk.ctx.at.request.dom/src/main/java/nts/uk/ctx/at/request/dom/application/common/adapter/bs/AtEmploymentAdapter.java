package nts.uk.ctx.at.request.dom.application.common.adapter.bs;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmploymentHisImport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AtEmploymentAdapter {

	//RequestList 264
	List<EmploymentHisImport> findByListSidAndPeriod(String sId, DatePeriod datePeriod);
}
