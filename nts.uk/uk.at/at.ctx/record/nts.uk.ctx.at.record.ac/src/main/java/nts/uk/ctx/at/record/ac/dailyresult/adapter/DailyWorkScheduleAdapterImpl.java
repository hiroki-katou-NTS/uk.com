package nts.uk.ctx.at.record.ac.dailyresult.adapter;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyresult.adapter.DailyWorkScheduleAdapter;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkSchedulePub;

@Stateless
public class DailyWorkScheduleAdapterImpl implements DailyWorkScheduleAdapter {

	@Inject
	private WorkSchedulePub workSchedulePub;

	@Override
	public Optional<String> getWorkTypeCode(String sid, GeneralDate baseDate) {
		return workSchedulePub.getWorkTypeCode(sid, baseDate);
	}
}
