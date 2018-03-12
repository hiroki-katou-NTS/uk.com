package nts.uk.ctx.at.function.infra.repository.alarm.extraprocessstatus;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.extraprocessstatus.KfnmtAlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.infra.entity.alarm.extraprocessstatus.KfnmtAlarmListExtraProcessStatusPK;

@Stateless
public class JpaAlarmListExtraProcessStatusRepo extends JpaRepository implements AlarmListExtraProcessStatusRepository {

	private final String SELECT_ALL_ALEX_PROCESS = "SELECT c FROM KfnmtAlarmListExtraProcessStatus c ";
	private final String SELECT_ALL_ALEX_PROCESS_BY_CID = SELECT_ALL_ALEX_PROCESS 
			+ " c.kfnmtAlarmListExtraProcessStatusPK.companyID = :companyID";
	private final String SELECT_ALEX_PROCESS_BY_CODE = SELECT_ALL_ALEX_PROCESS_BY_CID 
			+ " AND c.kfnmtAlarmListExtraProcessStatusPK.startDate = :startDate"
			+ " AND c.kfnmtAlarmListExtraProcessStatusPK.startTime = :startTime";
	private final String SELECT_ALEX_PROCESS_BY_END_DATE = SELECT_ALL_ALEX_PROCESS_BY_CID 
			+ " AND c.kfnmtAlarmListExtraProcessStatusPK.endDate = :endDate"
			+ " AND c.kfnmtAlarmListExtraProcessStatusPK.endTime = :endTime";
	
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

	@Override
	public void addAlListExtaProcess(AlarmListExtraProcessStatus alarmListExtraProcessStatus) {
		this.commandProxy().insert(KfnmtAlarmListExtraProcessStatus.toEntity( alarmListExtraProcessStatus));
		
	}

	@Override
	public void updateAlListExtaProcess(AlarmListExtraProcessStatus alarmListExtraProcessStatus) {
		KfnmtAlarmListExtraProcessStatus newEntity = KfnmtAlarmListExtraProcessStatus.toEntity(alarmListExtraProcessStatus);
		KfnmtAlarmListExtraProcessStatus updateEntity = this.queryProxy().find(newEntity.kfnmtAlarmListExtraProcessStatusPK, KfnmtAlarmListExtraProcessStatus.class).get();
		updateEntity.employeeID = newEntity.employeeID;
		updateEntity.endDate = newEntity.endDate;
		updateEntity.endTime = newEntity.endTime;
		this.commandProxy().update(updateEntity);
		
	}

	@Override
	public void deleteAlListExtaProcess(String companyID, GeneralDate startDate, int startTime) {
		this.commandProxy().remove(KfnmtAlarmListExtraProcessStatus.class,new KfnmtAlarmListExtraProcessStatusPK(companyID,startDate,startTime));
		
	}

	@Override
	public Optional<AlarmListExtraProcessStatus> getAlListExtaProcessByEndDate(String companyID) {
		return this.queryProxy().query(SELECT_ALEX_PROCESS_BY_END_DATE,KfnmtAlarmListExtraProcessStatus.class)
				.setParameter("companyID", companyID)
				.setParameter("endDate", null)
				.setParameter("endTime", null)
				.getSingle(c -> c.toDomain());
	}


}
