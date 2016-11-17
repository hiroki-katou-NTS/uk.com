package nts.uk.ctx.pr.proto.dom.paymentdata.service;

import java.util.Map;

import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaid;
import nts.uk.shr.com.primitive.PersonId;

public interface PaymentDetailService {
	/**
	 * Calculate value in payment detail for personal.
	 * @param companyCode company code
	 * @param personId person id
	 * @param baseYearMonth base year month
	 * @return payment value
	 */
	Map<CategoryAtr, DetailItem> calculatePayValue(PaymentDetailParam param);
}
