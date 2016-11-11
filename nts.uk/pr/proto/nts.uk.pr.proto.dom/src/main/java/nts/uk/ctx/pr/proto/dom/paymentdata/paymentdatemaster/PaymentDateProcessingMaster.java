package nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.ProcessingNo;

/**
 * Aggregate Root: payment date processing master. 
 *
 */
public class PaymentDateProcessingMaster extends AggregateRoot {
	/**
	 * Payroll bonus attribute
	 */
	@Getter
	private PayBonusAtr payBonusAttribute;
	
	/**
	 * Processing Number.
	 */
	@Getter
	private ProcessingNo processingNo;
	
	/**
	 * Processing Name.
	 */
	@Getter
	private ProcessingName processingName;
	
	/**
	 * Current processing year month.
	 */
	@Getter
	private YearMonth currentProcessingYm;
	
	/**
	 * Display attribute.
	 */
	@Getter
	private boolean displayAttribute;

	/**
	 * Constructor with parameter.
	 * @param payBonusAtribute Payroll Bonus Attribute
	 * @param processingNo Processing No
	 * @param processingName Processing Name
	 * @param currentProcessingYm Current Processing Year Month
	 * @param displayAtribute Display Attribute
	 */
	public PaymentDateProcessingMaster(PayBonusAtr payBonusAttribute, ProcessingNo processingNo,
			ProcessingName processingName, YearMonth currentProcessingYm, boolean displayAttribute) {
		super();
		this.payBonusAttribute = payBonusAttribute;
		this.processingNo = processingNo;
		this.processingName = processingName;
		this.currentProcessingYm = currentProcessingYm;
		this.displayAttribute = displayAttribute;
	}
	
	/**
	 * Create instance using Java type parameters.
	 */
	public static PaymentDateProcessingMaster createFromJavaType(int payBonusAttribute, int processingNo,
			String processingName, BigDecimal currentProcessingYm, boolean displayAttribute) {
//		return new PaymentDateProcessingMaster(
//				PayBonusAttribute.valueOf(payBonusAttribute),
//				new ProcessingNo(processingNo),
//				new ProcessingName(processingName),
//				currentProcessingYm,
//				displayAttribute
//				);
		
		return null;
	}
}
