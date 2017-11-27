package nts.uk.ctx.at.shared.dom.worktime.algorithm.caltimediff;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.CollectionAtr;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.JoggingWorkTime;
import nts.uk.ctx.at.shared.dom.worktimeset_old.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktimeset_old.Timezone;
import nts.uk.ctx.at.shared.dom.worktimeset_old.WorkTimeSet;
import nts.uk.ctx.at.shared.dom.worktimeset_old.WorkTimeSetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class CalculateTimeDiffServiceImpl implements CalculateTimeDiffService {
	@Inject
	public WorkTimeSetRepository workTimeSetRepo;
	
	@Override
	public JoggingWorkTime caculateJoggingWorkTime(String workTimeCode, TimeWithDayAttr scheduleStartClock) {
		JoggingWorkTime joggingWorkTime = new JoggingWorkTime();
		String companyId = AppContexts.user().companyId();
		Optional<WorkTimeSet> workTimeSet = workTimeSetRepo.findByCode(companyId, workTimeCode);
		if (!workTimeSet.isPresent()) {
			return null;
		}
		PrescribedTimezoneSetting pTimezoneSetting = workTimeSet.get().getPrescribedTimezoneSetting();
		// TimeWithDayAttr morningEndTime = pTimezoneSetting.getMorningEndTime();
		// TimeWithDayAttr afternoonStartTime = pTimezoneSetting.getAfternoonStartTime();
		List<Timezone> timezone = pTimezoneSetting.getTimezone();
		TimeWithDayAttr startClock = timezone.get(0).getStart();
		int time = scheduleStartClock.valueAsMinutes() - startClock.valueAsMinutes();
		if (scheduleStartClock.greaterThan(startClock)) {
			joggingWorkTime.setAtr(CollectionAtr.AFTER);
			joggingWorkTime.setTime(new AttendanceTime(time));
		} else {
			joggingWorkTime.setAtr(CollectionAtr.BEFORE);
			joggingWorkTime.setTime(new AttendanceTime(Math.abs(time)));
		}
		return joggingWorkTime;
	}

}
