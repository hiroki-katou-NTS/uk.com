package nts.uk.ctx.bs.employee.dom.temporaryAbsence;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface TemporaryAbsenceRepository {

	Optional<TemporaryAbsence> getBySid(String sid, GeneralDate referenceDate);
	
}
