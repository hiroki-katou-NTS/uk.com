package nts.uk.ctx.basic.dom.organization.position;



import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.company.CompanyCode;
import nts.uk.shr.com.primitive.Memo;

@Getter
@Setter
public class JobTitle extends AggregateRoot{
	
	
	private CompanyCode companyCode;
	
	private String historyId;
	
	private JobCode jobCode;
	
	private JobName jobName;
	
	private PresenceCheckScopeSet presenceCheckScopeSet;
	
	private JobCode jobOutCode;
	
	private Memo memo;

	public JobTitle(CompanyCode companyCode, String historyId, JobCode jobCode, JobName jobName,
			PresenceCheckScopeSet presenceCheckScopeSet, JobCode jobOutCode, Memo memo) {
		super();
		this.companyCode = companyCode;
		this.historyId = historyId;
		this.jobCode = jobCode;
		this.jobName = jobName;
		this.presenceCheckScopeSet = presenceCheckScopeSet;
		this.jobOutCode = jobOutCode;
		this.memo = memo;
	}	

	public static JobTitle createSimpleFromJavaType(
			String companyCode,String historyId,String jobCode,
			String jobName, int presenceCheckScopeSet,
			String jobOutCode, String memo)
	{
		return new JobTitle( new CompanyCode(companyCode),historyId,new JobCode(jobCode),
					new JobName(jobName),
					EnumAdaptor.valueOf(presenceCheckScopeSet, PresenceCheckScopeSet.class), 
					new JobCode(jobOutCode),
					new Memo(memo));
	}

}
