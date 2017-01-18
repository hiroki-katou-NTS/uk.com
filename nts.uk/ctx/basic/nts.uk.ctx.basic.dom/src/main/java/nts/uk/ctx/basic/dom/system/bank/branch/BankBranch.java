package nts.uk.ctx.basic.dom.system.bank.branch;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.company.CompanyCode;
import nts.uk.shr.com.primitive.Memo;

public class BankBranch extends AggregateRoot{
	/**
	 * Company code
	 */
	@Getter
	private CompanyCode companyCode;
	/**
	 * Bank code
	 */
	@Getter
	private String bankCode;
	/**
	 *  Bank branch code
	 */
	@Getter
	private BankBranchCode bankBranchCode;
	/**
	 *   Bank branch name
	 */
	@Getter
	private BankBranchName bankBranchName;
	/**
	 *   Bank branch name katakana
	 */
	@Getter
	private BankBranchNameKana bankBranchNameKana;
	/**
	 *   Memo
	 */
	@Getter
    private Memo memo;
	
	public BankBranch(CompanyCode companyCode, String bankCode, BankBranchCode bankBranchCode,
			BankBranchName bankBranchName, BankBranchNameKana bankBranchNameKana, Memo memo) {
		super();
		this.companyCode = companyCode;
		this.bankCode = bankCode;
		this.bankBranchCode = bankBranchCode;
		this.bankBranchName = bankBranchName;
		this.bankBranchNameKana = bankBranchNameKana;
		this.memo = memo;
	}

	public static BankBranch createFromJavaType (String companyCode,String bankCode,String bankBranchCode, String bankBranchName,
			String bankBranchNameKana, String memo){
		return new BankBranch(new CompanyCode(companyCode),bankCode,new BankBranchCode(bankBranchCode), new BankBranchName(bankBranchName), new BankBranchNameKana(bankBranchNameKana), new Memo(memo));
	}
	
}


