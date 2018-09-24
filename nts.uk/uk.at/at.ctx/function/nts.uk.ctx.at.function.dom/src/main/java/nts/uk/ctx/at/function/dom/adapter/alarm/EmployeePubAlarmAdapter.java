package nts.uk.ctx.at.function.dom.adapter.alarm;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author thuongtv
 *
 */

public interface EmployeePubAlarmAdapter {
	// call request list 218
	List<String> getListEmployeeId(String workplaceId, GeneralDate executeDate);
}
