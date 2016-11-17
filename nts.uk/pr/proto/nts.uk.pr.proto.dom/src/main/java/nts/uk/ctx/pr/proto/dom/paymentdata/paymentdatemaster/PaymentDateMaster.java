package nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster;

import java.math.BigDecimal;

import javax.persistence.EnumType;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.ProcessingNo;
import nts.uk.ctx.pr.proto.dom.paymentdata.SparePayAtr;

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

	/**
	 * 
	 * @param neededWorkDay
	 * @param processingNo
	 * @param processingYearMonth
	 * @param sparePayAttribute
	 * @param payBonusAttribute
	 */
	public PaymentDateMaster(BigDecimal neededWorkDay, ProcessingNo processingNo, YearMonth processingYearMonth,
			SparePayAtr sparePayAttribute, PayBonusAtr payBonusAttribute) {
		super();
		this.neededWorkDay = neededWorkDay;
		this.processingNo = processingNo;
		this.processingYearMonth = processingYearMonth;
		this.sparePayAttribute = sparePayAttribute;
		this.payBonusAttribute = payBonusAttribute;
	}
	
	public static PaymentDateMaster createFromJavaType(BigDecimal neededWorkDay, int processingNo, int processingYearMonth,
			int sparePayAttribute, int payBonusAttribute){
		return new PaymentDateMaster(neededWorkDay, new ProcessingNo(processingNo), YearMonth.of(processingYearMonth), EnumAdaptor.valueOf(sparePayAttribute, SparePayAtr.class), EnumAdaptor.valueOf(payBonusAttribute, PayBonusAtr.class));
	}
}
