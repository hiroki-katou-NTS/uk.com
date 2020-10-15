package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ScBasicScheduleAdapter {
	
	public Optional<ScBasicScheduleImport_Old> findByID(String employeeID, GeneralDate date);
	
	public List<ScBasicScheduleImport_Old> findByID(List<String> employeeID, DatePeriod date);
	
	public ScBasicScheduleImport findByIDRefactor(String employeeID, GeneralDate date);
	
}
