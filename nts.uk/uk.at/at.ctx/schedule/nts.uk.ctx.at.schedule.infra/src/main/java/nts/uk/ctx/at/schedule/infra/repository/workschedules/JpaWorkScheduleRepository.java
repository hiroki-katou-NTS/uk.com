package nts.uk.ctx.at.schedule.infra.repository.workschedules;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfo;

@Stateless
public class JpaWorkScheduleRepository extends JpaRepository implements WorkScheduleRepository{

	@Override
	public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(WorkSchedule workSchedule) {
		this.commandProxy().insert(this.toEntity(workSchedule));
	}
	
	public KscdtSchBasicInfo toEntity(WorkSchedule workSchedule) {
		return null;
	}

	@Override
	public void update(WorkSchedule workSchedule) {
		// TODO Auto-generated method stub
		
	}

}
