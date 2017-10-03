package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
@Stateless
public class JpaScheduleErrorLogRepository implements ScheduleErrorLogRepository{

	@Override
	public List<ScheduleErrorLog> findByExecutionId(String executionId) {
		// TODO Auto-generated method stub
		return null;
	}

}
