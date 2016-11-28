package nts.uk.ctx.pr.screen.app.query.qpp005.result;

import lombok.Value;

@Value
public class PaymentDataHeaderDto {
	
	Integer processingYM;
	
	/** 扶養人数（その月時点） */
	Integer dependentNumber;

	/** 明細書コード */
	String specificationCode;

	/** 明細書名 */
	String specificationName;

	/** 作成方法フラグ */
	Integer makeMethodFlag;

	/** 社員コード */
	String employeeCode;

	/** memo */
	String comment;

	boolean isCreated;

	public PaymentDataHeaderDto(Integer processingYM, Integer dependentNumber, String specificationCode, String specificationName,
			Integer makeMethodFlag, String employeeCode, String comment, boolean isCreated) {
		super();
		
		this.processingYM = processingYM;
		this.dependentNumber = dependentNumber;
		this.specificationCode = specificationCode;
		this.specificationName = specificationName;
		this.makeMethodFlag = makeMethodFlag;
		this.employeeCode = employeeCode;
		this.comment = comment;
		this.isCreated = isCreated;
	}

}
