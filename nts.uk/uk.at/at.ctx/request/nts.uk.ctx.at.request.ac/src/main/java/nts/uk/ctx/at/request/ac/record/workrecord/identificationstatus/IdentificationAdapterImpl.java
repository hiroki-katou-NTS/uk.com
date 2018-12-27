package nts.uk.ctx.at.request.ac.record.workrecord.identificationstatus;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.workrecord.identificationstatus.IdentificationAdapter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class IdentificationAdapterImpl implements IdentificationAdapter {

	@Inject
	private IdentificationRepository identificationRepository;
	
	@Override
	public List<GeneralDate> getProcessingYMD(String companyID, String employeeID, DatePeriod period) {
		return identificationRepository.findByEmployeeIDSortDate(employeeID, period.start(), period.end())
				.stream().map(x -> x.getProcessingYmd()).collect(Collectors.toList());
	}

}
