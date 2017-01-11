package nts.uk.ctx.basic.dom.organization.position;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.primitive.Memo;

@Getter
public class Position extends AggregateRoot {

	private GeneralDate endDate;

	private JobName jobName;

	private JobCode jobCode;

	private JobCode jobOutCode;

	private GeneralDate startDate;

	private String historyID;

	private String companyCode;
	
	private Memo memo;
	
	
	public Position(GeneralDate endDate, JobName jobName, JobCode jobCode, JobCode jobOutCode,
			GeneralDate startDate, String historyID, String companyCode, Memo memo) {
		super();
		this.endDate = endDate;
		this.jobName = jobName;
		this.jobCode = jobCode;
		this.jobOutCode = jobOutCode;
		this.startDate = startDate;
		this.historyID = historyID;
		this.companyCode = companyCode;
		this.memo = memo;
	}

}