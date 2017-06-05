/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding;

import java.util.Optional;

/**
 * The Interface RefundPaddingRepository.
 */
public interface RefundPaddingRepository {

	/**
	 * Save.
	 *
	 * @param refundPadding the refund padding
	 */
    void save(RefundPadding refundPadding);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the labor insurance office
	 */
	Optional<RefundPadding> findByPrintType(String companyCode, PrintType printType);

}
