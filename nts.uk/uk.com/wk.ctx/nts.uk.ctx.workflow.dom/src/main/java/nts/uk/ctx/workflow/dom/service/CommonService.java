package nts.uk.ctx.workflow.dom.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;

/**
 * 共通アルゴリズム
 * @author Doan Duy Hung
 *
 */
public interface CommonService {
	
	public List<ApprovalRootState> getApprovalRootStateListByDateAndID(List<String> listEmployeeID, List<GeneralDate> listDate);
	
}
