package nts.uk.ctx.at.schedule.infra.entity.workschedule;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
/**
 * 
 * @author Hieult
 *
 */
@Stateless
public class JpaWorkScheduleRepository extends JpaRepository implements WorkScheduleRepository {

	@Override
	public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {

		return null;
	}

	@Override
	public void insert(WorkSchedule workSchedule) {
		
	}

	@Override
	public void update(WorkSchedule workSchedule) {
		
	}

}
