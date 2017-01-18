package nts.uk.ctx.basic.dom.system.bank.branch;

import java.util.List;

import nts.uk.ctx.basic.dom.system.bank.BankCode;

public interface BankBranchRepository {
	
    /**
     * Get list of bank code
     * @param bankCode
     * @return
     */
	List<BankBranch> findAll(String companyCode,BankCode bankCode);
	
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
