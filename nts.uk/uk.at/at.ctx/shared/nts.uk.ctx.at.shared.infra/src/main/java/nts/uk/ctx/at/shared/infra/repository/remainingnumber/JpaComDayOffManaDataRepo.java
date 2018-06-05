package nts.uk.ctx.at.shared.infra.repository.remainingnumber;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.subhdmana.KrcmtComDayoffMaData;

@Stateless
public class JpaComDayOffManaDataRepo extends JpaRepository implements ComDayOffManaDataRepository {

	private String GET_BYSID = "SELECT a FROM KrcmtComDayoffMaData a WHERE a.sID = :employeeId AND a.cID = :cid";
	
	private String GET_BY_REDAY = String.join(" ", GET_BYSID, " AND a.remainDays > 0");

	private String GET_BYSID_WITHREDAY = String.join(" ", GET_BYSID, " AND a.remainDays > 0 OR a.comDayOffID IN  (SELECT c.krcmtLeaveDayOffManaPK.comDayOffID FROM KrcmtLeaveDayOffMana c "
					+ "INNER JOIN KrcmtLeaveManaData b ON b.leaveID = c.krcmtLeaveDayOffManaPK.leaveID WHERE b.cID = :cid AND"
					+ " b.sID =:employeeId AND b.subHDAtr !=:subHDAtr)");

	private String GET_BYCOMDAYOFFID = String.join(" ", GET_BYSID, " AND a.comDayOffID IN (SELECT b.krcmtLeaveDayOffManaPK.comDayOffID FROM KrcmtLeaveDayOffMana b WHERE b.krcmtLeaveDayOffManaPK.leaveID = :leaveID)");

	private String GET_BYSID_BY_HOLIDAYDATECONDITION = "SELECT c FROM KrcmtComDayoffMaData c WHERE c.sID = :employeeId AND c.cID = :cid AND c.dayOff = :dateSubHoliday";
	
	
	private String GET_BY_LISTID = " SELECT c FROM KrcmtComDayoffMaData c WHERE c.comDayOffID IN :comDayOffIDs";
	

