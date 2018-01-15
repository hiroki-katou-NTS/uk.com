package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ScBasicScheduleAdapter {
	
	public Optional<ScBasicScheduleImport> findByID(String employeeID, GeneralDate date);
	
}
