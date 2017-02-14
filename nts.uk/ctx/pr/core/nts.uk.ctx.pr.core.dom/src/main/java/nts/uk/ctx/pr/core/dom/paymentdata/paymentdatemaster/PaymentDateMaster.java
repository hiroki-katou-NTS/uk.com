package nts.uk.ctx.pr.core.dom.paymentdata.paymentdatemaster;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.paymentdata.SparePayAtr;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * Payroll date master;
 *
 */
public class PaymentDateMaster extends AggregateRoot {
	@Getter
	private BigDecimal neededWorkDay;
	
	@Getter
	private ProcessingNo processingNo;
	
	@Getter
	private YearMonth processingYearMonth;
	
	@Getter
	private SparePayAtr sparePayAttribute;
	
	@Getter
	private PayBonusAtr payBonusAttribute;
	
	@Getter
	private LocalDate standardDate;

	/**
	 * 
	 * @param neededWorkDay
	 * @param processingNo
	 * @param processingYearMonth
	 * @param sparePayAttribute
	 * @param payBonusAttribute
	 */
	public PaymentDateMaster(BigDecimal neededWorkDay, ProcessingNo processingNo, YearMonth processingYearMonth,
			SparePayAtr sparePayAttribute, PayBonusAtr payBonusAttribute, LocalDate standardDate) {
		super();
		this.neededWorkDay = neededWorkDay;
		this.processingNo = processingNo;
		this.processingYearMonth = processingYearMonth;
		this.sparePayAttribute = sparePayAttribute;
		this.payBonusAttribute = payBonusAttribute;
		this.standardDate = standardDate;
	}
	
	public static PaymentDateMaster createFromJavaType(BigDecimal neededWorkDay, int processingNo, int processingYearMonth,
			int sparePayAttribute, int payBonusAttribute, LocalDate standardDate){
		return new PaymentDateMaster(neededWorkDay, new ProcessingNo(processingNo), YearMonth.of(processingYearMonth), EnumAdaptor.valueOf(sparePayAttribute, SparePayAtr.class), EnumAdaptor.valueOf(payBonusAttribute, PayBonusAtr.class), standardDate);
	}
}
