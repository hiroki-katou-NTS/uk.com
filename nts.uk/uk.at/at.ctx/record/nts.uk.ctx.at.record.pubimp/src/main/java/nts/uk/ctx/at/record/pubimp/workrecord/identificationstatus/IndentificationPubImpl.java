package nts.uk.ctx.at.record.pubimp.workrecord.identificationstatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.pub.workrecord.identificationstatus.IndentificationPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class IndentificationPubImpl implements IndentificationPub {
   
	@Inject
	private IdentificationRepository identificationRepository;
	
	@Override
	public List<GeneralDate> getResovleDateIdentify(String employeeId, DatePeriod datePeriod) {
		List<Identification> identifications = identificationRepository.findByEmployeeIDSortDate(employeeId, datePeriod.start(), datePeriod.end());
		if (identifications.isEmpty())
			return Collections.emptyList();
		else
			return identifications.stream().map(x -> x.getProcessingYmd()).collect(Collectors.toList());
	}

}
