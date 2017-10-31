package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface TemporaryAbsenceRepository {

	Optional<TemporaryAbsence> getBySid(String sid, GeneralDate referenceDate);
	
	Optional<TemporaryAbsence> getByTempAbsenceId(String tempAbsenceId);
	
}
