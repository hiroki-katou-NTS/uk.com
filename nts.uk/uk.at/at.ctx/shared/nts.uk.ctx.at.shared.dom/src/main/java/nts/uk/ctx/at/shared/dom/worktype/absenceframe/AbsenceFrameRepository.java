package nts.uk.ctx.at.shared.dom.worktype.absenceframe;

import java.util.List;

public interface AbsenceFrameRepository {
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<AbsenceFrame> findAbsenceFrame(String companyId);
}
