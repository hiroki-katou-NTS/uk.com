package nts.uk.ctx.pr.core.dom.payment.banktranfer;

public interface BankTranferRepository {
	/**
	 * Add new bank tranfer
	 * @param root Bank Tranfer domain
	 */
	void add(BankTranfer root);
}
