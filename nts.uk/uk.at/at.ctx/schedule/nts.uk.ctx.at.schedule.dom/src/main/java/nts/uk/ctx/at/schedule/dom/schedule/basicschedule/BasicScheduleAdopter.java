package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface BasicScheduleAdopter {
	
	Optional<BasicSchedule> findByEmployeeIdAndBaseDate(String employeeId, GeneralDate baseDate);
}
