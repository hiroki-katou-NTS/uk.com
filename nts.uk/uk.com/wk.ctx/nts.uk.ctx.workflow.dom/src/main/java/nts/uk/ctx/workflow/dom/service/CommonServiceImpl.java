package nts.uk.ctx.workflow.dom.service;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CommonServiceImpl implements CommonService {
	
	@Override
	public List<ApprovalRootState> getApprovalRootStateListByDateAndID(List<String> listEmployeeID,
			List<GeneralDate> listDate) {
		return null;
	}

}
