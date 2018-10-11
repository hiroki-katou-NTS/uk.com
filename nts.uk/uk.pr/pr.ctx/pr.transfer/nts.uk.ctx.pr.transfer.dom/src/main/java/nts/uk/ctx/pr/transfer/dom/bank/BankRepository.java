package nts.uk.ctx.pr.transfer.dom.bank;

import java.util.List;
import java.util.Optional;

public interface BankRepository {

	/**
	 * Get list of bank
	 * 
	 * @param companyCode
	 * @return
	 */
	List<Bank> findAllBank(String companyId);

	/**
	 * Find bank
	 * 
	 * @param companyCode
	 * @param bankCode
	 * @return
	 */
	Optional<Bank> findBank(String companyId, String bankCode);

	/**
	 * Add new Bank
	 * 
	 * @param bank
	 */
	void add(Bank bank);

	/**
	 * Update Bank information
	 * 
	 * @param bank
	 */
	void update(Bank bank);

	/**
	 * Remove a bank
	 * 
	 * @param companyCode
	 * @param bankCode
	 */
	void remove(String companyId, List<String> bankCode);

}
