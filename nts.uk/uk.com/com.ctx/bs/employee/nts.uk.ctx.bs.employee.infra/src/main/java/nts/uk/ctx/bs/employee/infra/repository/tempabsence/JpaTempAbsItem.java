package nts.uk.ctx.bs.employee.infra.repository.tempabsence;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsItemRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.AfterChildbirth;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.AnyLeave;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.CareHoliday;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.ChildCareHoliday;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.Leave;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.MidweekClosure;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.SickLeave;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTempAbsHisItem;

@Stateless
@Transactional
public class JpaTempAbsItem extends JpaRepository implements TempAbsItemRepository {
	
	private final String GET_BY_SID_DATE = "select hi from BsymtTempAbsHisItem hi"
			+ " inner join BsymtTempAbsHistory h on hi.histId = h.histId"
			+ " where hi.sid = :sid and h.startDate <= :referDate and h.endDate >= :referDate";
	
	private final String GET_BY_HID = "select hi from BsymtTempAbsHisItem hi where hi.histId = :histId";
	
	@Override
	public Optional<TempAbsenceHisItem> getItemByEmpIdAndReferDate(String employeeId, GeneralDate referenceDate) {
		Optional<BsymtTempAbsHisItem> option = this.queryProxy().query(GET_BY_SID_DATE, BsymtTempAbsHisItem.class)
				.setParameter("sid", employeeId).setParameter("referDate", referenceDate).getSingle();
		if (option.isPresent()) {
			return Optional.of(toDomain(option.get()));
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<TempAbsenceHisItem> getItemByHitoryID(String historyId) {
		Optional<BsymtTempAbsHisItem> option = this.queryProxy().query(GET_BY_HID, BsymtTempAbsHisItem.class)
				.setParameter("histId", historyId).getSingle();
		if (option.isPresent()) {
			return Optional.of(toDomain(option.get()));
		}
		return Optional.empty();
	}
	
	@Override
	public List<TempAbsenceHisItem> getListItemByEmpId(String employeeId) {
		// TODO
		return null;
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
	public void add(TempAbsenceHisItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(TempAbsenceHisItem domain) {
		Optional<BsymtTempAbsHisItem> tempAbs = this.queryProxy().find(domain.getHistoryId(),
				BsymtTempAbsHisItem.class);

		if (!tempAbs.isPresent()) {
			throw new RuntimeException("invalid TempAbsenceHisItem");
		}
		updateEntity(domain, tempAbs.get());

		this.commandProxy().update(tempAbs.get());
	}

	@Override
	public void delete(String histId) {
		Optional<BsymtTempAbsHisItem> tempAbs = this.queryProxy().find(histId, BsymtTempAbsHisItem.class);

		if (!tempAbs.isPresent()) {
			throw new RuntimeException("invalid TempAbsenceHisItem");
		}

		this.commandProxy().remove(BsymtTempAbsHisItem.class, histId);
	}

	/**
	 * Covert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private BsymtTempAbsHisItem toEntity(TempAbsenceHisItem domain) {
		switch (domain.getLeaveHolidayType()) {
		case LEAVE_OF_ABSENCE:
			Leave leave = (Leave) domain;
			return new BsymtTempAbsHisItem(leave.getHistoryId(), leave.getEmployeeId(),
					leave.getLeaveHolidayType().value, leave.getRemarks().v(), leave.getSoInsPayCategory());
		case MIDWEEK_CLOSURE:
			MidweekClosure midweek = (MidweekClosure) domain;
			return new BsymtTempAbsHisItem(midweek.getHistoryId(), midweek.getEmployeeId(),
					midweek.getLeaveHolidayType().value, midweek.getRemarks().v(), midweek.getSoInsPayCategory(),
					midweek.getMultiple());
		case AFTER_CHILDBIRTH:
			AfterChildbirth childBirth = (AfterChildbirth) domain;
			return new BsymtTempAbsHisItem(childBirth.getHistoryId(), childBirth.getEmployeeId(),
					childBirth.getLeaveHolidayType().value, childBirth.getRemarks().v(),
					childBirth.getSoInsPayCategory(),childBirth.getFamilyMemberId());
		case CHILD_CARE_NURSING:
			ChildCareHoliday childCare = (ChildCareHoliday) domain;
			return new BsymtTempAbsHisItem(childCare.getHistoryId(), childCare.getEmployeeId(),
					childCare.getLeaveHolidayType().value, childCare.getRemarks().v(), childCare.getSoInsPayCategory(), childCare.getSameFamily(), childCare.getChildType(),childCare.getFamilyMemberId(), 
					childCare.getCreateDate(), childCare.getSpouseIsLeave());
		case NURSING_CARE_LEAVE:
			CareHoliday careLeave = (CareHoliday) domain;
			return new BsymtTempAbsHisItem(careLeave.getHistoryId(), careLeave.getEmployeeId(),
					careLeave.getLeaveHolidayType().value, careLeave.getRemarks().v(), careLeave.getSoInsPayCategory(), careLeave.getSameFamily() ,
					careLeave.getSameFamilyDays(), careLeave.getFamilyMemberId());
		case SICK_LEAVE:
			SickLeave sickLeave = (SickLeave) domain;
			return new BsymtTempAbsHisItem(sickLeave.getHistoryId(), sickLeave.getEmployeeId(),
					sickLeave.getLeaveHolidayType().value, sickLeave.getRemarks().v(), sickLeave.getSoInsPayCategory());
		case ANY_LEAVE:
			AnyLeave anyLeave = (AnyLeave) domain;
			return new BsymtTempAbsHisItem(anyLeave.getHistoryId(), anyLeave.getEmployeeId(),
					anyLeave.getLeaveHolidayType().value, anyLeave.getRemarks().v(), anyLeave.getSoInsPayCategory());
		default:
			return null;
		}

	}

	/**
	 * Update entity from domain
	 * 
	 * @param domain
	 * @return
	 */
	private void updateEntity(TempAbsenceHisItem domain, BsymtTempAbsHisItem entity) {
		// Common value
		entity.histId = domain.getHistoryId();
		entity.leaveHolidayAtr = domain.getLeaveHolidayType().value;
		entity.remarks = domain.getRemarks().v();
		entity.soInsPayCategory = domain.getSoInsPayCategory();

		switch (domain.getLeaveHolidayType()) {
		case LEAVE_OF_ABSENCE:
			break;
		case MIDWEEK_CLOSURE:
			MidweekClosure midweek = (MidweekClosure) domain;
			entity.multiple = midweek.getMultiple() ? 1 : 0;
			break;
		case AFTER_CHILDBIRTH:
			AfterChildbirth childBirth = (AfterChildbirth) domain;
			entity.familyMemberId = childBirth.getFamilyMemberId();
			break;
		case CHILD_CARE_NURSING:
			ChildCareHoliday childCare = (ChildCareHoliday) domain;
			entity.sameFamily = childCare.getSameFamily() ? 1 : 0;
			entity.childType = childCare.getChildType();
			entity.familyMemberId = childCare.getFamilyMemberId();
			entity.createDate = childCare.getCreateDate();
			entity.spouseIsLeave = childCare.getSpouseIsLeave() ? 1 : 0;
			break;
		case NURSING_CARE_LEAVE:
			CareHoliday careLeave = (CareHoliday) domain;
			entity.sameFamily = careLeave.getSameFamily() ? 1 : 0;
			entity.sameFamilyDays = careLeave.getSameFamilyDays();
			entity.familyMemberId = careLeave.getFamilyMemberId();
			break;
		case SICK_LEAVE:
			break;
		case ANY_LEAVE:
			break;
		default:
		}

	}

}
