package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
@Stateless
public class JpaScheduleErrorLogRepository implements ScheduleErrorLogRepository{

	@Override
	public List<ScheduleErrorLog> findByExecutionId(String executionId) {
		//TODO mock data for test export
		List<ScheduleErrorLog> lst=new ArrayList<>();
		lst.add(new ScheduleErrorLog(executionId, executionId, GeneralDate.today(), executionId));
		lst.add(new ScheduleErrorLog(executionId, executionId, GeneralDate.today(), executionId));
		lst.add(new ScheduleErrorLog(executionId, executionId, GeneralDate.today(), executionId));
		lst.add(new ScheduleErrorLog(executionId, executionId, GeneralDate.today(), executionId));
		lst.add(new ScheduleErrorLog(executionId, executionId, GeneralDate.today(), executionId));
		lst.add(new ScheduleErrorLog(executionId, executionId, GeneralDate.today(), executionId));
		lst.add(new ScheduleErrorLog(executionId, executionId, GeneralDate.today(), executionId));
		return lst;
	}

}
