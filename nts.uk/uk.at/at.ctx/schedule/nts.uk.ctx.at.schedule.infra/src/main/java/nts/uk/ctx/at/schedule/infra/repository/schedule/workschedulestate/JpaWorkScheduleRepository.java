package nts.uk.ctx.at.schedule.infra.repository.schedule.workschedulestate;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.workschedule.WorkScheduleRepository;

/**
 * @author anhdt
 *
 */
@Stateless
public class JpaWorkScheduleRepository implements WorkScheduleRepository {

	@Override
	public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(WorkSchedule workSchedule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WorkSchedule workSchedule) {
		// TODO Auto-generated method stub
		
	}

}
