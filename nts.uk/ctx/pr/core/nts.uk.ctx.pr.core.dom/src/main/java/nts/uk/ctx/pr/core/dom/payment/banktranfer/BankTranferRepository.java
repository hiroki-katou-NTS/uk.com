package nts.uk.ctx.pr.core.dom.payment.banktranfer;

import java.util.List;
import java.util.Optional;

public interface BankTranferRepository {
	/**
	 * Add new bank tranfer
	 * @param root Bank Tranfer domain
	 */
	void add(BankTranfer root);
	
	/**
	 * Update new bank tranfer
	 * @param root Bank Tranfer domain
	 */
	void update(BankTranfer root);
	
	/**
	 * Find list bank tranfer
	 * (SEL_1)
	 * @param param parameter information
	 * @return BankTranfer
	 */
	List<BankTranfer> findBySEL1(BankTranferParam param);
	
	/**
	 * Find list bank tranfer
	 * (SEL_2)
	 * @param param parameter information
	 * @return BankTranfer
	 */
	List<BankTranfer> findBySEL2(BankTranferParam param);
	
	/**
	 * Find a bank tranfer
	 * @param param parameter information
	 * @return BankTranfer
	 */
	Optional<BankTranfer> find(BankTranferParam param);
}
