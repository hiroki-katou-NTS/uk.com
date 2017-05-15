/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;

/**
 * A factory for creating Generator objects.
 */
@Stateless
public class PaymentReportGeneratorFactory {

	/** The qualification generator. */
	@EJB(name = "PaymentReportHorizontalGenerator")
	private PaymentReportHorizontalGenerator paymentReportHorizontalGenerator;

	/**
	 * Creates a new PaymentReportGenerator object.
	 *
	 * @param data the data
	 * @return the payment report generator
	 */
	public PaymentGenerator createGenerator(PaymentReportData data) {
		int val = 0;
		switch (val) {
		case 0:
			return new PaymentReportVerticalGenerator();

		case 1:
			return paymentReportHorizontalGenerator;

		default:
			break;
		}

		return null;
	}
}
