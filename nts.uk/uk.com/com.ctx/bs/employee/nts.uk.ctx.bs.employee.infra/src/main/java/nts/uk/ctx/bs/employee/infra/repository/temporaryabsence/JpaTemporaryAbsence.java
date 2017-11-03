package nts.uk.ctx.bs.employee.infra.repository.temporaryabsence;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.infra.entity.temporaryAbsence.BsymtTemporaryAbsence;

@Stateless
public class JpaTemporaryAbsence extends JpaRepository implements TemporaryAbsenceRepository {

	public final String SELECT_BY_SID_AND_REFERENCEDATE = "SELECT c FROM BsymtTemporaryAbsence c WHERE c.bsymtTemporaryAbsencePK.sid = :sid"
			+ " AND c.startDate <= :referenceDate  AND c.endDate >= :referenceDate ";

	public static final String SELECT_BY_TEMP_ABSENCE = "SELECT c FROM BsymtTemporaryAbsence c WHERE c.bsymtTemporaryAbsencePK.leaveHolidayId = :tempAbsenceId";

	private TemporaryAbsence toTemporaryAbsence(BsymtTemporaryAbsence entity) {
		val domain = TemporaryAbsence.createSimpleFromJavaType(entity.sid,
				entity.bsymtTemporaryAbsencePK.leaveHolidayId, entity.leaveHolidayAtr, entity.startDate, entity.endDate, entity.reason,
				entity.familyMemberId, entity.birthday, entity.multiple);
		return domain;
	}

	@Override
	public Optional<TemporaryAbsence> getBySidAndReferDate(String sid, GeneralDate referenceDate) {
		BsymtTemporaryAbsence entity = this.queryProxy()
				.query(SELECT_BY_SID_AND_REFERENCEDATE, BsymtTemporaryAbsence.class).setParameter("sid", sid)
				.setParameter("referenceDate", referenceDate).getSingleOrNull();

		TemporaryAbsence temporaryAbsence = toTemporaryAbsence(entity);

		return Optional.of(temporaryAbsence);
	}

	// vinhpx: start
	@Override
	public Optional<TemporaryAbsence> getByTempAbsenceId(String tempAbsenceId) {
		return this.queryProxy().query(SELECT_BY_TEMP_ABSENCE, BsymtTemporaryAbsence.class)
				.setParameter("tempAbsenceId", tempAbsenceId).getSingle(x -> toTemporaryAbsence(x));
	}
	// vinhpx: end
}
