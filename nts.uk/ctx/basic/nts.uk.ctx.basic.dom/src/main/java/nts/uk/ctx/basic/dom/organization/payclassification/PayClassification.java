package nts.uk.ctx.basic.dom.organization.payclassification;

import org.eclipse.persistence.internal.jpa.metadata.accessors.objects.MetadataMethod;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.primitive.Memo;

@Getter
public class PayClassification extends AggregateRoot {

	private GeneralDate endDate;


	private PayClassificationName payClassificationName;

	private GeneralDate startDate;

	private PayClassificationCode payClassCode;

	private String historyID;

	private String companyCode;
	
	private Memo memo;
	

	
	
	public PayClassification(GeneralDate endDate, 
			PayClassificationName payClassificationName, GeneralDate startDate, PayClassificationCode payClassCode,
			String historyID ,String companyCode, Memo Memo) {
		super();
		this.endDate = endDate;
		this.payClassificationName = payClassificationName;
		this.startDate = startDate;
		this.payClassCode = payClassCode;
		this.historyID = historyID;
		this.companyCode = companyCode;
		this.memo = memo;
	}

}