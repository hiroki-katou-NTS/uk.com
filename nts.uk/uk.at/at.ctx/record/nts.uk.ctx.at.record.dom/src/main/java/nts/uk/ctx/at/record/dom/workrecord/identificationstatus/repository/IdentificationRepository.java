package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository;

import nts.arc.time.GeneralDate;

public interface IdentificationRepository {
	
	void delete(String employeeId, GeneralDate processingYmd);

}
