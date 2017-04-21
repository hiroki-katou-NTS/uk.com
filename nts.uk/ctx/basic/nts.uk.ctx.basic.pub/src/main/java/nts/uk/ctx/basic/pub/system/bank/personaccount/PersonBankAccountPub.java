package nts.uk.ctx.basic.pub.system.bank.personaccount;

import java.util.List;

public interface PersonBankAccountPub {
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param baseYM
	 * @return
	 */
	List<PersonBankAccountDto> findBasePIdAndBaseYM(String companyCode, String personId, int baseYM);

}
