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
	private PaymentGenerator paymentReportHorizontalGenerator;

	/**
	 * Creates a new PaymentReportGenerator object.
	 *
	 * @param pageOrientation the page orientation
	 * @param data the data
	 * @return the payment generator
	 */
	public PaymentGenerator createGenerator(String pageOrientation, PaymentReportData data) {
		switch (pageOrientation) {
		case "PORTRAIT":
			return new PaymentReportVerticalGenerator();

		case "LANDSCAPE":
			return paymentReportHorizontalGenerator;

		default:
			break;
		}

		return null;
	}
}
