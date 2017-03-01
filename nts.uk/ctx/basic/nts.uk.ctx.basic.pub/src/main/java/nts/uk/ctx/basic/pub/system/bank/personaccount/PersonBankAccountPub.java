package nts.uk.ctx.basic.pub.system.bank.personaccount;

import java.util.List;

public interface PersonBankAccountPub {
	/**
	 * Publish check exists bank account by bank
	 * @param companyCode
	 * @param bankCodeList
	 * @return
	 */
	boolean checkExistsBankAccount(String companyCode, List<String> bankCodeList);
}
