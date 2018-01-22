package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;

public interface BasicScheduleService {
	/**
	 * Process register basic schedule
	 * @return
	 */
	public List<String> register(String companyId, List<BasicSchedule> basicScheduleList);
}
