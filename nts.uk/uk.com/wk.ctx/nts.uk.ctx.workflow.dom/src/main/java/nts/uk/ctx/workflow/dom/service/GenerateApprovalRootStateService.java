package nts.uk.ctx.workflow.dom.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.service.output.AppRootStateConfirmOutput;
/**
 * 承認ルートインスタンスを生成する
 * @author Doan Duy Hung
 *
 */
public interface GenerateApprovalRootStateService {
	
	/**
	 * 承認ルートインスタンスを生成する
	 * @param companyID
	 * @param employeeID
	 * @param confirmAtr
	 * @param appType
	 * @param date
	 * @return
	 */
	public AppRootStateConfirmOutput getApprovalRootState(String companyID, String employeeID, 
			ConfirmationRootType confirmAtr, ApplicationType appType, GeneralDate date);
	
	
	
}
