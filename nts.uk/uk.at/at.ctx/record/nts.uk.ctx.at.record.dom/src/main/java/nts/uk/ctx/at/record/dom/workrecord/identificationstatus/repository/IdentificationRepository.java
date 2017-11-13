package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface IdentificationRepository {
	
	void delete(String employeeId, GeneralDate processingYmd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> processingYmds);

}
