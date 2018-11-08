package nts.uk.ctx.pr.transfer.app.command.bank;

import lombok.Value;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranch;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class BankBranchCommand {
	
	private String id;
	private String code;
	private String name;
	private String bankCode;
	private String kanaName;
	private String memo;
	
	public BankBranch toDomain() {
		if (this.id == null || this.id.isEmpty()) {
			return BankBranch.newBranch(AppContexts.user().companyId(), this.bankCode, this.code, this.name,
					this.kanaName, this.memo);
		} else {
			return BankBranch.createFromJavaType(AppContexts.user().companyId(), this.id, this.bankCode, this.code,
					this.name, this.kanaName, this.memo);
		}
	}

}
