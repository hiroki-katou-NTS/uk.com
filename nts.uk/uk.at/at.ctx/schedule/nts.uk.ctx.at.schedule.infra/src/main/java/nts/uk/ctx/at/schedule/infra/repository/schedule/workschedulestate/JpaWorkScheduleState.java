package nts.uk.ctx.at.schedule.infra.repository.schedule.workschedulestate;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleStateRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate.KscstWorkScheduleState;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkScheduleState extends JpaRepository implements WorkScheduleStateRepository {

	private final String SELECT_ALL = "SELECT a FROM KscstWorkScheduleState a";

	private final WorkScheduleState toDomain(KscstWorkScheduleState entity) {
		WorkScheduleState domain = WorkScheduleState.createFromJavaType(entity.scheduleEditState,
				entity.kscstWorkScheduleStatePK.scheduleItemId, entity.kscstWorkScheduleStatePK.date,
				entity.kscstWorkScheduleStatePK.employeeId);
		return domain;
	}

	@Override
	public List<WorkScheduleState> findAll() {
		return this.queryProxy().query(SELECT_ALL, KscstWorkScheduleState.class).getList(x -> toDomain(x));
	}
}
