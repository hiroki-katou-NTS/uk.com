package nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter;

import java.util.List;

public interface PersonBankAccountAdapter {
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param baseYM
	 * @return
	 */
	List<BasicPersonBankAccountDto> findBasePIdAndBaseYM(String companyCode, String personId, int baseYM);
}
