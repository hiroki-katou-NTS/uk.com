package nts.uk.ctx.bs.employee.dom.temporaryabsence.frame;

import java.util.List;

/**
 * The Interface TempAbsenceRepositoryFrame.
 */
public interface TempAbsenceRepositoryFrame {
	
	/**
	 * Udpate.
	 *
	 * @param tempAbsenceFrame the temp absence frame
	 */
	void udpate(TempAbsenceFrame tempAbsenceFrame);
	
	/**
	 * Find by temp absence frame pk.
	 *
	 * @param cId the c id
	 * @param tempAbsenceFrameNo the temp absence frame no
	 * @return the temp absence frame
	 */
	TempAbsenceFrame findByTempAbsenceFramePk (String cId, int tempAbsenceFrameNo);
	
	/**
	 * Find by cid.
	 *
	 * @param cId the c id
	 * @return the list
	 */
	List<TempAbsenceFrame> findByCid(String cId);
}
