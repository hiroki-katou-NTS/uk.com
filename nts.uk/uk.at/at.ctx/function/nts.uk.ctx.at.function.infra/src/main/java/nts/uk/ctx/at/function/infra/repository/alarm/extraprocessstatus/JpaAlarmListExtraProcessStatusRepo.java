package nts.uk.ctx.at.function.infra.repository.alarm.extraprocessstatus;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.extraprocessstatus.KfnmtAlarmListExtraProcessStatus;
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class JpaAlarmListExtraProcessStatusRepo extends JpaRepository implements AlarmListExtraProcessStatusRepository {

	private static final String SELECT_ALL_ALEX_PROCESS = "SELECT c FROM KfnmtAlarmListExtraProcessStatus c ";
	
	private static final String SELECT_ALL_ALEX_PRO_BY_ID = SELECT_ALL_ALEX_PROCESS 
			+ " WHERE c.extraProcessStatusID = :extraProcessStatusID ";
	private static final String SELECT_ALL_ALEX_PROCESS_BY_CID = SELECT_ALL_ALEX_PROCESS 
			+ " WHERE c.companyID = :companyID";
	
	private static final String SELECT_ALEX_PROCESS_BY_CODE = SELECT_ALL_ALEX_PROCESS_BY_CID 
			+ " AND c.startDate = :startDate"
			+ " AND c.startTime = :startTime";
	private static final String SELECT_ALEX_PROCESS_BY_STATUS = SELECT_ALL_ALEX_PROCESS_BY_CID 
			+ " AND c.startDate = :startDate"
			+ " AND c.startTime = :startTime"
			+ " AND c.status = :status ";
	private static final String SELECT_ALEX_PROCESS_BY_END_DATE = "SELECT c FROM KfnmtAlarmListExtraProcessStatus c "
			+ "WHERE c.companyID = :companyID"
			+ " AND c.employeeID = :employeeID"
			+ " AND c.endDate IS NULL"
			+ " AND c.endTime IS NULL";

	
	@Override
	public List<AlarmListExtraProcessStatus> getAllAlListExtaProcess(String companyID) {
		return this.queryProxy().query(SELECT_ALL_ALEX_PROCESS_BY_CID,KfnmtAlarmListExtraProcessStatus.class)
				.setParameter("companyID", companyID)
				.getList(c->c.toDomain());
	}

	@Override
	public Optional<AlarmListExtraProcessStatus> getAlListExtaProcess(String companyID, GeneralDate startDate,
			int startTime) {
		return this.queryProxy().query(SELECT_ALEX_PROCESS_BY_CODE,KfnmtAlarmListExtraProcessStatus.class)
				.setParameter("companyID", companyID)
				.setParameter("startDate", startDate)
				.setParameter("startTime", startTime)
				.getSingle(c -> c.toDomain());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public String addAlListExtaProcess(AlarmListExtraProcessStatus alarmListExtraProcessStatus) {
		this.commandProxy().insert(KfnmtAlarmListExtraProcessStatus.toEntity( alarmListExtraProcessStatus));
		this.getEntityManager().flush();
		return alarmListExtraProcessStatus.getExtraProcessStatusID();
	}

	@Override
	public void updateAlListExtaProcess(AlarmListExtraProcessStatus alarmListExtraProcessStatus) {
		KfnmtAlarmListExtraProcessStatus newEntity = KfnmtAlarmListExtraProcessStatus.toEntity(alarmListExtraProcessStatus);
		KfnmtAlarmListExtraProcessStatus updateEntity = this.queryProxy().find(newEntity.getExtraProcessStatusID(), KfnmtAlarmListExtraProcessStatus.class).get();
		updateEntity.employeeID = newEntity.employeeID;
		updateEntity.endDate = newEntity.endDate;
		updateEntity.endTime = newEntity.endTime;
		updateEntity.status = newEntity.status;
		
		this.commandProxy().update(updateEntity);
		
	}

	@Override
	public void deleteAlListExtaProcess(String extraProcessStatusID) {
		this.commandProxy().remove(KfnmtAlarmListExtraProcessStatus.class,extraProcessStatusID);
		
	}

	@Override
	public Optional<AlarmListExtraProcessStatus> getAlListExtaProcessByEndDate(String companyID, String employeeID) {
		List<AlarmListExtraProcessStatus> listAlarmStatus = this.queryProxy()
				.query(SELECT_ALEX_PROCESS_BY_END_DATE, KfnmtAlarmListExtraProcessStatus.class)
				.setParameter("companyID", companyID).setParameter("employeeID", employeeID)
				.getList(c -> c.toDomain());
		if (listAlarmStatus.isEmpty())
			return Optional.ofNullable(null);
		else
			return Optional.of(listAlarmStatus.get(0));
	}

	@Override
	public Optional<AlarmListExtraProcessStatus> getAlListExtaProcessByID(String extraProcessStatusID) {
		Optional<AlarmListExtraProcessStatus> listAlarmStatus = this.queryProxy().query(SELECT_ALL_ALEX_PRO_BY_ID,KfnmtAlarmListExtraProcessStatus.class)
				.setParameter("extraProcessStatusID", extraProcessStatusID)
				.getSingle(c->c.toDomain());
		return listAlarmStatus;
	}

	@Override
	public boolean isAlListExtaProcessing(String companyId, String employeeId,int status) {
		String countProcessSql = new StringBuilder("SELECT COUNT(c.extraProcessStatusID) FROM KfnmtAlarmListExtraProcessStatus c")
											.append(" WHERE c.companyID = :companyID AND c.employeeID = :employeeID")
											.append(" AND c.status = :status").toString();
		Long count = queryProxy().query(countProcessSql, Long.class)
						.setParameter("companyID", companyId).setParameter("employeeID", employeeId)
						.setParameter("status", status).getSingleOrNull();
		return count != null && count > 0;
	}

	@Override
	public Optional<AlarmListExtraProcessStatus> getAlListExtaProcessByStatus(String companyID, GeneralDate startDate,
			int startTime, int status) {
		return this.queryProxy().query(SELECT_ALEX_PROCESS_BY_STATUS,KfnmtAlarmListExtraProcessStatus.class)
				.setParameter("companyID", companyID)
				.setParameter("startDate", startDate)
				.setParameter("startTime", startTime)
				.setParameter("status", status)
				.getSingle(c -> c.toDomain());
	}


}
