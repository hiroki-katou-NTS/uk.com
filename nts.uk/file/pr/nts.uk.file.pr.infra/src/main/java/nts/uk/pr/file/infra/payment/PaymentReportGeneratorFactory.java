/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import javax.ejb.Stateless;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;

/**
 * A factory for creating Generator objects.
 */
@Stateless
public class PaymentReportGeneratorFactory {

	/**
	 * Creates a new PaymentReportGenerator object.
	 *
	 * @param data the data
	 * @return the payment generator
	 */
	public PaymentGenerator createGenerator(PaymentReportData data) {

		switch (data.getSelectPrintTypes()) {
		case 0:
			return new PaymentReportVerticalOneGenerator();
		case 1:
			return new PaymentReportVerticalTwoGenerator();
		case 2:
			return new PaymentReportVerticalThreeGenerator();
		case 3:
			return new PaymentReportHorizontalTwoGenerator();
		case 4:
			return new PaymentReportZFoldedGenerator();
		case 5:
			return new PaymentReportPostCardGenerator();		

		default:
			break;
		}

		return null;
	}
}
