package nts.uk.ctx.basic.dom.organization.payclassification;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
public class PayClassification extends AggregateRoot {

	private GeneralDate endDate;

	private ExclusVersion exclusiveVersion;

	private Memo memo;

	private PayClassificationName payClassificationName;

	private GeneralDate startDate;

	private PayClassificationCode payClassificationCode;

	private String historyID;

	private String companyCode;
	
	public PayClassification(GeneralDate endDate, ExclusVersion exclusiveVersion, Memo memo,
			PayClassificationName payClassificationName, GeneralDate startDate, PayClassificationCode payClassCode,
			String historyID ,String companyCode, PayClassificationCode payClassificationCode) {
		super();
		this.endDate = endDate;
		this.exclusiveVersion = exclusiveVersion;
		this.memo = memo;
		this.payClassificationName = payClassificationName;
		this.startDate = startDate;
		this.payClassificationCode = payClassificationCode;
		this.historyID = historyID;
		this.companyCode = companyCode;
	}

}