package nts.uk.ctx.workflow.pub.service.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
/**
 * @author loivt
 *	ルート状況
 */
@Data
@AllArgsConstructor
public class ApprovalRootSituation {
	/**
	 * インスタンスID
	 */
	private String appRootID;
	/**
	 * ルートの状況
	 */
	private ApproverEmployeeState approvalAtr;
	/**
	 * 対象日
	 */
	private GeneralDate appDate;
	/**
	 * 対象者
	 */
	private String targetID;
	/**
	 * 承認状況
	 */
	private ApprovalStatus approvalStatus; 
}
