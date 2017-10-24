package nts.uk.ctx.bs.employee.infra.repository.temporaryAbsence;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryAbsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryAbsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.infra.entity.temporaryAbsence.BsymtTemporaryAbsence;

@Stateless
public class JpaTemporaryAbsence extends JpaRepository implements TemporaryAbsenceRepository {

	public final String SELECT_BY_SID_AND_REFERENCEDATE = "SELECT c FROM BsymtTemporaryAbsence c WHERE c.bsymtTemporaryAbsencePK.sid = :sid"
			+ " AND c.startDate <= :referenceDate  AND c.endDate >= :referenceDate ";

	private TemporaryAbsence toDomainEmployee(BsymtTemporaryAbsence entity) {
		val domain = TemporaryAbsence.createSimpleFromJavaType(entity.getBsymtTemporaryAbsencePK().getSid(),
				entity.getLeaveHolidayId(), entity.getLeaveHolidayAtr(), entity.getStartDate(), entity.getEndDate(),
				entity.getReason(), entity.getFamilyMemberId(), entity.getBirthday(), entity.getMultiple());
		return domain;
	}

	@Override
	public Optional<TemporaryAbsence> getBySid(String sid, GeneralDate referenceDate) {
		BsymtTemporaryAbsence entity = this.queryProxy()
				.query(SELECT_BY_SID_AND_REFERENCEDATE, BsymtTemporaryAbsence.class)
				.setParameter("sid", sid)
				.setParameter("referenceDate", referenceDate).getSingleOrNull();
		
		TemporaryAbsence temporaryAbsence = toDomainEmployee(entity);
		
		return Optional.of(temporaryAbsence);
	}

}
