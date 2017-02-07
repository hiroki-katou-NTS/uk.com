package nts.uk.ctx.basic.dom.system.bank.branch;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.basic.dom.system.bank.BankCode;

public interface BankBranchRepository {
	
	/**
	 * Find bank branch
	 * @param companyCode
	 * @param bankCode
	 * @param branchCode
	 * @return
	 */
	Optional<BankBranch> find(String companyCode, String bankCode, String branchCode);
	
    /**
     * Get list of bank code
     * @param bankCode
     * @return
     */
	List<BankBranch> findAll(String companyCode,BankCode bankCode);
	
	/**
     * Get list 
     * @param bankCode
     * @return
     */
	List<BankBranch> findAll(String companyCode);
	
	/**
	 * Add new bank branch
	 * @param bank branch
	 */
    
	void add(BankBranch bank);
	
    /**
     * Update bank branch information	
     * @param bank branch
     */
	void update(BankBranch bank);
	
	/**
	 * Remove a bank branch
	 * @param bank branch
	 */
	void remove(BankBranch bank);
}
