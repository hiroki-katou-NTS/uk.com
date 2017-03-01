package nts.uk.ctx.basic.app.command.system.bank.branch;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TranferBranch {
	private String bankCode;
	private String branchCode;
}
