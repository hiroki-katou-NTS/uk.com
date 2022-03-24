package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class UpdateSelfApproverCommand {

	/**
	 * 社員ID
	 */
	private String sid;

	/**
	 * 期間
	 */
	private GeneralDate startDate;
	private GeneralDate endDate;
	
	/**
	 * 承認者設定パラメータList
	 */
	private List<ApprovalSettingParamCommand> params;
}
