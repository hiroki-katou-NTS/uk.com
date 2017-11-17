package nts.uk.ctx.bs.employee.infra.repository.temporaryabsence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTemporaryAbsence;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTempAbsenceHist;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTemporaryAbsencePK;

@Stateless
@Transactional
public class JpaTemporaryAbsence extends JpaRepository implements TemporaryAbsenceRepository {

	private static final String SELECT_NO_WHERE = "SELECT c.bsymtTemporaryAbsencePK.leaveHolidayId,"
			+ " c.sid, c.leaveHolidayAtr, c.histId, h.strD, h.endD, c.reason, c.familyMemberId,"
			+ " c.birthday, c.multiple FROM BsymtTemporaryAbsence c "
			+ " JOIN BsymtTempAbsenceHist h on c.histId = h.historyId";

	private static final String SELECT_BY_SID_AND_REFERENCEDATE = SELECT_NO_WHERE + " WHERE c.sid = :sid"
			+ " AND h.startDate <= :referenceDate  AND h.endDate >= :referenceDate ";

	private static final String SELECT_BY_TEMP_ABSENCE_ID = SELECT_NO_WHERE
			+ " c WHERE c.bsymtTemporaryAbsencePK.leaveHolidayId = :tempAbsenceId";

	private static final String GETLIST_BY_SID = SELECT_NO_WHERE + " WHERE c.sid = :sid";

	private TemporaryAbsence toTemporaryAbsence(Object[] entity) {
		boolean p = entity[4].toString().indexOf("-") > 0;

		return TemporaryAbsence.createSimpleFromJavaType(String.valueOf(entity[1]), String.valueOf(entity[0]),
				Integer.valueOf(entity[2].toString()), String.valueOf(entity[3]),
				entity[4] == null ? null : GeneralDate.fromString(entity[4].toString(), p ? "yyyy-MM-dd" : "yyyy/MM/dd"),
				GeneralDate.fromString(entity[5].toString(), p ? "yyyy-MM-dd" : "yyyy/MM/dd"),
				entity[6] == null ? null : String.valueOf(entity[6]),
				entity[7] == null ? null : String.valueOf(entity[7]),
				entity[8] == null ? null : GeneralDate.fromString(entity[8].toString(), p ? "yyyy-MM-dd" : "yyyy/MM/dd"),
				entity[9] == null ? null : Integer.valueOf(entity[9].toString()));
	}

	private List<TemporaryAbsence> toListTemporaryAbsence(List<Object[]> listEntity) {
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
		Object[] entity = this.queryProxy().query(SELECT_BY_SID_AND_REFERENCEDATE, Object[].class)
				.setParameter("sid", sid).setParameter("referenceDate", referenceDate).getSingleOrNull();
		if (entity != null) {
			TemporaryAbsence temporaryAbsence = toTemporaryAbsence(entity);
			return Optional.of(temporaryAbsence);
		} else {
			return Optional.empty();
		}

	}

	@Override
	public Optional<TemporaryAbsence> getByTempAbsenceId(String tempAbsenceId) {
		return this.queryProxy().query(SELECT_BY_TEMP_ABSENCE_ID, Object[].class)
				.setParameter("tempAbsenceId", tempAbsenceId).getSingle(x -> toTemporaryAbsence(x));
	}

	// laitv
	@Override
	public List<TemporaryAbsence> getListBySid(String sid) {
		List<Object[]> listEntity = this.queryProxy().query(GETLIST_BY_SID, Object[].class).setParameter("sid", sid)
				.getList();

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
		return new BsymtTemporaryAbsence(key, domain.getEmployeeId(), domain.getDateHistoryItem().identifier(),
				domain.getTempAbsenceType().value, domain.getTempAbsenceReason(), domain.getFamilyMemberId(),
				domain.getBirthDate(), domain.getMulPregnancySegment());
	}

	private BsymtTempAbsenceHist toEntityTempAbsenceHist(TemporaryAbsence domain) {
		return new BsymtTempAbsenceHist(domain.getDateHistoryItem().identifier(), domain.getDateHistoryItem().start(),
				domain.getDateHistoryItem().end());
	}

	/**
	 * Update entity BsymtTemporaryAbsence
	 * 
	 * @param domain
	 * @param entity
	 */
	private void updateEntityBsymtTemporaryAbsence(TemporaryAbsence domain, BsymtTemporaryAbsence entity) {
		entity.sid = domain.getEmployeeId();
		entity.leaveHolidayAtr = domain.getTempAbsenceType().value;
		entity.histId = domain.getDateHistoryItem().identifier();
		entity.reason = domain.getTempAbsenceReason();
		entity.familyMemberId = domain.getFamilyMemberId();
		entity.birthday = domain.getBirthDate();
		entity.multiple = domain.getMulPregnancySegment();
	}

	private void updateEntityBsymtTempAbsenceHist(TemporaryAbsence domain, BsymtTempAbsenceHist entity) {
		entity.historyId = domain.getDateHistoryItem().identifier();
		entity.strD = domain.getDateHistoryItem().start();
		entity.endD = domain.getDateHistoryItem().end();
	}

	/**
	 * ドメインモデル「休職休業」を新規登録する
	 * 
	 * @param domain
	 */
	@Override
	public void addTemporaryAbsence(TemporaryAbsence domain) {
		this.commandProxy().insert(toEntity(domain));
		this.commandProxy().insert(toEntityTempAbsenceHist(domain));
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
		Optional<BsymtTempAbsenceHist> existItemHist = this.queryProxy().find(domain.getDateHistoryItem().identifier(),
				BsymtTempAbsenceHist.class);
		if (!existItem.isPresent()) {
			return;
		}
		// Update entity BsymtTemporaryAbsence
		updateEntityBsymtTemporaryAbsence(domain, existItem.get());
		updateEntityBsymtTempAbsenceHist(domain, existItemHist.get());
		// Update table
		this.commandProxy().update(existItem.get());
		this.commandProxy().update(existItemHist.get());

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
