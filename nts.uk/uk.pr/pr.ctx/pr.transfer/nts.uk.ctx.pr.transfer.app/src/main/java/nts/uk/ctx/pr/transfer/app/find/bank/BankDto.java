package nts.uk.ctx.pr.transfer.app.find.bank;

import lombok.Value;
import nts.uk.ctx.pr.transfer.dom.bank.Bank;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class BankDto {
	
	private String code;
	private String name;
	private String kanaName;
	private String memo;

	public BankDto(Bank domain) {
		super();
		this.code = domain.getBankCode().v();
		this.name = domain.getBankName().v();
		this.kanaName = domain.getBankNameKana().isPresent() ? domain.getBankNameKana().get().v() : null;
		this.memo = domain.getMemo().isPresent() ? domain.getMemo().get().v() : null;
	}
	
}
