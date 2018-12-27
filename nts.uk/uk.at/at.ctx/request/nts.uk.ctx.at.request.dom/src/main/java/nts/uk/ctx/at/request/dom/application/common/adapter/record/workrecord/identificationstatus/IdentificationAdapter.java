package nts.uk.ctx.at.request.dom.application.common.adapter.record.workrecord.identificationstatus;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface IdentificationAdapter {
	
	public List<GeneralDate> getProcessingYMD(String companyID, String employeeID, DatePeriod period);

}
