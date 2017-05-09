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
	 * Adds the.
	 *
	 * @param office the office
	 */
    void add(RefundPadding office);

	/**
	 * Update.
	 *
	 * @param office the office
	 */
    void update(RefundPadding office);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the labor insurance office
	 */
	Optional<RefundPadding> findById(String companyCode, PrintType printType);

}
