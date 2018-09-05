package nts.uk.ctx.at.record.dom.adapter.createdailyapprover;

import nts.arc.time.GeneralDate;

public interface CreateDailyApproverAdapter {
	public AppRootInsContentFnImport createDailyApprover(String employeeID, Integer rootType, GeneralDate recordDate);
}
