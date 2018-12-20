package nts.uk.ctx.pr.transfer.dom.sourcebank;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */
public interface TransferSourceBankRepository {
	
	public List<TransferSourceBank> getAllSourceBank(String companyId);
	
	public List<TransferSourceBank> getSourceBankByBranchId(String companyId, List<String> branchIds);
	
	public Optional<TransferSourceBank> getSourceBank(String companyId, String code);
	
	public void addSourceBank(TransferSourceBank sourceBank);
	
	public void updateSourceBank(TransferSourceBank sourceBank);
	
	public void removeSourceBank(String companyId, String code);

}
