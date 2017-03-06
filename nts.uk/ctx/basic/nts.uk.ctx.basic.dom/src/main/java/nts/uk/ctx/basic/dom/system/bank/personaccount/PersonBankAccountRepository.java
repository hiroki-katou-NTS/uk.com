package nts.uk.ctx.basic.dom.system.bank.personaccount;

import java.util.List;
import java.util.Optional;

public interface PersonBankAccountRepository {
	
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	List<PersonBankAccount> findAll (String companyCode);
	
	/**
	 * 
	 * @param conpanyCode
	 * @param personId
	 * @param historyID
	 * @return
	 */
	Optional<PersonBankAccount> find (String conpanyCode, String personId, String historyID);
	
	/**
	 * Get all person bank account by bank
	 * @param companyCode company code
	 * @param bankCode bank code
	 * @return list person bank account
	 */
	List<PersonBankAccount> findAll(String companyCode, String bankCode);
	/**
	 * Get all person bank account base on line bank code
	 * @param companyCode
	 * @param lineBankCode
	 * @return
	 */
	List<PersonBankAccount> findAllLineBank(String companyCode, String lineBankCode);

	/**
	 * Check all person bank account by bank
	 * @param companyCode company code
	 * @param bankCode bank code
	 * @return list person bank account
	 */
	/**
	 * 
	 * @param companyCode
	 * @param bankCode
	 * @param branchCode
	 * @return
	 */
	List<PersonBankAccount> findAllBranchCode(String companyCode, String bankCode, String branchCode);
	
	boolean checkExistsBankAccount(String companyCode, List<String> bankCode);
	
	boolean checkExistsBranchAccount(String companyCode, List<String> bankCode, List<String> branchCode);
	
	boolean checkExistsLineBankAccount(String companyCode, List<String> lineBank);
	/**
	 * 
	 * @param personBankAccount
	 */
	void update(PersonBankAccount personBankAccount);
}
