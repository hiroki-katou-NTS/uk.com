package nts.uk.ctx.at.schedule.dom.schedule.algorithm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.workinfo.GetWorkInfoSchedule;

@Stateless
public class GetWorkInfoScheduleImpl implements GetWorkInfoSchedule{

	@Inject
	private WorkScheduleRepository workScheduleRepository;
	@Override
	public Optional<WorkInformation> getWorkInfoSc(String employeeId, GeneralDate baseDate) {
		
		return workScheduleRepository.get(employeeId, baseDate).map(x -> x.getWorkInfo().getRecordInfo());
	}

}
