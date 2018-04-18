package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.subhdmana.KrcmtLeaveManaData;

@Stateless
public class JpaLeaveManaDataRepo extends JpaRepository implements LeaveManaDataRepository {

	private String QUERY_BYSID = "SELECT l FROM KrcmtLeaveManaData l WHERE l.cID = :cid AND l.sID =:employeeId ";
	
	private String QUERY_BYSIDWITHSUBHDATR = String.join(" ", QUERY_BYSID,"AND l.subHDAtr =:subHDAtr");
	
	@Override
	public List<LeaveManagementData> getBySidWithsubHDAtr(String cid, String sid, int state) {
		List<KrcmtLeaveManaData> listListMana = this.queryProxy().query(QUERY_BYSIDWITHSUBHDATR,KrcmtLeaveManaData.class)
				.setParameter("cid", cid)
				.setParameter("employeeId", sid)
				.setParameter("subHDAtr", state)
				.getList();
		return listListMana.stream().map(i->toDomain(i)).collect(Collectors.toList());
	}
	
	@Override
	public List<LeaveManagementData> getBySid(String cid, String sid) {
		List<KrcmtLeaveManaData> listListMana = this.queryProxy().query(QUERY_BYSID,KrcmtLeaveManaData.class)
				.setParameter("cid", cid)
				.setParameter("employeeId", sid)
				.getList();
		return listListMana.stream().map(i->toDomain(i)).collect(Collectors.toList());
	}
	
	
	/**
	 * Convert to domain
	 * @param entity
	 * @return
	 */
	private LeaveManagementData toDomain(KrcmtLeaveManaData entity){
		return new LeaveManagementData(entity.ID, entity.cID, entity.sID, entity.unknownDate, entity.dayOff, entity.expiredDate, entity.occurredDays, entity.occurredTimes, entity.unUsedDays, entity.unUsedTimes, entity.subHDAtr, entity.fullDayTime, entity.halfDayTime);
	}

	private KrcmtLeaveManaData toEntity(LeaveManagementData domain){
		KrcmtLeaveManaData entity = new KrcmtLeaveManaData();
		entity.ID = domain.getID();
		entity.cID = domain.getCID();
		entity.sID = domain.getSID();
		entity.unknownDate = domain.getComDayOffDate().isUnknownDate();
		entity.dayOff = domain.getComDayOffDate().getDayoffDate().isPresent() ? domain.getComDayOffDate().getDayoffDate().get() : null;
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
}
