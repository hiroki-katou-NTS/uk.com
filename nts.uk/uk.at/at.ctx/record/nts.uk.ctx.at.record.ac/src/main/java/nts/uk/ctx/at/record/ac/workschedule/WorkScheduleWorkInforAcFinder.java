package nts.uk.ctx.at.record.ac.workschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkSchedulePub;
/**
 * 
 * @author tutk
 *
 */
@Stateless
public class WorkScheduleWorkInforAcFinder implements WorkScheduleWorkInforAdapter {

	@Inject
	private WorkSchedulePub workSchedulePub;
	@Override
	public Optional<WorkScheduleWorkInforImport> get(String employeeID, GeneralDate ymd) {
		Optional<WorkScheduleExport> data =workSchedulePub.get(employeeID, ymd);
		if(data.isPresent()) {
			return Optional.of(new WorkScheduleWorkInforImport(data.get().getWorkTyle(), data.get().getWorkTime(),
					data.get().getGoStraightAtr(), data.get().getBackStraightAtr()));
		}
		return Optional.empty();
	}
	

}
