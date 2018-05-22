package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.subhdmana.KrcmtLeaveManaData;

@Stateless
public class JpaLeaveManaDataRepo extends JpaRepository implements LeaveManaDataRepository {

	private String QUERY_BYSID = "SELECT l FROM KrcmtLeaveManaData l WHERE l.cID = :cid AND l.sID =:employeeId ";

	private String QUERY_BYSIDWITHSUBHDATR = String.join(" ", QUERY_BYSID, "AND l.subHDAtr =:subHDAtr");

	private String QUERY_BYSIDANDDATECONDITION = String.join(" ", QUERY_BYSIDWITHSUBHDATR,
			"AND l.dayOff >= :startDate AND l.dayOff <= :endDate");

	private String QUERY_BYSIDANDHOLIDAYDATECONDITION = "SELECT l FROM KrcmtLeaveManaData l WHERE l.cID = :cid AND l.sID =:employeeId AND l.dayOff = :dateHoliday";
	
	private String QUERY_BYSID_AND_NOT_UNUSED = String.join(" ", QUERY_BYSID, "AND l.subHDAtr !=:subHDAtr");
	
	@Override
	public List<LeaveManagementData> getBySidWithsubHDAtr(String cid, String sid, int state) {
		List<KrcmtLeaveManaData> listListMana = this.queryProxy()
				.query(QUERY_BYSIDWITHSUBHDATR, KrcmtLeaveManaData.class).setParameter("cid", cid)
				.setParameter("employeeId", sid).setParameter("subHDAtr", state).getList();
		return listListMana.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<LeaveManagementData> getBySid(String cid, String sid) {
		List<KrcmtLeaveManaData> listListMana = this.queryProxy().query(QUERY_BYSID, KrcmtLeaveManaData.class)
				.setParameter("cid", cid).setParameter("employeeId", sid).getList();
		return listListMana.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	/**
	 * Convert to domain
	 * 
	 * @param entity
	 * @return
	 */
	private LeaveManagementData toDomain(KrcmtLeaveManaData entity) {
		return new LeaveManagementData(entity.ID, entity.cID, entity.sID, entity.unknownDate, entity.dayOff,
				entity.expiredDate, entity.occurredDays, entity.occurredTimes, entity.unUsedDays, entity.unUsedTimes,
				entity.subHDAtr, entity.fullDayTime, entity.halfDayTime);
	}

	private KrcmtLeaveManaData toEntity(LeaveManagementData domain) {
		KrcmtLeaveManaData entity = new KrcmtLeaveManaData();
		entity.ID = domain.getID();
		entity.cID = domain.getCID();
		entity.sID = domain.getSID();
		entity.unknownDate = domain.getComDayOffDate().isUnknownDate();
		entity.dayOff = domain.getComDayOffDate().getDayoffDate().isPresent()
				? domain.getComDayOffDate().getDayoffDate().get() : null;
		entity.expiredDate = domain.getExpiredDate();
		entity.occurredDays = domain.getOccurredDays().v();
		entity.occurredTimes = domain.getOccurredTimes().v();
		entity.unUsedDays = domain.getUnUsedDays().v();
		entity.unUsedTimes = domain.getUnUsedTimes().v();
		entity.subHDAtr = domain.getSubHDAtr().value;
		entity.fullDayTime = domain.getFullDayTime().v();
		entity.halfDayTime = domain.getHalfDayTime().v();
		return entity;
	}

	@Override
	public void create(LeaveManagementData domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public List<LeaveManagementData> getBySidWithsubHDAtrAndDateCondition(String cid, String sid, GeneralDate startDate,
			GeneralDate endDate) {
		List<KrcmtLeaveManaData> listLeaveData = this.queryProxy()
				.query(QUERY_BYSIDANDDATECONDITION, KrcmtLeaveManaData.class).setParameter("cid", cid)
				.setParameter("employeeId", sid).setParameter("startDate", startDate).setParameter("endDate", endDate)
				.getList();
		return listLeaveData.stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.
	 * LeaveManaDataRepository#getBySidWithHolidayDate(java.lang.String,
	 * java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<LeaveManagementData> getBySidWithHolidayDate(String cid, String sid, GeneralDate dateHoliday) {
		List<KrcmtLeaveManaData> listLeaveData = this.queryProxy()
				.query(QUERY_BYSIDANDHOLIDAYDATECONDITION, KrcmtLeaveManaData.class).setParameter("cid", cid)
				.setParameter("employeeId", sid).setParameter("dateHoliday", dateHoliday).getList();
		return listLeaveData.stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}
	
	@Override
	public List<LeaveManagementData> getBySidNotUnUsed(String cid, String sid) {
		List<KrcmtLeaveManaData> listListMana = this.queryProxy()
				.query(QUERY_BYSID_AND_NOT_UNUSED, KrcmtLeaveManaData.class).setParameter("cid", cid)
				.setParameter("employeeId", sid).setParameter("subHDAtr", 0).getList();
		return listListMana.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}
}
