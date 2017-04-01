/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.salarychart.data;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class DepartmentData.
 */
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder

/**
 * Gets the dep path.
 *
 * @return the dep path
 */

/**
 * Gets the accumulate payment.
 *
 * @return the accumulate payment
 */

/**
 * Gets the accumulated payment.
 *
 * @return the accumulated payment
 */
@Getter

/**
 * Sets the dep path.
 *
 * @param depPath the new dep path
 */

/**
 * Sets the accumulate payment.
 *
 * @param accumulatePayment the new accumulate payment
 */

/**
 * Sets the accumulated payment.
 *
 * @param accumulatedPayment the new accumulated payment
 */
@Setter
public class DepartmentData {
	
	/** The dep code. */
	private String depCode;
	
	/** The dep name. */
	private String depName; 
	
	/** The dep path. */
	private String depPath;
	
	/** The dep level. */
	private Integer depLevel;
	
	/** The denomination. */
	private Map<Denomination, Long> denomination;
	
	/** The accumulated payment. */
	private Double accumulatedPayment;
	
}
