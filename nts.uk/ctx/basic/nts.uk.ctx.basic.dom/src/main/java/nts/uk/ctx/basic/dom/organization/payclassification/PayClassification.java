package nts.uk.ctx.basic.dom.organization.payclassification;


import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import nts.uk.shr.com.primitive.Memo;

@Getter
public class PayClassification extends AggregateRoot {

	private PayClassificationName payClassName;

	private PayClassificationCode payClassCode;

	private String companyCode;
	
	private Memo memo;
	

	
	
	public PayClassification(PayClassificationName payClassName, PayClassificationCode payClassCode ,String companyCode, Memo memo) {
		super();
		this.payClassName = payClassName;
		this.payClassCode = payClassCode;
		this.companyCode = companyCode;
		this.memo = memo;
	}

	public static PayClassification createFromJavaType(String payClassName,String payClassCode,String companyCode,String memo){
		return new PayClassification(new PayClassificationName(payClassName), new PayClassificationCode(payClassCode), companyCode, new Memo(memo));
		
		
	}
}