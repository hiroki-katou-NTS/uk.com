package nts.uk.screen.com.app.command.sys.auth.roleset;

import lombok.Value;

@Value
public class DeleteRoleSetCommandBase {
	/** ロールセットコード. */
	private String roleSetCd;
}
