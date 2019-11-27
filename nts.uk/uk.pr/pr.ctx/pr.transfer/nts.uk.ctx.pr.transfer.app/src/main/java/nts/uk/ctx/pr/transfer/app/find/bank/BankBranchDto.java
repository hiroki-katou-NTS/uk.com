package nts.uk.ctx.pr.transfer.app.find.bank;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranch;

/**
 * 
 * @author HungTT
 *
 */

@Value
@AllArgsConstructor
public class BankBranchDto {

	private String id;
	private String code;
	private String name;
	private String bankCode;
	private String kanaName;
	private String memo;

	public BankBranchDto(BankBranch domain) {
		super();
		this.id = domain.getBranchId();
		this.code = domain.getBankBranchCode().v();
		this.name = domain.getBankBranchName().v();
		this.bankCode = domain.getBankCode().v();
		this.kanaName = domain.getBankBranchNameKana().isPresent() ? domain.getBankBranchNameKana().get().v() : null;
		this.memo = domain.getMemo().isPresent() ? domain.getMemo().get().v() : null;
	}

}
