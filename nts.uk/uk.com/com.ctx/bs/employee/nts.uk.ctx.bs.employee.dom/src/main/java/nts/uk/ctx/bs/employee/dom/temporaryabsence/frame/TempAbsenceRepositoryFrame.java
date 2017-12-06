package nts.uk.ctx.bs.employee.dom.temporaryabsence.frame;

import java.util.List;

public interface TempAbsenceRepositoryFrame {
	
	void udpate(TempAbsenceFrame tempAbsenceFrame);
	
	TempAbsenceFrame findByTempAbsenceFramePk (String cId, int tempAbsenceFrameNo);
	
	List<TempAbsenceFrame> findByCid(String cId);
}
