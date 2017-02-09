package nts.uk.ctx.basic.app.command.system.bank.branch;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TranferBranchCommand {
	/**
	 * Map<BankCode, BranchCode>
	 */
	private List<TranferBranch> branchCodes;
	private String bankNewCode;
}
