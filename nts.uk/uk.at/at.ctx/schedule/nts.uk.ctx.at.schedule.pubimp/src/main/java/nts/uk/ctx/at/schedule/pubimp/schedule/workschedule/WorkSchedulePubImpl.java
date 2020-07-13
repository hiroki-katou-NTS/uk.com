package nts.uk.ctx.at.schedule.pubimp.schedule.workschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkSchedulePub;

/**
 * 
 * @author tutk
 *
 */
@Stateless
public class WorkSchedulePubImpl implements WorkSchedulePub {

	@Inject
	private WorkScheduleRepository workScheduleRepository;

	@Override
	public Optional<WorkScheduleExport> get(String employeeID, GeneralDate ymd) {
		Optional<WorkSchedule> data = workScheduleRepository.get(employeeID, ymd);
		if (data.isPresent()) {
			return Optional.of(new WorkScheduleExport(data.get().getWorkInfo().getRecordInfo().getWorkTimeCode().v(),
					data.get().getWorkInfo().getRecordInfo().getWorkTimeCode() == null ? null
							: data.get().getWorkInfo().getRecordInfo().getWorkTimeCode().v(),
					data.get().getWorkInfo().getGoStraightAtr().value,
					data.get().getWorkInfo().getBackStraightAtr().value));
		}
		return Optional.empty();
	}

}
