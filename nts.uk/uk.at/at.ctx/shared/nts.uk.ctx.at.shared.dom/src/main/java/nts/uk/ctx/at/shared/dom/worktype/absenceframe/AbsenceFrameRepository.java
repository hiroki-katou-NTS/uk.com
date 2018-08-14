package nts.uk.ctx.at.shared.dom.worktype.absenceframe;

import java.util.List;
import java.util.Optional;

public interface AbsenceFrameRepository {
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<AbsenceFrame> findAbsenceFrame(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<AbsenceFrame> findAll(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @param frameNo
	 * @return
	 */
	Optional<AbsenceFrame> findAbsenceFrameByCode(String companyId, int frameNo);
	
	/**
	 * 
	 * @param companyId
	 * @param frameNos
	 * @return
	 */
	List<AbsenceFrame> findAbsenceFrameByListFrame(String companyId, List<Integer> frameNos);
	
	/**
	 * Update the Absence Frame.
	 *
	 * @param absenceFrame the absence frame
	 */
	void update(AbsenceFrame absenceFrame);
}
