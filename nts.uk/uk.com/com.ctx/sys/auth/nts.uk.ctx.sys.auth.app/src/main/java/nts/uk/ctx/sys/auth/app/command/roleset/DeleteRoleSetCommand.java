package nts.uk.ctx.sys.auth.app.command.roleset;

import lombok.Value;

@Value
public class DeleteRoleSetCommand {
	/** ロールセットコード. */
	private String roleSetCd;
}
