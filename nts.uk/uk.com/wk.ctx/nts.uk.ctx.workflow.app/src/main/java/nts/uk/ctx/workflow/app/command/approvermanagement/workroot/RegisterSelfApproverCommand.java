package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class RegisterSelfApproverCommand {
	
	/**
	 * 社員ID
	 */
	private String sid;

	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
	
	/**
	 * 承認者設定パラメータList
	 */
	private List<ApprovalSettingParamCommand> params;
}
