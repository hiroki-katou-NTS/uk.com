package nts.uk.pub.spr.approvalroot;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.pub.spr.approvalroot.output.ApprovalComSpr;
import nts.uk.pub.spr.approvalroot.output.ApprovalPersonSpr;
import nts.uk.pub.spr.approvalroot.output.ApprovalPhaseSpr;
import nts.uk.pub.spr.approvalroot.output.ApprovalRootStateSpr;
import nts.uk.pub.spr.approvalroot.output.ApprovalWorkplaceSpr;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface SprApprovalSearch {
	
	public List<ApprovalComSpr> getApprovalRootCom(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr);
	
	public List<ApprovalWorkplaceSpr> getApprovalRootWp(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr);
	
	public List<ApprovalPersonSpr> getApprovalRootPs(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr);
	
	public List<ApprovalPhaseSpr> getAllIncludeApprovers(String companyId, String branchId);
	
	public List<ApprovalRootStateSpr> getRootStateByDateAndType(GeneralDate date, Integer rootType);
	
	/**
	 * 3.指定した社員が承認できるかの判断
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @return
	 */
	public boolean judgmentTargetPersonCanApprove(String companyID, String rootStateID, String employeeID);
}
