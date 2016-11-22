package nts.uk.ctx.pr.screen.app.query.qpp005.result;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;

@Value
public class PaymentDataHeaderDto {

	/** 扶養人数（その月時点） */
	private Integer dependentNumber;

	/** 明細書コード */
	private String specificationCode;

	/** 明細書名 */
	private String specificationName;

	/** 作成方法フラグ */
	private Integer makeMethodFlag;

	/** 社員コード */
	private String employeeCode;

	/** memo */
	private String comment;

	public PaymentDataHeaderDto(Integer dependentNumber, String specificationCode, String specificationName,
			Integer makeMethodFlag, String employeeCode, String comment) {
		super();
		this.dependentNumber = dependentNumber;
		this.specificationCode = specificationCode;
		this.specificationName = specificationName;
		this.makeMethodFlag = makeMethodFlag;
		this.employeeCode = employeeCode;
		this.comment = comment;
	}

}
