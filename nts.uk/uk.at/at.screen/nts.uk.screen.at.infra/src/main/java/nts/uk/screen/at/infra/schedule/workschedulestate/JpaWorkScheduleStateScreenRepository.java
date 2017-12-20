package nts.uk.screen.at.infra.schedule.workschedulestate;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate.KscstWorkScheduleState;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenDto;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenRepository;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkScheduleStateScreenRepository extends JpaRepository
		implements WorkScheduleStateScreenRepository {

	private final String SELECT_BY_SID_AND_DATE_AND_SCHEDULE_ITEM_ID = "SELECT a FROM KscstWorkScheduleState a "
			+ "WHERE a.kscstWorkScheduleStatePK.employeeId IN :sId "
			+ "AND a.kscstWorkScheduleStatePK.date >= :startDate AND a.kscstWorkScheduleStatePK.date <= :endDate "
			+ "AND a.kscstWorkScheduleStatePK.scheduleItemId = 1 OR a.kscstWorkScheduleStatePK.scheduleItemId = 2 "
			+ "OR a.kscstWorkScheduleStatePK.scheduleItemId = 3 OR a.kscstWorkScheduleStatePK.scheduleItemId = 4";

	private static WorkScheduleStateScreenDto toDto(KscstWorkScheduleState entity) {
		return new WorkScheduleStateScreenDto(entity.kscstWorkScheduleStatePK.employeeId,
				entity.kscstWorkScheduleStatePK.date, entity.kscstWorkScheduleStatePK.scheduleItemId,
				entity.scheduleEditState);
	}

	@Override
	public List<WorkScheduleStateScreenDto> getByListSidAndDateAndScheId(List<String> sId, GeneralDate startDate,
			GeneralDate endDate) {
		return this.queryProxy().query(SELECT_BY_SID_AND_DATE_AND_SCHEDULE_ITEM_ID, KscstWorkScheduleState.class)
				.setParameter("sId", sId).setParameter("startDate", startDate).setParameter("endDate", endDate)
				.getList(x -> toDto(x));
	}

}
