package nts.uk.ctx.at.schedule.infra.entity.workschedule;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
/**
 * 
 * @author Hieult
 *
 */
@Stateless
public class JpaWorkScheduleBaRepository extends JpaRepository  {

	
	public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {

		return null;
	}

	
	public void insert(WorkSchedule workSchedule) {
		
	}

	
	public void update(WorkSchedule workSchedule) {
		
	}

}
