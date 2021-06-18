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
	
	public ScBasicScheduleImport findByIDRefactor(String employeeID, GeneralDate date);
	
	public List<BasicScheduleConfirmImport> findConfirmById(List<String> employeeID, DatePeriod date);
	
}
