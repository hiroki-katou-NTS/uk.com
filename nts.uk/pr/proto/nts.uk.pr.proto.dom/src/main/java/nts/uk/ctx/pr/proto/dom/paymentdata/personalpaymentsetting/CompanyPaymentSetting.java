package nts.uk.ctx.pr.proto.dom.paymentdata.personalpaymentsetting;

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
}
