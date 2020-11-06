package nts.uk.ctx.workflow.app.command.approvermanagement.setting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovalSettingCommand_Old {
	/**
	 * 本人による承認
	 */
	private int prinFlg;
}
