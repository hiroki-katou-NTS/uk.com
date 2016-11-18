package nts.uk.ctx.pr.proto.dom.paymentdata.service;

import java.util.List;
import java.util.Map;

import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;

public interface PaymentDetailService {
	/**
	 * Calculate value in payment detail for personal.
	 * @param companyCode company code
	 * @param personId person id
	 * @param baseYearMonth base year month
	 * @return payment value
	 */
	Map<CategoryAtr, List<DetailItem>> calculatePayValue(PaymentDetailParam param);
}
