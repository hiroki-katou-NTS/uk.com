package nts.uk.ctx.basic.dom.organization.position;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.company.CompanyCode;

@Getter
public class JobTitleRef extends AggregateRoot{
	
	private AuthorizationCode authorizationCode;
			
	private HistoryId historyId;
	
	private CompanyCode companyCode;
	
	private JobCode jobCode;
	
	private ReferenceSettings referenceSettings;
	
	public JobTitleRef(AuthorizationCode authorizationCode, ReferenceSettings referenceSettings, 
			CompanyCode companyCode, HistoryId historyId,JobCode jobCode) {
		super();
		this.authorizationCode = authorizationCode;
		this.historyId = historyId;
		this.companyCode = companyCode;
		this.referenceSettings = referenceSettings;
		this.jobCode = jobCode;
	}

	public static JobTitleRef createSimpleFromJavaType(String authorizationCode, int referenceSettings, 
			 String companyCode, String historyId,String jobCode)
	{
		return new JobTitleRef(new AuthorizationCode(authorizationCode), ReferenceSettings.valueOf(String.valueOf(referenceSettings)),
				new CompanyCode(companyCode), new HistoryId(historyId),new JobCode(jobCode));
	}

}