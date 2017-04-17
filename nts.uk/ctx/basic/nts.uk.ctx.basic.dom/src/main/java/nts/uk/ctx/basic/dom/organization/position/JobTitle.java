package nts.uk.ctx.basic.dom.organization.position;


import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.primitive.Memo;

@Getter
public class JobTitle extends AggregateRoot {



	private JobName jobName;

	private JobCode jobCode;

	private JobCode jobOutCode;

	private String historyId;

	private String companyCode;

	private Memo memo;

	private HiterarchyOrderCode hiterarchyOrderCode;
	
	private PresenceCheckScopeSet presenceCheckScopeSet;
	

	
	public JobTitle( JobName jobName, JobCode jobCode, JobCode jobOutCode,
			 String historyId, String companyCode, Memo memo ,HiterarchyOrderCode hiterarchyOrderCode ,PresenceCheckScopeSet presenceCheckScopeSet) {

		super();
		
		this.jobName = jobName;
		this.jobCode = jobCode;
		this.jobOutCode = jobOutCode;
		this.companyCode = companyCode;
		this.historyId = historyId;
		this.memo = memo;
		this.hiterarchyOrderCode = hiterarchyOrderCode;
		this.presenceCheckScopeSet = presenceCheckScopeSet;
	}

	
	public JobTitle( JobName jobName, JobCode jobCode, JobCode jobOutCode,
			 String companyCode, Memo memo,HiterarchyOrderCode hiterarchyOrderCode ,PresenceCheckScopeSet presenceCheckScopeSet) {

		super();
		
		this.jobName = jobName;
		this.jobCode = jobCode;
		this.jobOutCode = jobOutCode;
		this.companyCode = companyCode;
		this.historyId = IdentifierUtil.randomUniqueId();
		this.memo = memo;
		this.hiterarchyOrderCode = hiterarchyOrderCode;
		this.presenceCheckScopeSet = presenceCheckScopeSet;
	}

	public static JobTitle createFromJavaType( String jobName,String jobCode, String jobOutCode,
			 String historyId, String companyCode, String memo ,String hiterarchyOrderCode ,int presenceCheckScopeSet) {
		return new JobTitle(new JobName(jobName),  new JobCode(jobCode), new JobCode(jobOutCode), 
				historyId, companyCode, new Memo(memo),new HiterarchyOrderCode(hiterarchyOrderCode) ,EnumAdaptor.valueOf(presenceCheckScopeSet, PresenceCheckScopeSet.class));

	}


	public JobTitle(JobName jobName, JobCode jobCode, String historyId, Memo memo , String companyCode) {
		super();
		this.jobName = jobName;
		this.jobCode = jobCode;
		this.historyId = historyId;
		this.memo = memo;
		this.companyCode = companyCode;
	}

}