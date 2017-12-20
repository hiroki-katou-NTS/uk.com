package nts.uk.ctx.workflow.dom.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CommonServiceImpl implements CommonService {
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;

	@Override
	public List<ApprovalRootState> getApprovalRootStateListByDateAndID(List<String> listEmployeeID,
			List<GeneralDate> listDate) {
		return null;
	}

}
