package nts.uk.ctx.at.record.dom.adapter.statusofemployee;

import nts.arc.time.GeneralDate;

public interface RecStatusOfEmployeeAdapter {
	
	public RecStatusOfEmployeeImport getStatusOfEmployeeService(String employeeId, GeneralDate day);

}
