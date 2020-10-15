package nts.uk.ctx.at.request.dom.application.common.service.print;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.dto.AffJobTitleHistoryImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.印刷内容を取得する.印字する承認者を取得する.承認者印字詳細
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
public class ApproverPrintDetails {
	
	/**
	 * 承認区分
	 */
	private ApprovalBehaviorAtrImport_New approvalBehaviorAtr;
	
	/**
	 * 承認者
	 */
	private String approverID;
	
	/**
	 * 承認者の社員情報
	 */
	private EmployeeInfoImport employeeInfoImport;
	
	/**
	 * 承認者の職位情報
	 */
	private AffJobTitleHistoryImport affJobTitleHistoryImport;
	
	/**
	 * 承認日
	 */
	private Optional<GeneralDateTime> opApprovalDate;
	
	public ApproverPrintDetails() {
		this.opApprovalDate = Optional.empty();
	}
}
