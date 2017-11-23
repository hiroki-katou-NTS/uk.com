package nts.uk.ctx.bs.employee.infra.repository.temporaryabsence;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;

@Stateless
@Transactional
public class JpaTemporaryAbsence extends JpaRepository implements TemporaryAbsenceRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository#getBySidAndReferDate(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<TempAbsenceHisItem> getBySidAndReferDate(String sid, GeneralDate referenceDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository#getByTempAbsenceId(java.lang.String)
	 */
	@Override
	public Optional<TempAbsenceHisItem> getByTempAbsenceId(String tempAbsenceId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository#getListBySid(java.lang.String)
	 */
	@Override
	public List<TempAbsenceHisItem> getListBySid(String sid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository#addTemporaryAbsence(nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem)
	 */
	@Override
	public void addTemporaryAbsence(TempAbsenceHisItem domain) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository#updateTemporaryAbsence(nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem)
	 */
	@Override
	public void updateTemporaryAbsence(TempAbsenceHisItem domain) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository#deleteTemporaryAbsence(nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem)
	 */
	@Override
	public void deleteTemporaryAbsence(TempAbsenceHisItem domain) {
		// TODO Auto-generated method stub
		
	}


	
}
