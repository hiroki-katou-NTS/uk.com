package nts.uk.ctx.bs.employee.app.find.temporaryabsence;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;

/**
 * Temporary absence finder
 */

@Stateless
public class TemporaryAbsenceFinder {

	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepository;

	public float getNumOfTempAbsenceDays(String employeeId) {
		List<TempAbsenceHisItem> lstTemporaryAbsence = temporaryAbsenceRepository.getListBySid(employeeId);
		if (lstTemporaryAbsence.size() == 0)
			return 0;
		/*return lstTemporaryAbsence.stream().map(m -> ChronoUnit.DAYS.between(m.getDateHistoryItem().start().localDate(),
				m.getDateHistoryItem().end().localDate())).mapToInt(m -> Math.abs(m.intValue())).sum();*/
		return 0;
		// TODO
	}

	public List<TempAbsenceHisItem> getListBySid(String employeeId) {
		return temporaryAbsenceRepository.getListBySid(employeeId);
	}
}
