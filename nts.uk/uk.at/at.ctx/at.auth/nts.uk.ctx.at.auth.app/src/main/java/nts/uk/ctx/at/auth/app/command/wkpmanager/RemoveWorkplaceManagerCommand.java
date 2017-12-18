package nts.uk.ctx.at.auth.app.command.wkpmanager;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemoveWorkplaceManagerCommand {
	/* 職場管理者ID */
	private String wkpManagerId;
}
