package nts.uk.ctx.pr.transfer.app.command.bank;

import lombok.Value;
import nts.uk.ctx.pr.transfer.dom.bank.Bank;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class BankCommand {

	private String code;
	private String name;
	private String kanaName;
	private String memo;

	public Bank toDomain() {
		return Bank.createFromJavaType(AppContexts.user().companyId(), this.code, this.name, this.kanaName, this.memo);
	}

}
