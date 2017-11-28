package nts.uk.ctx.bs.employee.dom.temporaryabsence.frame;

import java.util.List;

public interface TempAbsenceRepositoryFrame {

	void add(TempAbsenceFrame tempAbsenceFrame);
	
	void udpate(TempAbsenceFrame tempAbsenceFrame);
	
	TempAbsenceFrame findByTAFPk (String cId, short tempAbsenceFrameNo);
	
	List<TempAbsenceFrame> findByCid(String cId);
}
