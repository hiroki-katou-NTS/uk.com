package nts.uk.ctx.bs.employee.infra.repository.temporaryabsence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTemporaryAbsence;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTemporaryAbsencePK;

@Stateless
public class JpaTemporaryAbsence extends JpaRepository implements TemporaryAbsenceRepository {

	public final String SELECT_BY_SID_AND_REFERENCEDATE = "SELECT c FROM BsymtTemporaryAbsence c WHERE c.sid = :sid"
			+ " AND c.startDate <= :referenceDate  AND c.endDate >= :referenceDate ";

	public static final String SELECT_BY_TEMP_ABSENCE = "SELECT c FROM BsymtTemporaryAbsence c WHERE c.bsymtTemporaryAbsencePK.leaveHolidayId = :tempAbsenceId";

	public static final String GETLIST_BY_SID = "SELECT c FROM BsymtTemporaryAbsence c WHERE c.sid = :sid";

	private TemporaryAbsence toTemporaryAbsence(BsymtTemporaryAbsence entity) {
		val domain = TemporaryAbsence.createSimpleFromJavaType(entity.sid,
				entity.bsymtTemporaryAbsencePK.leaveHolidayId, entity.leaveHolidayAtr, entity.startDate, entity.endDate,
				entity.reason, entity.familyMemberId, entity.birthday, entity.multiple);
		return domain;
	}

	private List<TemporaryAbsence> toListTemporaryAbsence(List<BsymtTemporaryAbsence> listEntity) {
		List<TemporaryAbsence> lstTemporaryAbsence = new ArrayList<>();
		if (!listEntity.isEmpty()) {
			listEntity.stream().forEach(c -> {
				TemporaryAbsence temporaryAbsence = toTemporaryAbsence(c);

				lstTemporaryAbsence.add(temporaryAbsence);
			});
		}
		return lstTemporaryAbsence;
	}

	@Override
	public Optional<TemporaryAbsence> getBySidAndReferDate(String sid, GeneralDate referenceDate) {
		BsymtTemporaryAbsence entity = this.queryProxy()
				.query(SELECT_BY_SID_AND_REFERENCEDATE, BsymtTemporaryAbsence.class).setParameter("sid", sid)
				.setParameter("referenceDate", referenceDate).getSingleOrNull();

		if (entity != null) {
			TemporaryAbsence temporaryAbsence = toTemporaryAbsence(entity);
			return Optional.of(temporaryAbsence);
		}else {
			return Optional.empty();
		}
		
	}

	// vinhpx: start
	@Override
	public Optional<TemporaryAbsence> getByTempAbsenceId(String tempAbsenceId) {
		return this.queryProxy().query(SELECT_BY_TEMP_ABSENCE, BsymtTemporaryAbsence.class)
				.setParameter("tempAbsenceId", tempAbsenceId).getSingle(x -> toTemporaryAbsence(x));
	}
	// vinhpx: end

	// laitv
	@Override
	public List<TemporaryAbsence> getListBySid(String sid) {

		List<BsymtTemporaryAbsence> listEntity = this.queryProxy().query(GETLIST_BY_SID, BsymtTemporaryAbsence.class)
				.setParameter("sid", sid).getList();

		return toListTemporaryAbsence(listEntity);
	}

	/**
	 * Convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private BsymtTemporaryAbsence toEntity(TemporaryAbsence domain) {
		BsymtTemporaryAbsencePK key = new BsymtTemporaryAbsencePK(domain.getTempAbsenceId());
		return new BsymtTemporaryAbsence(key, domain.getEmployeeId(), domain.getStartDate(), domain.getEndDate(),
				domain.getTempAbsenceType().value, domain.getTempAbsenceReason(), domain.getFamilyMemberId(),
				domain.getBirthDate(), domain.getMulPregnancySegment());
	}

	/**
	 * Update entity
	 * 
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(TemporaryAbsence domain, BsymtTemporaryAbsence entity) {
		entity.sid = domain.getEmployeeId();
		entity.startDate = domain.getStartDate();
		entity.endDate = domain.getEndDate();
		entity.leaveHolidayAtr = domain.getTempAbsenceType().value;
		entity.reason = domain.getTempAbsenceReason();
		entity.familyMemberId = domain.getFamilyMemberId();
		entity.birthday = domain.getBirthDate();
		entity.multiple = domain.getMulPregnancySegment();
	}

	/**
	 * ドメインモデル「休職休業」を新規登録する
	 * 
	 * @param domain
	 */
	@Override
	public void addTemporaryAbsence(TemporaryAbsence domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	/**
	 * 取得した「休職休業」を更新する
	 * 
	 * @param domain
	 */
	@Override
	public void updateTemporaryAbsence(TemporaryAbsence domain) {
		// Get exist item
		BsymtTemporaryAbsencePK key = new BsymtTemporaryAbsencePK(domain.getTempAbsenceId());
		Optional<BsymtTemporaryAbsence> existItem = this.queryProxy().find(key, BsymtTemporaryAbsence.class);
		if (!existItem.isPresent()) {
			return;
		}
		// Update entity
		updateEntity(domain, existItem.get());
		// Update table
		this.commandProxy().update(existItem.get());

	}

	/**
	 * ドメインモデル「休職休業」を削除する
	 * 
	 * @param domain
	 */
	@Override
	public void deleteTemporaryAbsence(TemporaryAbsence domain) {
		BsymtTemporaryAbsencePK key = new BsymtTemporaryAbsencePK(domain.getTempAbsenceId());
		this.commandProxy().remove(BsymtTemporaryAbsence.class, key);
	}
}
