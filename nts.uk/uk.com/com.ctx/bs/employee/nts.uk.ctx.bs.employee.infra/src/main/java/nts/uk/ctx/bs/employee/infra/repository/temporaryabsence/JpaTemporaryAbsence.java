package nts.uk.ctx.bs.employee.infra.repository.temporaryabsence;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
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

	/**
	 * Covert from domain to entity
	 * @param domain
	 * @return
	 */
	public BsymtTempAbsHisItem toEntity(TempAbsenceHisItem domain){
		switch (domain.getLeaveHolidayType()) {
		case LEAVE_OF_ABSENCE:
			Leave leave = (Leave)domain;
			return new BsymtTempAbsHisItem(leave.getHistoryId(), leave.getEmployeeId(), leave.getLeaveHolidayType().value, leave.getRemarks().v(), 
					leave.getSoInsPayCategory(), null, null, null, null, null, null, null);
		case MIDWEEK_CLOSURE:
			MidweekClosure midweek = (MidweekClosure)domain;
			return new BsymtTempAbsHisItem(midweek.getHistoryId(), midweek.getEmployeeId(), midweek.getLeaveHolidayType().value, midweek.getRemarks().v(), 
					midweek.getSoInsPayCategory(), midweek.getMultiple()? 1:0, null, null, null, null, null, null);
		case AFTER_CHILDBIRTH:
			AfterChildbirth childBirth = (AfterChildbirth) domain;
			return new BsymtTempAbsHisItem(childBirth.getHistoryId(), childBirth.getEmployeeId(), childBirth.getLeaveHolidayType().value, childBirth.getRemarks().v(), 
					childBirth.getSoInsPayCategory(), null, childBirth.getFamilyMemberId(), null, null, null, null, null);
		case CHILD_CARE_NURSING:
			ChildCareHoliday childCare = (ChildCareHoliday)domain;
			return new BsymtTempAbsHisItem(childCare.getHistoryId(), childCare.getEmployeeId(), childCare.getLeaveHolidayType().value, childCare.getRemarks().v(), 
					childCare.getSoInsPayCategory(), null, childCare.getFamilyMemberId(), childCare.getSameFamily()?1:0, childCare.getChildType(), 
					childCare.getCreateDate(), childCare.getSpouseIsLeave()? 1:0, null);
		case NURSING_CARE_LEAVE:
			CareHoliday careLeave = (CareHoliday) domain;
			return new BsymtTempAbsHisItem(careLeave.getHistoryId(),careLeave.getEmployeeId(),careLeave.getLeaveHolidayType().value,careLeave.getRemarks().v(),
					careLeave.getSoInsPayCategory(),null,careLeave.getFamilyMemberId(),careLeave.getSameFamily()?1:0,null,null, null,careLeave.getSameFamilyDays());
		case SICK_LEAVE:
			SickLeave sickLeave = (SickLeave)domain;
			return new BsymtTempAbsHisItem(sickLeave.getHistoryId(), sickLeave.getEmployeeId(), sickLeave.getLeaveHolidayType().value, sickLeave.getRemarks().v(), 
					sickLeave.getSoInsPayCategory(), null, null, null, null, null, null, null);
		case ANY_LEAVE:
			AnyLeave anyLeave = (AnyLeave)domain;
			return new BsymtTempAbsHisItem(anyLeave.getHistoryId(), anyLeave.getEmployeeId(), anyLeave.getLeaveHolidayType().value, anyLeave.getRemarks().v(), 
					anyLeave.getSoInsPayCategory(), null, null, null, null, null, null, null);
		default:
			return null;
		}
		
	}
	
	/**
	 * Update entity from domain
	 * @param domain
	 * @return
	 */
	public void updateEntity(TempAbsenceHisItem domain, BsymtTempAbsHisItem entity){
		
		entity.histId = domain.getHistoryId();
		entity.sid = domain.getEmployeeId();
		entity.leaveHolidayAtr = domain.getLeaveHolidayType().value;
		entity.remarks = domain.getRemarks().v();
		entity.soInsPayCategory = domain.getSoInsPayCategory();
		
		switch (domain.getLeaveHolidayType()) {
		case LEAVE_OF_ABSENCE:
			break;
		case MIDWEEK_CLOSURE:
			MidweekClosure midweek = (MidweekClosure)domain;
			entity.multiple = midweek.getMultiple() ? 1 : 0;
			break;
		case AFTER_CHILDBIRTH:
			AfterChildbirth childBirth = (AfterChildbirth) domain;
			entity.familyMemberId = childBirth.getFamilyMemberId();
			break;
		case CHILD_CARE_NURSING:
			ChildCareHoliday childCare = (ChildCareHoliday)domain;
			entity.sameFamily = childCare.getSameFamily() ? 1: 0;
			entity.childType = childCare.getChildType();
			entity.familyMemberId = childCare.getFamilyMemberId();
			entity.createDate = childCare.getCreateDate();
			entity.spouseIsLeave = childCare.getSpouseIsLeave() ? 1: 0;
			break;
		case NURSING_CARE_LEAVE:
			CareHoliday careLeave = (CareHoliday) domain;
			entity.sameFamily  = careLeave.getSameFamily() ? 1: 0;
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
	@Override
	public void addTemporaryAbsence(TempAbsenceHisItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void updateTemporaryAbsence(TempAbsenceHisItem domain) {
		Optional<BsymtTempAbsHisItem> tempAbs = this.queryProxy().find(domain.getHistoryId(), BsymtTempAbsHisItem.class);
		
		if (!tempAbs.isPresent()){
			throw new RuntimeException("invalid TempAbsenceHisItem");
		}
		updateEntity(domain, tempAbs.get());
		
		this.commandProxy().update(tempAbs.get());
	}

	@Override
	public void deleteTemporaryAbsence(String histId) {
		Optional<BsymtTempAbsHisItem> tempAbs = this.queryProxy().find(histId, BsymtTempAbsHisItem.class);
		
		if (!tempAbs.isPresent()){
			throw new RuntimeException("invalid TempAbsenceHisItem");
		}
		
		this.commandProxy().remove(BsymtTempAbsHisItem.class, histId);
	}

}
