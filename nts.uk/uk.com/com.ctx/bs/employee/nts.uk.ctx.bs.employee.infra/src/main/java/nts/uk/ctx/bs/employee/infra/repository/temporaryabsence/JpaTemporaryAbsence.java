package nts.uk.ctx.bs.employee.infra.repository.temporaryabsence;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTempAbsHisItem;

@Stateless
@Transactional
public class JpaTemporaryAbsence extends JpaRepository implements TemporaryAbsenceRepository {

	private final String GET_BY_SID_DATE = "select hi from BsymtTempAbsHisItem hi"
			+ " join in BsymtTempAbsHistory h on hi.histId = h.histId"
			+ " where hi.sid = :sid and h.startDate <= :referDate and h.endDate >= referDate";

	private final String GET_BY_HID = "select hi from BsymtTempAbsHisItem hi where hi.histId = :histId";

	@Override
	public Optional<TempAbsenceHisItem> getBySidAndReferDate(String sid, GeneralDate referenceDate) {
		Optional<BsymtTempAbsHisItem> option = this.queryProxy().query(GET_BY_SID_DATE, BsymtTempAbsHisItem.class)
				.setParameter("sid", sid).setParameter("referDate", referenceDate).getSingle();
		if (option.isPresent()) {
			return Optional.of(toDomain(option.get()));
		}
		return Optional.empty();
	}

	private TempAbsenceHisItem toDomain(BsymtTempAbsHisItem ent) {
		Boolean multiple = ent.multiple == null ? null : ent.multiple == 1;
		Boolean sameFamily = ent.sameFamily == null ? null : ent.sameFamily == 1;
		Boolean spouseIsLeave = ent.spouseIsLeave == null ? null : ent.spouseIsLeave == 1;
		return TempAbsenceHisItem.createTempAbsenceHisItem(ent.leaveHolidayAtr, ent.histId, ent.sid,
				ent.remarks, Integer.valueOf(ent.soInsPayCategory), multiple, ent.familyMemberId, sameFamily,
				ent.childType, ent.createDate, spouseIsLeave, ent.sameFamilyDays);
	}

	@Override
	public Optional<TempAbsenceHisItem> getByTempAbsenceId(String tempAbsenceId) {
		Optional<BsymtTempAbsHisItem> option = this.queryProxy().query(GET_BY_HID, BsymtTempAbsHisItem.class)
				.setParameter("histId", tempAbsenceId).getSingle();
		if (option.isPresent()) {
			return Optional.of(toDomain(option.get()));
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository#
	 * getListBySid(java.lang.String)
	 */
	@Override
	public List<TempAbsenceHisItem> getListBySid(String sid) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository#
	 * addTemporaryAbsence(nts.uk.ctx.bs.employee.dom.temporaryabsence.
	 * TempAbsenceHisItem)
	 */
	@Override
	public void addTemporaryAbsence(TempAbsenceHisItem domain) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository#
	 * updateTemporaryAbsence(nts.uk.ctx.bs.employee.dom.temporaryabsence.
	 * TempAbsenceHisItem)
	 */
	@Override
	public void updateTemporaryAbsence(TempAbsenceHisItem domain) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository#
	 * deleteTemporaryAbsence(nts.uk.ctx.bs.employee.dom.temporaryabsence.
	 * TempAbsenceHisItem)
	 */
	@Override
	public void deleteTemporaryAbsence(TempAbsenceHisItem domain) {
		// TODO Auto-generated method stub

	}

}
