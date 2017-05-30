/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.LayoutItem;
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

		switch (data.getLayoutItem()) {
		case LayoutItem.VERTICAL_ONE_PERSON:
			return new PaymentReportVerticalOneGenerator();
		case LayoutItem.VERTICAL_TWO_PERSON:
			return new PaymentReportVerticalTwoGenerator();
		case LayoutItem.VERTICAL_THREE_PERSON:
			return new PaymentReportVerticalThreeGenerator();
		case LayoutItem.HORIZONTAL_TWO_PERSON:
			return new PaymentReportHorizontalTwoGenerator();
		case LayoutItem.ZFOLDED:
			return new PaymentReportZFoldedGenerator();
		case LayoutItem.POSTCARD:
			return new PaymentReportPostCardGenerator();

		default:
			break;
		}

		return null;
	}
}
