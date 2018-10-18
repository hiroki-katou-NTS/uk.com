package nts.uk.screen.at.infra.schedule.workschedulestate;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate.KscdtScheState;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenDto;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenRepository;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkScheduleStateScreenRepository extends JpaRepository implements WorkScheduleStateScreenRepository {

	private final String SELECT_BY_SID_AND_DATE_AND_SCHEDULE_ITEM_ID = "SELECT a FROM KscdtScheState a "
			+ "WHERE a.kscdtScheStatePK.employeeId IN :sId "
			+ "AND (a.kscdtScheStatePK.date BETWEEN :startDate AND :endDate) "
			+ "AND (a.kscdtScheStatePK.scheduleItemId = 1 OR a.kscdtScheStatePK.scheduleItemId = 2 "
			+ "OR a.kscdtScheStatePK.scheduleItemId = 3 OR a.kscdtScheStatePK.scheduleItemId = 4)";

	private static WorkScheduleStateScreenDto toDto(KscdtScheState entity) {
		return new WorkScheduleStateScreenDto(entity.kscdtScheStatePK.employeeId,
				entity.kscdtScheStatePK.date, entity.kscdtScheStatePK.scheduleItemId,
				entity.scheduleEditState);
	}

	@Override
	public List<WorkScheduleStateScreenDto> getByListSidAndDateAndScheId(List<String> sId, GeneralDate startDate,
			GeneralDate endDate) {
		List<WorkScheduleStateScreenDto> datas = new ArrayList<WorkScheduleStateScreenDto>();
		CollectionUtil.split(sId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subIdList -> {
			datas.addAll(
					this.queryProxy().query(SELECT_BY_SID_AND_DATE_AND_SCHEDULE_ITEM_ID, KscdtScheState.class)
							.setParameter("sId", subIdList).setParameter("startDate", startDate)
							.setParameter("endDate", endDate).getList(x -> toDto(x)));
		});
		return datas;
	}
}
