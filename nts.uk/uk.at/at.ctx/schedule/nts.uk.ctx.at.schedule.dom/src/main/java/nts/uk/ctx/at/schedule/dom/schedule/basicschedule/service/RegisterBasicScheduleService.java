package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;

public interface RegisterBasicScheduleService {
	/**
	 * Process register basic schedule
	 * @return
	 */
	public List<String> register(String companyId, Integer modeDisplay, List<BasicSchedule> basicScheduleListAfter,
			List<BasicSchedule> basicScheduleListBefore, boolean isInsertMode, RegistrationListDateSchedule registrationListDateSchedule);
}
