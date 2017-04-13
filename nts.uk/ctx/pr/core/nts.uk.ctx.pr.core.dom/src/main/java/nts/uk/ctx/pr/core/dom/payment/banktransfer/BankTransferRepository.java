package nts.uk.ctx.pr.core.dom.payment.banktransfer;

import java.util.List;
import java.util.Optional;

public interface BankTransferRepository {
	/**
	 * Add new bank transfer
	 * @param root Bank Transfer domain
	 */
	void add(BankTransfer root);
	
	/**
	 * Update new bank transfer
	 * @param root Bank Transfer domain
	 */
	void update(BankTransfer root);
	
	/**
	 * Find list bank transfer
	 * (SEL_1)
	 * @param param parameter information
	 * @return BankTransfer
	 */
	List<BankTransfer> findBySEL1(BankTransferParam param);
	
	/**
	 * Find list bank transfer
	 * (SEL_2)
	 * @param param parameter information
	 * @return BankTransfer
	 */
	List<BankTransfer> findBySEL2(BankTransferParam param);
	
	/**
	 * Find a bank transfer
	 * @param param parameter information
	 * @return BankTransfer
	 */
	Optional<BankTransfer> find(BankTransferParam param);
}
