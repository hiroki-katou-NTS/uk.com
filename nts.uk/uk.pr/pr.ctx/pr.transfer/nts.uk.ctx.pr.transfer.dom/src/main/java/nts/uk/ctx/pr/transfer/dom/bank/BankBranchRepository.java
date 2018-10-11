package nts.uk.ctx.pr.transfer.dom.bank;

import java.util.List;
import java.util.Optional;

public interface BankBranchRepository {
	
	/**
	 * Find bank branch
	 * @param companyCode
	 * @param branchId
	 * @return
	 */
	Optional<BankBranch> findBranch(String companyId, String branchId);
	
    /**
     * Get list of bank code
     * @param bankCode
     * @return
     */
	List<BankBranch> findAllBranch(String companyId, BankCode bankCode);
	
	/**
	 * Add new bank branch
	 * @param bank branch
	 */
    
	void add(BankBranch bank);
	
    /**
     * Update bank branch information	
     * @param branchId branch
     */
	void update(BankBranch bank);
	
	/**
	 * Remove a bank branch
	 * @param branchId branch
	 */
	void remove(String companyId, String branchId);
	
	/**
	 * Remove a list bank branch
	 * @param companyCode
	 * @param branchId
	 */
	void removeAll(String companyId, List<String> branchIdList);

	/**
	 * Check exist bank
	 * @param companyCode
	 * @param bankCode
	 * @param branchCode
	 * @return
	 */
	boolean checkExists(String companyCode, String bankCode, String branchCode);
	
}
