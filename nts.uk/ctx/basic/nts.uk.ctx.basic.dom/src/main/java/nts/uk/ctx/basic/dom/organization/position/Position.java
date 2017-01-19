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
<<<<<<< HEAD

	private Memo memo;

	public Position(GeneralDate endDate, JobName jobName, JobCode jobCode, JobCode jobOutCode, GeneralDate startDate,
			String historyID, String companyCode, Memo memo) {
=======
	
	private Memo memo;
	
	
	public Position(GeneralDate endDate, JobName jobName, JobCode jobCode, JobCode jobOutCode,
			GeneralDate startDate, String historyID, String companyCode, Memo memo) {
>>>>>>> basic/teamd/develop
		super();
		this.endDate = endDate;
		this.jobName = jobName;
		this.jobCode = jobCode;
		this.jobOutCode = jobOutCode;
		this.startDate = startDate;
		this.companyCode = companyCode;
		this.memo = memo;
	}

	public static Position createFromJavaType(GeneralDate endDate, String jobName, String jobCode, String jobOutCode,
			GeneralDate startDate, String historyID, String companyCode, String memo) {
		return new Position(endDate, new JobName(jobName), new JobCode(jobCode), new JobCode(jobOutCode), startDate,
				historyID, companyCode, new Memo(memo));

	}

}