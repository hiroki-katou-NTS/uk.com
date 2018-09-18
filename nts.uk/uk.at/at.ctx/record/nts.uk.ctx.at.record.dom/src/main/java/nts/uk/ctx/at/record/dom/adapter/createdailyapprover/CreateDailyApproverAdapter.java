package nts.uk.ctx.at.record.dom.adapter.createdailyapprover;

import nts.arc.time.GeneralDate;

public interface CreateDailyApproverAdapter {
	public AppRootInsContentFnImport createDailyApprover(String employeeID, Integer rootType, GeneralDate recordDate);
	
	// 日別実績の就業実績確認状態を作成する
	public void createApprovalStatus(String employeeID, GeneralDate date, Integer rootType);
	
	//
	public void deleteApprovalStatus(String employeeID, GeneralDate date, Integer rootType);
}
