package nts.uk.ctx.pr.proto.dom.paymentdata.paymentsetting;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.BonusDetailCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.HolidayPaid;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentDetailCode;
import nts.uk.shr.com.primitive.PersonId;

public class PersonalPaymentSetting extends AggregateRoot {
	
	@Getter
	private final CompanyCode companyCode;

	@Getter
	private final PersonId personId;

	@Getter
	private final YearMonth startDate;
	
	@Getter
	private BonusDetailCode bonusDetailCode;

	@Getter
	private YearMonth endDate;

	@Getter
	private PaymentDetailCode paymentDetailCode;
	
	public PersonalPaymentSetting(CompanyCode companyCode, PersonId personId,YearMonth startDate) {
		super();
		this.companyCode = companyCode;
		this.personId = personId;
		this.startDate = startDate;
	}
	
	/**
	 * Create instance using Java type parameters.
	 * 
	 * @param companyCode company code
	 * @param personId person id
	 * @param startDate start date
	 * @return PersonalPaymentSetting
	 */
	public static PersonalPaymentSetting createFromJavaType(String companyCode, String personId,int startDate) {
		return new PersonalPaymentSetting(new CompanyCode(companyCode), new PersonId(personId), new YearMonth(startDate));
	}
}