	@Override
	public List<CompensatoryDayOffManaData> getBySidWithReDay(String cid, String sid) {
		List<KrcmtComDayoffMaData> list = this.queryProxy().query(GET_BYSID_WITHREDAY, KrcmtComDayoffMaData.class)
				.setParameter("employeeId", sid).setParameter("cid", cid).setParameter("subHDAtr", 0).getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<CompensatoryDayOffManaData> getBySid(String cid, String sid) {
		List<KrcmtComDayoffMaData> list = this.queryProxy().query(GET_BYSID, KrcmtComDayoffMaData.class)
				.setParameter("employeeId", sid).setParameter("cid", cid).getList();

		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public void update(CompensatoryDayOffManaData domain) {
		this.commandProxy().update(toEnitty(domain));
	}

	@Override
	public void deleteByComDayOffId(String comDayOffID) {
		KrcmtComDayoffMaData entity = this.getEntityManager().find(KrcmtComDayoffMaData.class, comDayOffID);
		if(Objects.isNull(entity)){
			throw new BusinessException("Msg_198");
		}
		this.commandProxy().remove(entity);
	}

	/**
	 * Convert to domain
	 * 
	 * @param entity
	 * @return
	 */
	private CompensatoryDayOffManaData toDomain(KrcmtComDayoffMaData entity) {
		return new CompensatoryDayOffManaData(entity.comDayOffID, entity.cID, entity.sID, entity.unknownDate,
				entity.dayOff, entity.requiredDays, entity.requiredTimes, entity.remainDays, entity.remainTimes);
	}

	private KrcmtComDayoffMaData toEnitty(CompensatoryDayOffManaData domain) {
		KrcmtComDayoffMaData entity = new KrcmtComDayoffMaData();
		entity.comDayOffID = domain.getComDayOffID();
		entity.cID = domain.getCID();
		entity.sID = domain.getSID();
		entity.unknownDate = domain.getDayOffDate().isUnknownDate();
		entity.dayOff = domain.getDayOffDate().getDayoffDate().isPresent()
				? domain.getDayOffDate().getDayoffDate().get() : null;
		entity.requiredDays = domain.getRequireDays().v();
		entity.requiredTimes = domain.getRequiredTimes().v();
		entity.remainDays = domain.getRemainDays().v();
		entity.remainTimes = domain.getRemainTimes().v();
		return entity;
	}

	@Override
	public void create(CompensatoryDayOffManaData domain) {
		this.commandProxy().insert(toEnitty(domain));
	}

	@Override
	public List<CompensatoryDayOffManaData> getBySidComDayOffIdWithReDay(String cid, String sid, String leaveId) {
		List<KrcmtComDayoffMaData> list = this.queryProxy().query(GET_BYCOMDAYOFFID, KrcmtComDayoffMaData.class)
				.setParameter("employeeId", sid)
				.setParameter("cid", cid)
				.setParameter("leaveID", leaveId)
				.getList();
		return list.stream().map(i->toDomain(i)).collect(Collectors.toList());
	}


	public List<CompensatoryDayOffManaData> getByDateCondition(String cid, String sid,
			GeneralDate startDate, GeneralDate endDate) {
		String query = "";
		List<KrcmtComDayoffMaData> list = new ArrayList<>();
		if (!Objects.isNull(startDate) && !Objects.isNull(endDate)) {
			query = "SELECT a FROM KrcmtComDayoffMaData a WHERE a.cID = :cid AND"
					+ " a.sID =:employeeId AND a.dayOff >= :startDate AND a.dayOff <= :endDate OR "
					+ " a.comDayOffID IN  (SELECT c.krcmtLeaveDayOffManaPK.comDayOffID FROM KrcmtLeaveDayOffMana c "
					+ "INNER JOIN KrcmtLeaveManaData b ON b.leaveID = c.krcmtLeaveDayOffManaPK.leaveID WHERE b.cID = :cid AND"
					+ " b.sID =:employeeId AND b.dayOff >= :startDate AND b.dayOff <= :endDate)";
			list = this.queryProxy().query(query, KrcmtComDayoffMaData.class).setParameter("employeeId", sid)
					.setParameter("cid", cid).setParameter("startDate", startDate).setParameter("endDate", endDate)
					.getList();
		} else if (!Objects.isNull(startDate)) {
			query = "SELECT a FROM KrcmtComDayoffMaData a WHERE a.cID = :cid AND"
					+ " a.sID =:employeeId AND a.dayOff >= :startDate OR "
					+ " a.comDayOffID IN  (SELECT c.krcmtLeaveDayOffManaPK.comDayOffID FROM KrcmtLeaveManaData b JOIN KrcmtLeaveDayOffMana c ON b.leaveID = c.krcmtLeaveDayOffManaPK.leaveID WHERE b.cID = :cid AND b.sID =:employeeId AND b.dayOff >= :startDate)";
			list = this.queryProxy().query(query, KrcmtComDayoffMaData.class).setParameter("employeeId", sid)
					.setParameter("cid", cid).setParameter("startDate", startDate).getList();
		} else if (!Objects.isNull(endDate)) {
			query = "SELECT a FROM KrcmtComDayoffMaData a WHERE a.cID = :cid AND"
					+ " a.sID =:employeeId AND a.dayOff <= :endDate OR "
					+ " a.comDayOffID IN  (SELECT c.krcmtLeaveDayOffManaPK.comDayOffID FROM KrcmtLeaveManaData b JOIN KrcmtLeaveDayOffMana c ON b.leaveID = c.krcmtLeaveDayOffManaPK.leaveID WHERE b.cID = :cid AND b.sID =:employeeId AND b.dayOff <= :endDate)";
			list = this.queryProxy().query(query, KrcmtComDayoffMaData.class).setParameter("employeeId", sid)
					.setParameter("cid", cid).setParameter("endDate", endDate).getList();
		} else {
			query = "SELECT a FROM KrcmtComDayoffMaData a WHERE a.cID = :cid AND"
					+ " a.sID =:employeeId OR "
					+ " a.comDayOffID IN  (SELECT c.krcmtLeaveDayOffManaPK.comDayOffID FROM KrcmtLeaveManaData b JOIN KrcmtLeaveDayOffMana c ON b.leaveID = c.krcmtLeaveDayOffManaPK.leaveID WHERE b.cID = :cid AND b.sID =:employeeId )";
			list = this.queryProxy().query(query, KrcmtComDayoffMaData.class).setParameter("employeeId", sid)
					.setParameter("cid", cid).getList();
		}
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.
	 * ComDayOffManaDataRepository#getBySidWithHolidayDateCondition(java.lang.
	 * String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<CompensatoryDayOffManaData> getBySidWithHolidayDateCondition(String cid, String sid,
			GeneralDate dateSubHoliday) {
		List<KrcmtComDayoffMaData> list = this.queryProxy()
				.query(GET_BYSID_BY_HOLIDAYDATECONDITION, KrcmtComDayoffMaData.class).setParameter("employeeId", sid)
				.setParameter("cid", cid).setParameter("dateSubHoliday", dateSubHoliday).getList();

		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public void updateReDayByComDayId(List<String> comDayIds) {
		List<KrcmtComDayoffMaData> KrcmtComDayoffMaData = this.queryProxy()
				.query(GET_BY_LISTID, KrcmtComDayoffMaData.class)
				.setParameter("comDayOffIDs",comDayIds)
				.getList();
		for(KrcmtComDayoffMaData busItem: KrcmtComDayoffMaData){
			busItem.remainDays =  new Double(0);
		}
		this.commandProxy().updateAll(KrcmtComDayoffMaData);
	}
	
	@Override
	public void updateReDayReqByComDayId(List<String> comDayIds) {
		List<KrcmtComDayoffMaData> KrcmtComDayoffMaData = this.queryProxy()
				.query(GET_BY_LISTID, KrcmtComDayoffMaData.class)
				.setParameter("comDayOffIDs",comDayIds)
				.getList();
		for(KrcmtComDayoffMaData busItem: KrcmtComDayoffMaData){
			busItem.remainDays =  busItem.requiredDays;
		}
		this.commandProxy().updateAll(KrcmtComDayoffMaData);
	}

	@Override
	public List<CompensatoryDayOffManaData> getByReDay(String cid, String sid) {
		List<KrcmtComDayoffMaData> list = this.queryProxy().query(GET_BY_REDAY, KrcmtComDayoffMaData.class)
				.setParameter("employeeId", sid).setParameter("cid", cid).getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}
	
}
