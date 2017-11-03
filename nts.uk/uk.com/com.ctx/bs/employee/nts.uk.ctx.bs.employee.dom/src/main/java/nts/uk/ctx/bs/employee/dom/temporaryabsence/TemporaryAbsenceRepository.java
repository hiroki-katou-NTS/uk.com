package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface TemporaryAbsenceRepository {

	Optional<TemporaryAbsence> getBySidAndReferDate(String sid, GeneralDate referenceDate);
	
	Optional<TemporaryAbsence> getByTempAbsenceId(String tempAbsenceId);
	
	List<TemporaryAbsence> getListBySid(String sid);
	
}
