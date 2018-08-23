package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterApproverRootOutput;

/**
 * 01.承認ルートを取得する
 * @author dudt
 *
 */
public interface ApproverRootMaster {
	/**
	 * 承認ルートを取得する
	 * @param baseDate
	 * @param isCompany 会社別があるかチェックする
	 * @param isWorkplace 職場別があるかチェックする
	 * @param isPerson 個人別があるかチェックする
	 * @return 
	 */
	public MasterApproverRootOutput masterInfors(String companyID, GeneralDate baseDate, 
			boolean isCompany, boolean isWorkplace, boolean isPerson);
}
