package nts.uk.ctx.basic.dom.organization.position;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.payclassification.ExclusVersion;

@Getter
public class Position extends AggregateRoot {

	private GeneralDate endDate;

	private JobName jobName;

	private JobCode jobCode;

	private ExternalCode jobOutCode;

	private GeneralDate startDate;

	private String historyID;

	private String companyCode;

	private ExclusVersion exclusVersion;
	
	public Position(GeneralDate endDate, JobName jobName, JobCode jobCode, ExternalCode jobOutCode,
			GeneralDate startDate, String historyID, String companyCode, ExclusVersion exclusVersion) {
		super();
		this.endDate = endDate;
		this.jobName = jobName;
		this.jobCode = jobCode;
		this.jobOutCode = jobOutCode;
		this.startDate = startDate;
		this.historyID = historyID;
		this.companyCode = companyCode;
		this.exclusVersion = exclusVersion;
	}

}