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
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.AfterChildbirth;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.CareHoliday;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.ChildCareHoliday;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.Leave;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.LeaveHolidayState;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.MidweekClosure;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTempAbsenceHist;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTemporaryAbsence;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTemporaryAbsencePK;

@Stateless
@Transactional
public class JpaTemporaryAbsence extends JpaRepository implements TemporaryAbsenceRepository {

	private static final String SELECT_NO_WHERE = "SELECT c.bsymtTemporaryAbsencePK.leaveHolidayId,"
			+ " c.sid, c.leaveHolidayAtr, c.histId, h.strD, h.endD, c.reason, c.familyMemberId,"
			+ " c.birthday, c.multiple FROM BsymtTemporaryAbsence c "
			+ " JOIN BsymtTempAbsenceHist h on c.histId = h.historyId";

	private static final String SELECT_BY_SID_AND_REFERENCEDATE = SELECT_NO_WHERE + " WHERE c.sid = :sid"
			+ " AND h.strD <= :referenceDate  AND h.endD >= :referenceDate ";

	private static final String SELECT_BY_TEMP_ABSENCE_ID = SELECT_NO_WHERE
			+ " c WHERE c.bsymtTemporaryAbsencePK.leaveHolidayId = :tempAbsenceId";

	private static final String GETLIST_BY_SID = SELECT_NO_WHERE + " WHERE c.sid = :sid";

	private TemporaryAbsence toTemporaryAbsence(Object[] entity) {
		TemporaryAbsence temporaryAbsence = TemporaryAbsence.createSimpleFromJavaType(
				String.valueOf(entity[1].toString()), String.valueOf(entity[0].toString()),
				Integer.valueOf(entity[2].toString()), String.valueOf(entity[3].toString()),
				entity[4] == null ? null : GeneralDate.fromString(entity[4].toString(), "yyyy-MM-dd"),
				GeneralDate.fromString(entity[5].toString(), "yyyy-MM-dd"),
				entity[6] == null ? null : String.valueOf(entity[6].toString()),
				entity[7] == null ? null : String.valueOf(entity[7].toString()),
				entity[8] == null ? null : GeneralDate.fromString(entity[8].toString(), "yyyy-MM-dd"),
				entity[9] == null ? null : Integer.valueOf(entity[9].toString()));
		return temporaryAbsence;
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
		LeaveHolidayState state = domain.getLeaveHolidayState();
		switch (state.getTempAbsenceType()) {
		case LEAVE_OF_ABSENCE:
			Leave leave = (Leave) state;
			return new BsymtTemporaryAbsence(key, domain.getEmployeeId(), domain.getDateHistoryItem().identifier(),
					state.getTempAbsenceType().value, leave.getReason());
		case MIDWEEK_CLOSURE:
			MidweekClosure midweekClosure = (MidweekClosure) state;
			return new BsymtTemporaryAbsence(key, domain.getEmployeeId(), domain.getDateHistoryItem().identifier(),
					state.getTempAbsenceType().value, midweekClosure.getBirthDate(), midweekClosure.getMultiple());
		case AFTER_CHILDBIRTH:
			AfterChildbirth afterChildbirth = (AfterChildbirth) state;
			return new BsymtTemporaryAbsence(key, domain.getEmployeeId(), domain.getDateHistoryItem().identifier(),
					state.getTempAbsenceType().value, afterChildbirth.getFamilyMemberId());
		case CHILD_CARE_NURSING:
			ChildCareHoliday childCareHoliday = (ChildCareHoliday) state;
			return new BsymtTemporaryAbsence(key, domain.getEmployeeId(), domain.getDateHistoryItem().identifier(),
					state.getTempAbsenceType().value, childCareHoliday.getFamilyMemberId());
		case NURSING_CARE_LEAVE:
			CareHoliday careHoliday = (CareHoliday) state;
			return new BsymtTemporaryAbsence(key, domain.getEmployeeId(), domain.getDateHistoryItem().identifier(),
					state.getTempAbsenceType().value, careHoliday.getFamilyMemberId());
		default:
			return null;
		}
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
		entity.histId = domain.getDateHistoryItem().identifier();
		// check with state
		LeaveHolidayState state = domain.getLeaveHolidayState();
		entity.leaveHolidayAtr = state.getTempAbsenceType().value;
		switch (state.getTempAbsenceType()) {
		case LEAVE_OF_ABSENCE:
			Leave leave = (Leave) state;
			entity.reason = leave.getReason();
			break;
		case MIDWEEK_CLOSURE:
			MidweekClosure midweekClosure = (MidweekClosure) state;
			entity.birthday = midweekClosure.getBirthDate();
			entity.multiple = midweekClosure.getMultiple();
			break;
		case AFTER_CHILDBIRTH:
			AfterChildbirth afterChildbirth = (AfterChildbirth) state;
			entity.familyMemberId = afterChildbirth.getFamilyMemberId();
			break;
		case CHILD_CARE_NURSING:
			ChildCareHoliday childCareHoliday = (ChildCareHoliday) state;
			entity.familyMemberId = childCareHoliday.getFamilyMemberId();
			break;
		case NURSING_CARE_LEAVE:
			CareHoliday careHoliday = (CareHoliday) state;
			entity.familyMemberId = careHoliday.getFamilyMemberId();
			break;
		}
		
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
