package nts.uk.ctx.at.schedule.pubimp.schedule.workschedule.ccg005;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.ccg005.WorkSchedulePub;

@Stateless
public class WorkSchedulePubImpl implements WorkSchedulePub{

	@Inject
	private WorkScheduleRepository repo;
	
	@Override
	public Optional<String> getWorkTypeCode(String sid, GeneralDate baseDate) {
		return Optional.ofNullable(repo.get(sid, baseDate).map(i -> i.getWorkInfo().getRecordInfo().getWorkTypeCode().v()).orElse(null));
	}
}
