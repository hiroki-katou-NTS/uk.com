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
	public Optional<BankBranch> findBranch(String companyId, String branchId);
	
    /**
     * Get list of bank code
     * @param bankCode
     * @return
     */
	public List<BankBranch> findAllBranch(String companyId, List<String> bankCodes);
	
	/**
     * Get list of bank code
     * @param bankCode
     * @return
     */
	public List<BankBranch> findAllBranchByBank(String companyId, String bankCode);
	
	/**
	 * Add new bank branch
	 * @param bank branch
	 */
    
	public void addBranch(BankBranch bank);
	
    /**
     * Update bank branch information	
     * @param branchId branch
     */
	public void updateBranch(BankBranch bank);
	
	/**
	 * Remove a bank branch
	 * @param branchId branch
	 */
	public void removeBranch(String companyId, String branchId);
	
	/**
	 * Remove a list bank branch
	 * @param companyId
	 * @param bankCode
	 */
	public void removeListBranch(String companyId, List<String> branchIds);
	
	/**
	 * Remove a list bank branch
	 * @param companyId
	 * @param bankCode
	 */
	public void removeListBranchFromBank(String companyId, String bankCode);

	/**
	 * Check exist bank
	 * @param companyCode
	 * @param bankCode
	 * @param branchCode
	 * @return
	 */
	public boolean checkExistBranch(String companyCode, String bankCode, String branchCode);
	
}
