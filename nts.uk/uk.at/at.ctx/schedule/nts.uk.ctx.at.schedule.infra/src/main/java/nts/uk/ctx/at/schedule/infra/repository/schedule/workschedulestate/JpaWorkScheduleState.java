package nts.uk.ctx.at.schedule.infra.repository.schedule.workschedulestate;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleStateRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate.KscstWorkScheduleState;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate.KscstWorkScheduleStatePK;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkScheduleState extends JpaRepository implements WorkScheduleStateRepository {

	private static final String SELECT_ALL = "SELECT a FROM KscstWorkScheduleState a";
	private static final String SELCET_BY_DATE_EMPID = "SELECT a FROM KscstWorkScheduleState a "
			+ "WHERE a.kscstWorkScheduleStatePK.employeeId =: sId " + "AND a.kscstWorkScheduleStatePK.date =: date";

	private WorkScheduleState toDomain(KscstWorkScheduleState entity) {
		WorkScheduleState domain = WorkScheduleState.createFromJavaType(entity.scheduleEditState,
				entity.kscstWorkScheduleStatePK.scheduleItemId, entity.kscstWorkScheduleStatePK.date,
				entity.kscstWorkScheduleStatePK.employeeId);
		return domain;
	}

	@Override
	public List<WorkScheduleState> findAll() {
		return this.queryProxy().query(SELECT_ALL, KscstWorkScheduleState.class).getList(x -> toDomain(x));
	}

	@Override
	public void updateScheduleEditState(WorkScheduleState domain) {
		Optional<KscstWorkScheduleState> kscstWorkScheduleState = this.queryProxy().find(
				new KscstWorkScheduleStatePK(domain.getSId(), domain.getScheduleItemId(), domain.getYmd()),
				KscstWorkScheduleState.class);
		if (kscstWorkScheduleState.isPresent()) {
			kscstWorkScheduleState.get().scheduleEditState = domain.getScheduleEditState().value;
			this.commandProxy().update(kscstWorkScheduleState);
			this.getEntityManager().flush();
		}
	}

	@Override
	public List<WorkScheduleState> findByDateAndEmpId(String sId, GeneralDate date) {
		return this.queryProxy().query(SELCET_BY_DATE_EMPID, KscstWorkScheduleState.class).setParameter("sId", sId)
				.setParameter("date", date).getList(x -> toDomain(x));
	}
}
