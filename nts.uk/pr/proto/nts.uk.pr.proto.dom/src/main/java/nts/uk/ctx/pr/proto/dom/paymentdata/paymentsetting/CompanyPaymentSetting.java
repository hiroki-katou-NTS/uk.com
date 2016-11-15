package nts.uk.ctx.pr.proto.dom.paymentdata.paymentsetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.BonusDetailCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentDetailCode;

public class CompanyPaymentSetting extends AggregateRoot{
	
	@Getter
	private final CompanyCode companyCode;

	@Getter
	private final YearMonth startDate;
	
	@Getter
	private BonusDetailCode bonusDetailCode;

	private YearMonth endDate;

	private PaymentDetailCode paymentDetailCode;
	
	public CompanyPaymentSetting(CompanyCode companyCode,YearMonth startDate) {
		super();
		this.companyCode = companyCode;
		this.startDate = startDate;
	}
	
	/**
	 * Create instance using Java type parameters.
	 * 
	 * @param companyCode company code
	 * @param startDate start date
	 * @return CompanyPaymentSetting
	 */
	public static CompanyPaymentSetting createFromJavaType(String companyCode, int startDate) {
		return new CompanyPaymentSetting(new CompanyCode(companyCode), new YearMonth(startDate));
	}
}
