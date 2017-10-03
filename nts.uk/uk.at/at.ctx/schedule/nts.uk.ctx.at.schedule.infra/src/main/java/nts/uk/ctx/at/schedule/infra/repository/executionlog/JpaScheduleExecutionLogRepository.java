package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Period;
@Stateless
public class JpaScheduleExecutionLogRepository implements ScheduleExecutionLogRepository {

	@Override
	public List<ScheduleExecutionLog> find(String companyId, Period period) {
		// TODO Auto-generated method stub
		return null;
	}

}
