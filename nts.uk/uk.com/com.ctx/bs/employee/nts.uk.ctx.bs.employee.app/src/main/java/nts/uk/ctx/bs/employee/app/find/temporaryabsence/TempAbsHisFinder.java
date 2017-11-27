package nts.uk.ctx.bs.employee.app.find.temporaryabsence;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregSingleFinder;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * Temporary absence finder
 */

@Stateless
public class TempAbsHisFinder implements PeregSingleFinder<TempAbsHisItemDto>{

	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepository;

	@Override
	public String targetCategoryCode() {
		return "CS00008";
	}

	@Override
	public Class<TempAbsHisItemDto> dtoClass() {
		return TempAbsHisItemDto.class;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregSingleFinder#getCtgSingleData(nts.uk.shr.pereg.app.find.PeregQuery)
	 */
	@Override
	public PeregDto getSingleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}
	
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
