package nts.uk.ctx.at.record.pubimp.workrecord.closurestatus;

import java.time.Period;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.export.WorkRecordExport;
import nts.uk.ctx.at.record.dom.workrecord.export.dto.EmpAffInfoExport;
import nts.uk.ctx.at.record.pub.workrecord.closurestatus.GetAffiliationPeriodPub;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class GetAffiliationPeriodPubImpl implements GetAffiliationPeriodPub {

	@Inject
	private WorkRecordExport workRecordExport; 
	
	@Override
	public EmpAffInfoExport getAffiliationPeriod(List<String> listSid, YearMonthPeriod YMPeriod, GeneralDate baseDate) {
		return workRecordExport.getAffiliationPeriod(listSid, YMPeriod, baseDate);
	}

}
