package nts.uk.ctx.at.record.dom.adapter.workschedule;

import java.util.Optional;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author tutk
 *
 */
public interface WorkScheduleWorkInforAdapter {
	public Optional<WorkScheduleWorkInforImport> get(String employeeID , GeneralDate ymd);
}
