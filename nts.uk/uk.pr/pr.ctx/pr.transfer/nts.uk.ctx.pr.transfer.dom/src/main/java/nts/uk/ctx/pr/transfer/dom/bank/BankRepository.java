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
	public List<Bank> findAllBank(String companyId);

	/**
	 * Find bank
	 * 
	 * @param companyCode
	 * @param bankCode
	 * @return
	 */
	public Optional<Bank> findBank(String companyId, String bankCode);

	/**
	 * Add new Bank
	 * 
	 * @param bank
	 */
	public void addBank(Bank bank);

	/**
	 * Update Bank information
	 * 
	 * @param bank
	 */
	public void updateBank(Bank bank);

	/**
	 * Remove a bank
	 * 
	 * @param companyCode
	 * @param bankCode
	 */
	public void removeBank(String companyId, String bankCode);
	
	/**
	 * Remove list bank
	 * 
	 * @param companyCode
	 * @param bankCode
	 */
	public void removeListBank(String companyId, List<String> bankCode);
	
	public boolean checkExistBank(String companyId, String bankCode);

}
