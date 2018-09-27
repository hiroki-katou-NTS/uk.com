package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
@AllArgsConstructor
public class ApprovalForApplication {
	Integer empRootAtr;
	/**
	 * 申請種類
	 */
	Integer appType;
	/**
	 * 申請種類 name
	 */
	String appTypeName;
	/**
	 * 期間: 開始日
	 */
	GeneralDate startDate;
	/**
	 * 期間: 完了日
	 */
	GeneralDate endDate;
	/**
	 *　承認者情報
	 */
	List<ApprovalRootMaster> lstApproval;
	
}
