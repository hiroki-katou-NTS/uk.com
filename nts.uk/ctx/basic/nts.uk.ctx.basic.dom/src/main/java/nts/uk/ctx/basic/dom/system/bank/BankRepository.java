package nts.uk.ctx.basic.dom.system.bank;

import java.util.List;

public interface BankRepository {

	/**
	 * Get list of bank
	 * @param companyCode
	 * @return
	 */
	List<Bank> findAll(String companyCode);

	/**
	 * Add new Bank
	 * @param bank
	 */
	void add(Bank bank);
	
	/**
	 * Update Bank information
	 * @param bank
	 */
	void update(Bank bank);
	
	/**
	 * Remove a bank
	 * @param companyCode
	 * @param bankCode
	 */
	void remove(Bank bank);
}
