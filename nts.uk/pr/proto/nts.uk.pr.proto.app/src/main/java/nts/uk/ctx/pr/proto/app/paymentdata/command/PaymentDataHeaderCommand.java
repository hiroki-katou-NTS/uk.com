package nts.uk.ctx.pr.proto.app.paymentdata.command;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;

@Value
public class PaymentDataHeaderCommand {

	/** 扶養人数（その月時点） */
	private int dependentNumber;

	/** 明細書コード */
	private String specificationCode;

	/** 明細書名 */
	private String specificationName;

	/** 作成方法フラグ */
	private int makeMethodFlag;

	/** 社員コード */
	private String employeeCode;

	/** memo */
	private String comment;

	/**
	 * Create DTO from domain object.
	 * 
	 * @param domain
	 *            domain
	 * @return DTO
	 */
	public static PaymentDataHeaderCommand fromDomain(Payment domain, String specificationName, String employeeCode) {
		return new PaymentDataHeaderCommand(
				domain.getDependentNumber().v(), 
				domain.getSpecificationCode().v(),
				specificationName,
				domain.getMakeMethodFlag().value,
				employeeCode,
				domain.getComment().v()
				);
	}

}
