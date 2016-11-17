package nts.uk.ctx.pr.proto.app.paymentdata.find;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateProcessingMaster;

@Value
public class PaymentDateProcessingMasterDto {
	/**
	 * Payroll bonus attribute
	 */
	int payBonusAttribute;

	/**
	 * Processing Number.
	 */
	int processingNo;

	/**
	 * Processing Name.
	 */
	String processingName;

	/**
	 * Current processing year month.
	 */
	int currentProcessingYm;

	/**
	 * Display attribute.
	 */
	int displayAttribute;
	
	public static PaymentDateProcessingMasterDto fromDomain(PaymentDateProcessingMaster domain) {
		return new PaymentDateProcessingMasterDto(domain.getPayBonusAttribute().value, domain.getProcessingNo().v().intValue(), domain.getProcessingName().v(), domain.getCurrentProcessingYm().v().intValue(), domain.getDisplayAttribute().value);
	}
}
